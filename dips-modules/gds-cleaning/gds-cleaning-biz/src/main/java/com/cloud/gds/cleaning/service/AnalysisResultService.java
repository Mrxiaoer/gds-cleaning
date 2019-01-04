package com.cloud.gds.cleaning.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.gds.cleaning.api.entity.AnalysisResult;

import java.util.Map;
import java.util.Set;

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

	/**
	 * 根据id删除中心、卫星相关分析结果集
	 * @param id
	 * @return
	 */
	boolean deleteAllById(Long id);

	/**
	 * 根据ids删除中心、卫星相关分析结果集
	 * @param ids
	 * @return
	 */
	boolean deleteAllByIds(Set<Long> ids);

	/**
	 * 自动清洗
	 *
	 * @param fieldId
	 * @return
	 */
	boolean automaticCleaning(Long fieldId);

}
