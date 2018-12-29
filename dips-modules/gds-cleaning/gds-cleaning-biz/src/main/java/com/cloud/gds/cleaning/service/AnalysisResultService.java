package com.cloud.gds.cleaning.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.gds.cleaning.api.entity.AnalysisResult;

import java.util.Map;

/**
 * 算法结果分析
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-28
 */
public interface AnalysisResultService extends IService<AnalysisResult> {

	/**
	 * python 调用算法结果处理
	 * @param fieldId
	 * @param threshold
	 * @param degree
	 */
	void dataAnalysis(Long fieldId,Float threshold,Integer degree);

	/**
	 * 手动过滤
	 * @param params
	 * @return
	 */
	Boolean manualFilter(Map<String,Object> params);

}
