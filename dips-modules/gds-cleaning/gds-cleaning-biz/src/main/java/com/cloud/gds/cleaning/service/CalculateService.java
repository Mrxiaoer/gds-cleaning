package com.cloud.gds.cleaning.service;

/**
 * 清洗算法调用接口
 * @author maoxinmin
 */
public interface CalculateService {

	/**
	 * 计算相似度
	 * @param str
	 * @return
	 */
	public String Similarity(String str);
}
