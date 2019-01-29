package com.cloud.gds.cleaning.service.impl;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.dto.WillAnalysisData;
import com.cloud.gds.cleaning.api.entity.AnalysisResult;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.feign.DataAnalysisService;
import com.cloud.gds.cleaning.api.vo.DataSetVo;
import com.cloud.gds.cleaning.api.vo.GroupVo;
import com.cloud.gds.cleaning.api.vo.ResultJsonVo;
import com.cloud.gds.cleaning.mapper.AnalysisResultMapper;
import com.cloud.gds.cleaning.mapper.DataFieldMapper;
import com.cloud.gds.cleaning.mapper.DataFieldValueMapper;
import com.cloud.gds.cleaning.mapper.DataRuleMapper;
import com.cloud.gds.cleaning.service.DoAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.text.Collator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 执行分析实现类
 *
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2019-01-20
 */
@Service
public class DoAnalysisServiceImpl implements DoAnalysisService {

	private final DataFieldValueMapper dataFieldValueMapper;
	private final DataFieldMapper dataFieldMapper;
	private final DataRuleMapper dataRuleMapper;
	private final ExecutorService analysisThreadPool;
	private final DataAnalysisService dataAnalysisService;
	private final AnalysisResultMapper analysisResultMapper;

	@Value("${file-save.path}")
	String fileSavePath;

	@Autowired
	public DoAnalysisServiceImpl(DataFieldValueMapper dataFieldValueMapper, DataFieldMapper dataFieldMapper,
								 DataRuleMapper dataRuleMapper, @Qualifier("analysisThreadPool") ExecutorService analysisThreadPool,
								 DataAnalysisService dataAnalysisService, AnalysisResultMapper analysisResultMapper) {
		this.dataFieldValueMapper = dataFieldValueMapper;
		this.dataFieldMapper = dataFieldMapper;
		this.dataRuleMapper = dataRuleMapper;
		this.analysisThreadPool = analysisThreadPool;
		this.dataAnalysisService = dataAnalysisService;
		this.analysisResultMapper = analysisResultMapper;
	}

	@Override
	public void handOutAnalysis(long fieldId, float threshold, int oneSize) {
		WillAnalysisData willAnalysisData = this.getAnalysisData(fieldId, threshold);

		int beforeNum = 0;
		int afterNum = 0;
		float mx = 0.8F;
		while (oneSize << 2 < beforeNum) {

			if (beforeNum * mx > afterNum) {
				WillAnalysisData AnalysisData = this.getAnalysisData(fieldId, threshold);
				beforeNum = willAnalysisData.getData().size();
				//结果处理
				this.resultHandle(fieldId, this.doHandout(fieldId, willAnalysisData, oneSize));
				afterNum = AnalysisData.getData().size();
			} else {
				this.resultHandle(fieldId, this.doHandout(fieldId, willAnalysisData, oneSize << 1));
			}
		}

	}

