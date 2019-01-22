package com.cloud.gds.cleaning.service.impl;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.dto.WillAnalysisData;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.DataSetVo;
import com.cloud.gds.cleaning.mapper.DataFieldMapper;
import com.cloud.gds.cleaning.mapper.DataFieldValueMapper;
import com.cloud.gds.cleaning.mapper.DataRuleMapper;
import com.cloud.gds.cleaning.service.DoAnalysisService;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

/**
 * 执行分析实现类
 *
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2019-01-20
 */
public class DoAnalysisServiceImpl implements DoAnalysisService {

	private final DataFieldValueMapper dataFieldValueMapper;
	private final DataFieldMapper dataFieldMapper;
	private final DataRuleMapper dataRuleMapper;
	@Value("${file-save.path}")
	String fileSavePath;

	@Autowired
	@Qualifier("analysisThreadPool")
	private ExecutorService analysisThreadPool;

	@Autowired
	public DoAnalysisServiceImpl(DataFieldValueMapper dataFieldValueMapper, DataFieldMapper dataFieldMapper,
		DataRuleMapper dataRuleMapper) {
		this.dataFieldValueMapper = dataFieldValueMapper;
		this.dataFieldMapper = dataFieldMapper;
		this.dataRuleMapper = dataRuleMapper;
	}

	@Override
	public void handOutAnalysis(long fieldId, float threshold, int oneSize) {
		WillAnalysisData willAnalysisData = this.getAnalysisData(fieldId, threshold);

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
		int currentNum = 0;
		while (flag) {
			if (list.size() > oneSize * (currentNum + 1)) {
				subList = list.subList(currentNum * oneSize, oneSize * (currentNum + 1));
			} else {
				subList = list.subList(currentNum * oneSize, list.size());
				flag = false;
			}
			WillAnalysisData subData = willAnalysisData.clone();
			subData.setData(subList);
			String filePath = this.willAnalysisDataToFile(fieldId + "_" + currentNum, subData);
			//@todo 分析过程
			analysisThreadPool.execute(() -> System.out.println(Thread.currentThread().getName()));

			currentNum++;
		}
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

	// public void dataAnalysis(Map<String, Object> params) {
	// 	Long fieldId = Long.valueOf(String.valueOf(params.get("fieldId")));
	// 	Float threshold = Float.parseFloat(params.get("threshold").toString()) / 100;
	// 	Integer degree = (Integer) params.get("degree");
	// 	// 分析程度degree  1、快速分析 2、深度分析
	// 	String fileUrl = dataFieldValueService.getAnalysisData(fieldId, threshold);
	//
	// 	//  更新清洗池中分析状态->正在分析
	// 	DataField dataField = new DataField();
	// 	dataField.setId(fieldId);
	// 	dataField.setAnalyseState(DataCleanConstant.BEING_ANALYSIS);
	// 	dataField.setThreshold(threshold);
	// 	dataFieldService.update(dataField);
	//
	// 	//  数据分析接口
	// 	String result = calculateService.analysisSimilarity(degree, fileUrl);
	// 	//		String result = null;
	// 	// 判断分析是否成功(分析正确返回json数据,错误返回None)
	// 	if ("None".equals(result)) {
	// 		// 失败
	// 		dataField.setAnalyseState(DataCleanConstant.ERROR_ANALYSIS);
	// 		dataField.setNeedReanalysis(DataCleanConstant.TRUE);
	// 		dataFieldService.update(dataField);
	// 	} else {
	// 		// 算法分析前先将分析结果表中对应数据删除
	// 		this.delete(new EntityWrapper<AnalysisResult>().eq("field_id", fieldId));
	//
	// 		// 算法分析未集类,不进行处理
	// 		boolean flag = true;
	// 		if (StrUtil.isNotBlank(result) && !"[]".equals(result)) {
	// 			// 算法分析返回结果,存入数据库
	// 			flag = this.jsonStrSave(fieldId, result, DataCleanConstant.FALSE);
	// 		}
	// 		if (flag) {
	// 			// 成功
	// 			dataField.setAnalyseState(degree.equals(DataCleanConstant.QUICK_ANALYSIS) ? DataCleanConstant
	// .DONE_QUICK_ANALYSIS : DataCleanConstant.DONE_DEEP_ANALYSIS);
	// 			dataField.setNeedReanalysis(DataCleanConstant.FALSE);
	// 			dataFieldService.update(dataField);
	// 		} else {
	// 			// 出错
	// 			dataField.setAnalyseState(DataCleanConstant.ERROR_ANALYSIS);
	// 			dataFieldService.update(dataField);
	// 		}
	// 	}
	// }

}
