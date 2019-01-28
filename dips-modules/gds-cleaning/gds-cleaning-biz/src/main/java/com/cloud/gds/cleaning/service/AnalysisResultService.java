package com.cloud.gds.cleaning.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.gds.cleaning.api.dto.DataDto;
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
	 *
	 * @param params
	 */
	void smallDataAnalysis(Map<String, Object> params);

	/**
	 * 根据中心数据过滤
	 *
	 * @param centerId   中心id
	 * @param screenSize 滤网大小
	 * @return
	 */
	Map<String, Object> centerFiltration(Long centerId, Float screenSize);

	/**
	 * 非中心数据过滤
	 *
	 * @param nonCentral 更换中心id
	 * @param screenSize 滤网大小
	 * @return
	 */
	Map<String, Object> nonCentralFiltration(Long nonCentral, Float screenSize);

	/**
	 * 新节点分析
	 *
	 * @param dataDto
	 * @return
	 */
	Map<String, Object> centerPointFiltration(DataDto dataDto);
}
