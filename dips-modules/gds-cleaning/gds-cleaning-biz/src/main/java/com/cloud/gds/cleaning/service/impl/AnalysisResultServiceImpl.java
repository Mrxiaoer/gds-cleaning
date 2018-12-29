package com.cloud.gds.cleaning.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.AnalysisResult;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.vo.GroupVo;
import com.cloud.gds.cleaning.api.vo.ResultJsonVo;
import com.cloud.gds.cleaning.mapper.AnalysisResultMapper;
import com.cloud.gds.cleaning.service.AnalysisResultService;
import com.cloud.gds.cleaning.service.CalculateService;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-28
 */
@Service
public class AnalysisResultServiceImpl extends ServiceImpl<AnalysisResultMapper, AnalysisResult>
	implements AnalysisResultService {

	@Autowired
	DataFieldService dataFieldService;

	@Autowired
	DataFieldValueService dataFieldValueService;

	@Autowired
	CalculateService calculateService;


	@Override
	public void dataAnalysis(Long fieldId, Float threshold, Integer degree) {
		// 分析程度degree  1、快速分析 2、深度分析
		String fileUrl = dataFieldValueService.getAnalysisData(fieldId,threshold);

		//  更新清洗池中分析状态->正在分析
		DataField dataField = new DataField();
		dataField.setId(fieldId);
		dataField.setAnalyseState(DataCleanConstant.BEING_ANALYSIS);
		dataFieldService.update(dataField);

		//  数据分析接口
		String result =  calculateService.Similarity(degree, fileUrl);

		// 判断分析是否成功(分析正确返回json数据,错误返回None)
		if ("None".equals(result)){
			// 失败
			dataField.setAnalyseState(DataCleanConstant.ERROR_ANALYSIS);
			dataFieldService.update(dataField);
		}else {
			// 算法分析前先将分析结果表中对应数据删除 todo 考虑是否如此使用
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
	public Boolean manualFilter(Map<String, Object> params) {

		return null;
	}

}
