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
import com.cloud.gds.cleaning.mapper.DataFieldValueMapper;
import com.cloud.gds.cleaning.service.AnalysisResultService;
import com.cloud.gds.cleaning.service.CalculateService;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.utils.CommonUtils;
import com.cloud.gds.cleaning.utils.DataRuleUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	private final DataFieldService dataFieldService;
	private final DataFieldValueService dataFieldValueService;
	private final CalculateService calculateService;

	@Autowired
	DataFieldValueMapper dataFieldValueMapper;

	@Autowired
	public AnalysisResultServiceImpl(DataFieldService dataFieldService, DataFieldValueService dataFieldValueService,
									 CalculateService calculateService) {
		this.dataFieldService = dataFieldService;
		this.dataFieldValueService = dataFieldValueService;
		this.calculateService = calculateService;
	}

	@Override
	public void dataAnalysis(Map<String, Object> params) {
		Long fieldId = Long.valueOf(String.valueOf(params.get("fieldId")));
		Float threshold = Float.parseFloat(params.get("threshold").toString()) / 100;
		Integer degree = (Integer) params.get("degree");
		// 分析程度degree  1、快速分析 2、深度分析
		String fileUrl = dataFieldValueService.getAnalysisData(fieldId, threshold);

		//  更新清洗池中分析状态->正在分析
		DataField dataField = new DataField();
		dataField.setId(fieldId);
		dataField.setAnalyseState(DataCleanConstant.BEING_ANALYSIS);
		dataField.setThreshold(threshold);
		dataFieldService.update(dataField);

		//  数据分析接口
//		String result = calculateService.analysisSimilarity(degree, fileUrl);
		String result = null;
		// 判断分析是否成功(分析正确返回json数据,错误返回None)
		if ("None".equals(result)) {
			// 失败
			dataField.setAnalyseState(DataCleanConstant.ERROR_ANALYSIS);
			dataFieldService.update(dataField);
		} else {
			// 算法分析前先将分析结果表中对应数据删除
			this.delete(new EntityWrapper<AnalysisResult>().eq("field_id", fieldId));

			// 算法分析未集类,不进行处理
			boolean flag = true;
			if (StrUtil.isNotBlank(result)) {
				// 算法分析返回结果,存入数据库
				flag = this.jsonStrSave(fieldId, result, DataCleanConstant.NO);
			}

			if (flag) {
				// 成功
				dataField.setAnalyseState(DataCleanConstant.DONE_ANALYSIS);
				dataFieldService.update(dataField);
			} else {
				// 出错
				dataField.setAnalyseState(DataCleanConstant.ERROR_ANALYSIS);
				dataFieldService.update(dataField);
			}
		}
	}


	@Override
	public boolean deleteAllById(Long id) {
		AnalysisResult before1 = new AnalysisResult();
		before1.setBaseId(id);
		AnalysisResult before2 = new AnalysisResult();
		before2.setCompareId(id);
		return this.delete(new EntityWrapper<>(before1)) && this.delete(new EntityWrapper<>(before2));
	}

	@Override
	public boolean deleteAllByIds(Set<Long> ids) {
		return this.delete(new EntityWrapper<AnalysisResult>().in("base_id", ids)) && this.delete(new EntityWrapper<AnalysisResult>().in("compare_id", ids));
	}

	@Override
	public boolean automaticCleaning(Long fieldId) {
		// 查询相应清洗池的分析结果集
		List<AnalysisResult> results = this.selectList(new EntityWrapper<AnalysisResult>().eq("field_id", fieldId));

		// 组装待清洗数据
		List<DataFieldValue> list = new ArrayList<>();
		if (results.size() == DataCleanConstant.NO) {
			return true;
		}
		for (AnalysisResult analysisResult : results) {
			if (!analysisResult.getBaseId().equals(analysisResult.getCompareId())) {
				DataFieldValue dataFieldValue = new DataFieldValue();
				dataFieldValue.setId(analysisResult.getCompareId());
				dataFieldValue.setBeCleanedId(analysisResult.getBaseId());
				dataFieldValue.setFieldId(analysisResult.getFieldId());
				// 由于数据被清洗了,对数据进行删除状态的更新
				dataFieldValue.setIsDeleted(DataCleanConstant.YES);
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
	public List<DARVo> centerFiltration(Long centerId, Float screenSize) {
		// 根据中心id与滤网大小查询滤出来的数据
		List<DataPoolAnalysis> results = dataFieldValueMapper.centerFiltration(centerId, screenSize / 100);

		List<DARVo> darVos = new ArrayList<>();
		if (results.size() == DataCleanConstant.NO) {
			return darVos;
		}
		// DataPoolAnalysis 转 DARVo
		for (DataPoolAnalysis result : results) {
			DARVo darVo = new DARVo();
			BeanUtils.copyProperties(result, darVo);
			darVo.setFieldValue(com.alibaba.fastjson.JSONObject.parseObject(result.getFieldValue()));
			// 百分比转换
			darVo.setSimilarity(darVo.getSimilarity() * 100);
			darVos.add(darVo);
		}
		return darVos;
	}

	@Override
	public List<DARVo> nonCentralFiltration(Long nonCentral, Float screenSize) {
		// 获取当前数据主表是那一个
		DataFieldValue dataFieldValue = dataFieldValueService.selectById(nonCentral);

		// 封装过滤参数
		FilterParams filterParams = new FilterParams();
		filterParams.setFileId(nonCentral);
		filterParams.setCenterId(dataFieldValue.getFieldId());
		filterParams.setThreshold(screenSize / 100);

		// 封装过滤参数model 转jsonStr
		String jsonStr = JSON.toJSONString(filterParams);

		// python 取过滤数据信息
		String resultJosn = calculateService.standardSimilarity(jsonStr);

		// todo 解决resultJosn 2019-1-9 11:07:10
		if (!"None".equals(jsonStr)) {
			// 结果数据不为空插入数据库中
			if (StrUtil.isNotBlank(resultJosn)) {
				this.jsonStrSave(dataFieldValue.getFieldId(), resultJosn, DataCleanConstant.YES);
			}
		}

		// 取数据滤网大的数据
		List<DARVo> list = this.centerFiltration(nonCentral, screenSize);

		return list;
	}

	@Override
	public List<DARVo> centerPointFiltration(DataDto dataDto) {
		// 插入新数据
		DataFieldValue value = new DataFieldValue();
		value.setFieldId(dataDto.getFieldId());
		value.setFieldValue(dataDto.getFieldValue().toJSONString());
		assert SecurityUtils.getUser() != null;
		value.setCreateUser(SecurityUtils.getUser().getId());
		value.setCreateTime(LocalDateTime.now());
		dataFieldValueService.insert(value);

		DataField dataField = dataFieldService.selectById(dataDto.getFieldId());

		String fileUrl = dataFieldValueService.getAnalysisData(dataDto.getFieldId(), dataField.getThreshold());

		// 重新聚类
		String result = calculateService.analysisSimilarity(DataCleanConstant.QUICK_ANALYSIS, fileUrl);

		// 判断分析是否成功(分析正确返回json数据,错误返回None)
		if ("None".equals(result)) {
			// 失败
			dataField.setAnalyseState(DataCleanConstant.ERROR_ANALYSIS);
			dataFieldService.update(dataField);
		} else {
			// 算法分析前先将分析结果表中对应数据删除
			this.delete(new EntityWrapper<AnalysisResult>().eq("field_id", dataDto.getFieldId()));

			// 算法分析返回结果,存入数据库
			boolean flag = this.jsonStrSave(dataDto.getFieldId(), result, DataCleanConstant.NO);
		}
		// 根据标准数据过滤计算接口
		List<DARVo> list = this.nonCentralFiltration(value.getId(), dataDto.getScreenSize());
		return list;
	}

	@Override
	public List<DARVo> filterMethod(DataDto dataDto) {
		// 判断数据是否修改过
		List<DARVo> list = new ArrayList<>();
		SortedMap<String, String> one = DataRuleUtils.strToSortedMap(dataDto.getFieldValue().toJSONString());
		SortedMap<String, String> two = DataRuleUtils.strToSortedMap(dataFieldValueMapper.selectById(dataDto.getId()).getFieldValue());
		Boolean flag = CommonUtils.checkSortedMap(one, two);
		// 如果返回正确证明未修改
		if (flag) {
			if (dataDto.getSimilarity() == 100) {
				// 中心数据清洗
				list = this.centerFiltration(dataDto.getId(), dataDto.getScreenSize());
			} else {
				list = this.nonCentralFiltration(dataDto.getId(), dataDto.getScreenSize());
			}
		} else {
			list = this.centerPointFiltration(dataDto);
		}
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

}
