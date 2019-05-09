package com.cloud.gds.preprocessing.service;

/**
 * 资讯
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-05-09
 */
public interface InvalidInformationService {

	/**
	 * 清洗无效数据
	 *
	 * @param titleLength 标题长度
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

	/**
	 * 爬取数据与正式库中数据进行清洗,清洗到采集表中重复的数据
	 *
	 * @return
	 */
	boolean cleanRepeatScrapy();
}
