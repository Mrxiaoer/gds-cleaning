package com.cloud.gds.cleaning.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.AnalysisResult;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.vo.GroupVo;
import com.cloud.gds.cleaning.api.vo.ResultJsonVo;
import com.cloud.gds.cleaning.mapper.AnalysisResultMapper;
import com.cloud.gds.cleaning.service.AnalysisResultService;
import com.cloud.gds.cleaning.service.CalculateService;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public AnalysisResultServiceImpl(DataFieldService dataFieldService, DataFieldValueService dataFieldValueService,
									 CalculateService calculateService) {
		this.dataFieldService = dataFieldService;
		this.dataFieldValueService = dataFieldValueService;
		this.calculateService = calculateService;
	}

	@Override
	public void dataAnalysis(Map<String, Object> params) {
		Long fieldId = Long.valueOf(String.valueOf(params.get("fieldId")));
		Float threshold = Float.parseFloat(params.get("threshold").toString())/100;
		Integer degree = (Integer) params.get("degree");
		// 分析程度degree  1、快速分析 2、深度分析
		// 由于前端传过来的阀值是100作为基数,因此需要转化
		String fileUrl = dataFieldValueService.getAnalysisData(fieldId, threshold);

		//  更新清洗池中分析状态->正在分析
		DataField dataField = new DataField();
		dataField.setId(fieldId);
		dataField.setAnalyseState(DataCleanConstant.BEING_ANALYSIS);
		dataField.setThreshold(threshold);
		dataFieldService.update(dataField);

		//  数据分析接口
		String result = calculateService.analysisSimilarity(degree, fileUrl);

		// 判断分析是否成功(分析正确返回json数据,错误返回None)
		if ("None".equals(result)) {
			// 失败
			dataField.setAnalyseState(DataCleanConstant.ERROR_ANALYSIS);
			dataFieldService.update(dataField);
		} else {
			// 算法分析前先将分析结果表中对应数据删除
			this.delete(new EntityWrapper<AnalysisResult>().eq("field_id", fieldId));

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
					q.setIsManual(DataCleanConstant.NO);
					analysisResults.add(q);
				}
			}
			this.insertBatch(analysisResults);

			// 成功
			dataField.setAnalyseState(DataCleanConstant.DONE_ANALYSIS);
			dataFieldService.update(dataField);
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
		for (AnalysisResult analysisResult : results) {
			if (analysisResult.getBaseId().equals(analysisResult.getCompareId())) {
				results.remove(analysisResult);
				continue;
			}
			DataFieldValue dataFieldValue = new DataFieldValue();
			dataFieldValue.setId(analysisResult.getCompareId());
			dataFieldValue.setBeCleanedId(analysisResult.getBaseId());
			// 由于数据被清洗了,对数据进行删除状态的更新
			dataFieldValue.setIsDeleted(DataCleanConstant.NO);
			assert SecurityUtils.getUser() != null;
			dataFieldValue.setModifiedUser(SecurityUtils.getUser().getId());
			dataFieldValue.setModifiedTime(LocalDateTime.now());
			list.add(dataFieldValue);
		}
		// 清洗数据,数据被清洗后要将分析结表中相应数据删除
		return dataFieldValueService.updateBatchById(list) && this.delete(new EntityWrapper<AnalysisResult>().eq("field_id", fieldId));
	}

}