	private List<ResultJsonVo> doHandout(long fieldId, WillAnalysisData willAnalysisData, int oneSize) {
		List<JSONObject> list = willAnalysisData.getData();
		//最大权重
		float maxWeight = Collections.max(willAnalysisData.getWeights());
		//最大权重所对应的字段
		String maxWeightParam = willAnalysisData.getParams().get(willAnalysisData.getWeights().indexOf(maxWeight));
		//根据该字段对数据进行排序
		list.sort((o1, o2) -> {
			Collator collator = Collator.getInstance(Locale.CHINA);
			return collator.compare(o1.get(maxWeightParam), o2.get(maxWeightParam));
		});

		//待分析数据分组处理
		boolean flag = true;
		List<JSONObject> subList;
		AtomicInteger needGetNum = new AtomicInteger((int) Math.ceil((double) list.size() / oneSize));
		int currentNum = 0;

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		List<ResultJsonVo> resultList = new ArrayList<>();
		//将数据分块分发处理，通过feign
		while (flag) {
			if (list.size() > oneSize * (currentNum + 1)) {
				//非最后一块
				subList = list.subList(currentNum * oneSize, oneSize * (currentNum + 1));
			} else {
				//最后一块
				subList = list.subList(currentNum * oneSize, list.size());
				flag = false;
			}
			WillAnalysisData subData = willAnalysisData.clone();
			subData.setData(subList);
			String filePath = this.willAnalysisDataToFile(fieldId + "_" + currentNum, subData);

			analysisThreadPool.execute(() -> {
				//将主线程的RequestAttributes赋予新线程
				RequestContextHolder.setRequestAttributes(requestAttributes);
				//向feign分发任务，失败重试
				String resultStr = null;
				int tryNum = 0;
				int maxTry = 5;
				while (resultStr == null && tryNum < maxTry) {
					resultStr = dataAnalysisService.bigDataAnalysis(filePath);
					tryNum++;
				}

				//清空RequestAttributes
				RequestContextHolder.resetRequestAttributes();
				//同步汇总
				if (JSONUtil.isJsonObj(resultStr)) {
					synchronized (resultList) {
						resultList.add(JSONUtil.toBean(resultStr, ResultJsonVo.class));
					}
				}
				//待获取反馈数-1
				needGetNum.getAndDecrement();
			});
			//当前执行到数目+1
			currentNum++;
		}

		while (needGetNum.get() > 0) {
			// System.out.println("未完成！");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return resultList;
	}

	/**
	 * 将汇总的返回数据进行处理并存储
	 *
	 * @param fieldId
	 * @param resultList
	 */
	private void resultHandle(long fieldId, List<ResultJsonVo> resultList) {
		// 结果存入数据库
		saveAnalysisResult(fieldId, resultList);
		// 自动清洗
		automaticCleaning(fieldId);

	}


	/**
	 * 储存分析结果返回数据
	 *
	 * @param fieldId
	 * @param resultList
	 * @return
	 */
	private boolean saveAnalysisResult(Long fieldId, List<ResultJsonVo> resultList) {
		List<AnalysisResult> list = new ArrayList<>();
		for (ResultJsonVo jsonVo : resultList) {
			for (GroupVo vo : jsonVo.getGroup()) {
				AnalysisResult result = new AnalysisResult();
				result.setFieldId(fieldId);
				result.setBaseId(jsonVo.getId());
				result.setCompareId(vo.getId());
				result.setSimilarity(vo.getSimilarity());
				// 是否手动分析(0、自动 1、手动)
				result.setIsManual(DataCleanConstant.FALSE);
				list.add(result);
			}
		}
		return insertBatch(list, 100);
	}

	/**
	 * 分批次插入数据库
	 *
	 * @param list
	 * @param oneSize
	 * @return
	 */
	public boolean insertBatch(List<AnalysisResult> list, int oneSize) {
		boolean flag = true;
		List<AnalysisResult> subList;
		int currentNum = 0;
		while (flag) {
			if (list.size() > oneSize * (currentNum + 1)) {
				subList = list.subList(currentNum * oneSize, oneSize * (currentNum + 1));
			} else {
				subList = list.subList(currentNum * oneSize, list.size());
				flag = false;
			}
			analysisResultMapper.insertAll(subList);
			currentNum++;
		}
		return true;
	}


	/**
	 * 需要分析的数据
	 *
	 * @param fieldId
	 * @return List<DataFieldValue>
	 */
	private List<DataFieldValue> needAnalysisList(Long fieldId) {
		//取出需要分析的数据，并更新状态
		Wrapper<DataFieldValue> wrapper = new EntityWrapper<DataFieldValue>().eq("field_id", fieldId)
			.eq("is_deleted", DataCleanConstant.FALSE);
		List<DataFieldValue> needAnalysisList = dataFieldValueMapper.selectList(wrapper);
		DataFieldValue dataFieldValue = new DataFieldValue();
		dataFieldValue.setState(1);
		dataFieldValueMapper.update(dataFieldValue, wrapper);
		//返回
		return needAnalysisList;
	}

	/**
	 * 获取并处理待分析数据
	 *
	 * @param fieldId
	 * @param threshold
	 * @return
	 */
	private WillAnalysisData getAnalysisData(Long fieldId, Float threshold) {

		DataField dataField = new DataField();
		dataField.setId(fieldId);
		dataField = dataFieldMapper.selectById(dataField);

		DataRule dataRule = new DataRule();
		dataRule.setId(dataField.getRuleId());
		dataRule = dataRuleMapper.selectById(dataRule);

		WillAnalysisData willAnalysisData = new WillAnalysisData();
		//设置阀值
		willAnalysisData.setThreshold(threshold);
		//设置字段名，权重，字段是否近似
		List<DataSetVo> list = JSONUtil.parseArray(dataRule.getParams()).toList(DataSetVo.class);
		List<String> params = new ArrayList<>(4);
		List<Float> weights = new ArrayList<>(4);
		List<Integer> approximates = new ArrayList<>(4);
		List<String> needDeleteFields = new ArrayList<>();
		for (DataSetVo dataSetVo : list) {
			//如果权重为零，删除该字段
			if (dataSetVo.getWeight() > 0) {
				params.add(dataSetVo.getProp());
				weights.add(dataSetVo.getWeight());
				approximates.add(dataSetVo.getIsSynonymous());
			} else {
				needDeleteFields.add(dataSetVo.getProp());
			}
		}
		willAnalysisData.setParams(params);
		willAnalysisData.setWeights(weights);
		willAnalysisData.setApproximates(approximates);

		willAnalysisData.setNeedReanalysis(dataField.getNeedReanalysis());
		//设置待分析数据
		List<DataFieldValue> willAnalysisList = needAnalysisList(fieldId);
		List<JSONObject> objList = new ArrayList<>();
		for (DataFieldValue dataFieldValue : willAnalysisList) {
			if (JSONUtil.isJsonObj(dataFieldValue.getFieldValue())) {
				JSONObject jsonObj = JSONUtil.parseObj(dataFieldValue.getFieldValue());
				//如果原数据含字段id，删除之
				jsonObj.remove("id");
				//删除权重为0的字段
				for (String needDeleteField : needDeleteFields) {
					jsonObj.remove(needDeleteField);
				}
				for (String needField : params) {
					if (!jsonObj.containsKey(needField)) {
						jsonObj.put(needField, "");
					}
				}
				//添加id字段
				jsonObj.putOnce("id", dataFieldValue.getId());

				objList.add(jsonObj);
			}
		}
		willAnalysisData.setData(objList);

		return willAnalysisData;
	}

	/**
	 * 将willAnalysisData存储到文件，并返回文件路径
	 *
	 * @param fileName         文件名
	 * @param willAnalysisData
	 * @return
	 */
	private String willAnalysisDataToFile(String fileName, WillAnalysisData willAnalysisData) {
		//写入文件
		String resultPath = fileSavePath + "/" + fileName + ".json";
		FileWriter fileWriter = new FileWriter(resultPath);
		fileWriter.write(JSONUtil.toJsonStr(willAnalysisData));
		//返回文件路径
		return resultPath;
	}

	@Override
	public String getAllNeedAnalysisDataFile(Long fieldId, Float threshold) {
		return this.willAnalysisDataToFile(fieldId.toString(), this.getAnalysisData(fieldId, threshold));
	}

	@Override
	public boolean automaticCleaning(Long fieldId) {
		// 查询相应清洗池的分析结果集
		List<AnalysisResult> results = analysisResultMapper.selectList(new EntityWrapper<AnalysisResult>().eq("field_id", fieldId));

		// 组装待清洗数据
		List<DataFieldValue> list = new ArrayList<>();
		if (results.size() == DataCleanConstant.FALSE) {
			return true;
		}
		for (AnalysisResult analysisResult : results) {
			if (!analysisResult.getBaseId().equals(analysisResult.getCompareId())) {
				DataFieldValue dataFieldValue = new DataFieldValue();
				dataFieldValue.setId(analysisResult.getCompareId());
				dataFieldValue.setBeCleanedId(analysisResult.getBaseId());
				dataFieldValue.setFieldId(analysisResult.getFieldId());
				// 由于数据被清洗了,对数据进行删除状态的更新
				dataFieldValue.setIsDeleted(DataCleanConstant.TRUE);
				assert SecurityUtils.getUser() != null;
				dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
				dataFieldValue.setModifiedTime(LocalDateTime.now());
				list.add(dataFieldValue);
			}
		}
		// 清洗数据,数据被清洗后要将分析结表中相应数据删除
		if (list.size() > 0) {
			return dataFieldValueMapper.updateBatchById(list) && SqlHelper.delBool(analysisResultMapper.delete(new EntityWrapper<AnalysisResult>().eq("field_id", fieldId)));

		}
		return true;
	}


}
