package com.cloud.gds.cleaning.api.constant;

/**
 * 数据清洗的全局常量
 *
 * @Author : lolilijve
 * @Email : 1042703214@qq.com
 * @Date : 2018-11-21
 */
public interface DataCleanConstant {

	Integer YES = 1;

	Integer NO = 0;

	Long ZERO = 0L;

	Long ONE = 1L;

	/**
	 * 快速分析
	 */
	Integer QUICK_ANALYSIS = 1;

	/**
	 * 深度分析
	 */
	Integer DEEP_ANALYSIS = 2;

	/**
	 * 分析状态 0、未分析
	 */
	Integer NO_ANALYSIS = 0;
	/**
	 * 分析状态 1、正在分析
	 */
	Integer BEING_ANALYSIS = 1;
	/**
	 * 分析状态 2、已分析
	 */
	Integer DONE_ANALYSIS = 2;
	/**
	 * 分析状态 3、分析出错
	 */
	Integer ERROR_ANALYSIS = 3;

	String PAGE = "page";

	String LIMIT = "limit";

}
