package com.cloud.gds.preprocessing.service;

/**
 * 申报
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-05-09
 */
public interface InvalidDeclareService {

	/**
	 * 清楚无效数据
	 *
	 * @param titleLength 主题长度
	 * @param textLength  正文长度
	 * @return
	 */
	boolean cleanIssueData(Integer titleLength, Integer textLength);

	/**
	 * 清洗采集库中重复数据
	 *
	 * @return
	 */
	boolean cleanInvalidInScape();
}
