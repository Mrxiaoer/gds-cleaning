package com.cloud.gds.preprocessing.service;

/**
 * 清洗国策数据无效值
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-19
 */
public interface InvalidPolicyService {

	/**
	 * 清除长度不符合问题数据
	 *
	 * @param titleLength
	 * @param textLength
	 * @return
	 */
	boolean cleanIssueData(Integer titleLength, Integer textLength);

	/**
	 * 清除采集表中相同名称的数据
	 *
	 * @return
	 */
	boolean cleanInvalidInScape();

	boolean cleanRepeatReal();

	/**
	 * 爬取数据与正式库中数据进行清洗,清洗到采集表中重复的数据
	 *
	 * @return
	 */
	boolean cleanRepeatScrapy();
}
