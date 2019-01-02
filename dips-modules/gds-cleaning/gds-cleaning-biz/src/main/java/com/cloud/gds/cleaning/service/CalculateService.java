package com.cloud.gds.cleaning.service;

/**
 * 清洗算法调用接口
 *
 * @author maoxinmin
 */
public interface CalculateService {

	/**
	 * 聚类分析及相似度计算接口
	 *
	 * @param analysisType 分析类型
	 * @param jsonFileUrl  json文件路径
	 * @return
	 */
	String similarity(int analysisType, String jsonFileUrl);

}
