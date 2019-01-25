package com.cloud.gds.cleaning.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.dto.DataDto;
import com.cloud.gds.cleaning.api.dto.DataPoolAnalysis;
import com.cloud.gds.cleaning.api.dto.FilterParams;
import com.cloud.gds.cleaning.api.entity.AnalysisResult;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.vo.DARVo;
import com.cloud.gds.cleaning.api.vo.GroupVo;
import com.cloud.gds.cleaning.api.vo.ResultJsonVo;
import com.cloud.gds.cleaning.mapper.AnalysisResultMapper;
import com.cloud.gds.cleaning.mapper.DataFieldMapper;
import com.cloud.gds.cleaning.mapper.DataFieldValueMapper;
import com.cloud.gds.cleaning.service.AnalysisResultService;
import com.cloud.gds.cleaning.service.CalculateService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.service.DoAnalysisService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-28
 */
@Service
public class AnalysisResultServiceImpl extends ServiceImpl<AnalysisResultMapper, AnalysisResult> implements
	AnalysisResultService {

	private final DataFieldMapper dataFieldMapper;
	private final DataFieldValueService dataFieldValueService;
	private final CalculateService calculateService;
	private final DoAnalysisService doAnalysisService;

	@Autowired
	DataFieldValueMapper dataFieldValueMapper;

	@Autowired
	public AnalysisResultServiceImpl(DataFieldMapper dataFieldMapper, DataFieldValueService dataFieldValueService,
									 CalculateService calculateService, DoAnalysisService doAnalysisService) {
		this.dataFieldMapper = dataFieldMapper;
		this.dataFieldValueService = dataFieldValueService;
		this.calculateService = calculateService;
		this.doAnalysisService = doAnalysisService;
	}

	@Override
	public void smallDataAnalysis(Map<String, Object> params ) {
		Long fieldId = Long.valueOf(String.valueOf(params.get("fieldId")));
		Float threshold = Float.parseFloat(params.get("threshold").toString()) / 100;
		Integer degree = (Integer) params.get("degree");
		// 分析程度degree  1、快速分析 2、深度分析
		String fileUrl = doAnalysisService.getAllNeedAnalysisDataFile(fieldId, threshold);
		// 算法正在分析中
		updateAnaStateWithThreshold(fieldId, threshold, DataCleanConstant.BEING_ANALYSIS);
 		//  数据分析接口
 		String result = calculateService.analysisSimilarity(degree, fileUrl);
// 		String result = null;
 		// 判断分析是否成功(分析正确返回json数据,错误返回None)
 		if ("None".equals(result)) {
 			// 失败
			updateAnalyseStateWithNeedReanalyse(fieldId, DataCleanConstant.ERROR_ANALYSIS, DataCleanConstant.TRUE);
 		} else {
 			// 算法分析前先将分析结果表中对应数据删除
 			this.delete(new EntityWrapper<AnalysisResult>().eq("field_id", fieldId));

 			// 算法分析未集类,不进行处理
 			boolean flag = true;
 			if (StrUtil.isNotBlank(result) && !"[]".equals(result)) {
 				// 算法分析返回结果,存入数据库
 				flag = this.jsonStrSave(fieldId, result, DataCleanConstant.FALSE);
 			}
 			if (flag) {
 				// 成功
				updateAnalyseStateWithNeedReanalyse(fieldId, degree.equals(DataCleanConstant.QUICK_ANALYSIS) ? DataCleanConstant.DONE_QUICK_ANALYSIS : DataCleanConstant.DONE_DEEP_ANALYSIS, DataCleanConstant.FALSE);
 			} else {
 				// 出错
				updateAnalyseState(fieldId, DataCleanConstant.ERROR_ANALYSIS);
 			}
 		}
	}

	@Override
	public boolean automaticCleaning(Long fieldId) {
		// 查询相应清洗池的分析结果集
		List<AnalysisResult> results = this.selectList(new EntityWrapper<AnalysisResult>().eq("field_id", fieldId));

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
		return dataFieldValueService.updateBatchById(list) && this.delete(new EntityWrapper<AnalysisResult>().eq("field_id", fieldId));
	}

	@Override
	public Map<String, Object> centerFiltration(Long centerId, Float screenSize) {
		// 根据中心id与滤网大小查询滤出来的数据
		List<DataPoolAnalysis> results = dataFieldValueMapper.centerFiltration(centerId, screenSize / 100);

		List<DARVo> darVos = new ArrayList<>();
		if (results.size() == DataCleanConstant.FALSE) {
			return null;
		}
		// DataPoolAnalysis 转 DARVo
		for (DataPoolAnalysis result : results) {
			DARVo darVo = new DARVo();
			BeanUtils.copyProperties(result, darVo);
			darVo.setFieldValue(com.alibaba.fastjson.JSONObject.parseObject(result.getFieldValue()));
			// 百分比转换
			DecimalFormat df = new DecimalFormat(".00");
			darVo.setSimilarity(Double.parseDouble(df.format(darVo.getSimilarity() * 100)));
			darVos.add(darVo);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("centerId", centerId);
		map.put("list", darVos);
		return map;
	}

	@Override
	public Map<String, Object> nonCentralFiltration(Long nonCentral, Float screenSize) {
		// 获取当前数据主表是那一个
		DataFieldValue dataFieldValue = dataFieldValueService.selectById(nonCentral);

		// 封装过滤参数
		FilterParams filterParams = new FilterParams();
		filterParams.setFileId(dataFieldValue.getFieldId().toString());
		filterParams.setCenterId(nonCentral);
		filterParams.setThreshold(screenSize / 100);

		// 封装过滤参数model 转jsonStr
		String jsonStr = JSON.toJSONString(filterParams);

		// python 取过滤数据信息
		String resultJosn = calculateService.standardSimilarity(jsonStr);
		// python返回结果判断是否有值,是否有集类
		if (!"None".equals(resultJosn)) {
			// 结果数据不为空插入数据库中
			if (StrUtil.isNotBlank(resultJosn) && "[]".equals(resultJosn)) {
				//  删除旧中心值
				this.delete(new EntityWrapper<AnalysisResult>().eq("base_id", nonCentral));
				this.jsonStrSave(dataFieldValue.getFieldId(), resultJosn, DataCleanConstant.TRUE);
			}
		}

		// 取数据滤网大的数据
		Map<String, Object> list = this.centerFiltration(nonCentral, screenSize);
		return list;
	}

	@Override
	public Map<String, Object> centerPointFiltration(DataDto dataDto) {
		// 插入新数据
		DataFieldValue value = new DataFieldValue();
		value.setFieldId(dataDto.getFieldId());
		value.setFieldValue(dataDto.getFieldValue().toJSONString());
		assert SecurityUtils.getUser() != null;
		value.setCreateUser(SecurityUtils.getUser().getId());
		value.setCreateTime(LocalDateTime.now());
		dataFieldValueService.insert(value);

		DataField dataField = dataFieldMapper.selectById(dataDto.getFieldId());

		String fileUrl = doAnalysisService.getAllNeedAnalysisDataFile(dataDto.getFieldId(), dataField.getThreshold());

		// 重新聚类
		String result = calculateService.analysisSimilarity(DataCleanConstant.DEEP_ANALYSIS, fileUrl);

		// 判断分析是否成功(分析正确返回json数据,错误返回None)
		if ("None".equals(result)) {
			// 失败
			updateAnalyseState(dataField.getId(), DataCleanConstant.ERROR_ANALYSIS);
		} else {
			// 算法分析前先将分析结果表中对应数据删除
			this.delete(new EntityWrapper<AnalysisResult>().eq("field_id", dataDto.getFieldId()).eq("is_manual", DataCleanConstant.FALSE));

			// 算法分析返回结果,存入数据库
			boolean flag = true;
			if (StrUtil.isNotBlank(result) && !"[]".equals(result)) {
				// 算法分析返回结果,存入数据库
				flag = this.jsonStrSave(dataDto.getFieldId(), result, DataCleanConstant.FALSE);
			}
			if (flag) {
				// 成功
				updateAnalyseStateWithNeedReanalyse(dataField.getId(), DataCleanConstant.DONE_DEEP_ANALYSIS, DataCleanConstant.FALSE);
			} else {
				// 出错
				updateAnalyseState(dataField.getId(), DataCleanConstant.ERROR_ANALYSIS);
			}
		}
		// 根据标准数据过滤计算接口
		Map<String, Object> list = this.nonCentralFiltration(value.getId(), dataDto.getScreenSize());
		return list;
	}

	private Boolean jsonStrSave(Long fieldId, String result, Integer isManual) {
		// 算法分析返回结果->entity
		List<ResultJsonVo> list = JSON.parseArray(result, ResultJsonVo.class);

		// 结果存入数据库
		List<AnalysisResult> analysisResults = new ArrayList<>();
		for (ResultJsonVo jsonVo : list) {
			for (GroupVo vo : jsonVo.getGroup()) {
				AnalysisResult q = new AnalysisResult();
				q.setFieldId(fieldId);
				q.setBaseId(jsonVo.getId());
				q.setCompareId(vo.getId());
				q.setSimilarity(vo.getSimilarity());
				// 是否手动分析(0、自动 1、手动)
				q.setIsManual(isManual);
				analysisResults.add(q);
			}
		}
		this.insertBatch(analysisResults);

		return true;
	}

	/**
	 * 更新清洗池中的分析状态以及阀值
	 *
	 * @param fieldId 清洗池id
	 * @param threshold 阀值
	 * @param analyseState 分析状态值
	 */
	private void updateAnaStateWithThreshold(Long fieldId,Float threshold,Integer analyseState){
		DataField dataField = new DataField();
		dataField.setId(fieldId);
		dataField.setAnalyseState(analyseState);
		dataField.setThreshold(threshold);
		dataField.setModifiedUser(SecurityUtils.getUser().getId());
		dataField.setModifiedTime(LocalDateTime.now());
		dataFieldMapper.updateById(dataField);
	}

	/**
	 * 更新清洗池中的算法分析状态
	 *
	 * @param fieldId 清洗池id
	 * @param analyseState 分析状态值
	 */
	private void updateAnalyseState(Long fieldId,Integer analyseState){
		DataField dataField = new DataField();
		dataField.setId(fieldId);
		dataField.setAnalyseState(analyseState);
		dataField.setModifiedUser(SecurityUtils.getUser().getId());
		dataField.setModifiedTime(LocalDateTime.now());
		dataFieldMapper.updateById(dataField);
	}

	/**
	 * 更新清洗池中的算法分析状态和是否需要重新分析状态
	 *
	 * @param fieldId 清洗池id
	 * @param analyseState 分析状态值
	 */
	private void updateAnalyseStateWithNeedReanalyse(Long fieldId,Integer analyseState,Integer needReanalysisState ){
		DataField dataField = new DataField();
		dataField.setId(fieldId);
		dataField.setAnalyseState(analyseState);
		dataField.setNeedReanalysis(needReanalysisState);
		dataField.setModifiedUser(SecurityUtils.getUser().getId());
		dataField.setModifiedTime(LocalDateTime.now());
		dataFieldMapper.updateById(dataField);
	}



}
