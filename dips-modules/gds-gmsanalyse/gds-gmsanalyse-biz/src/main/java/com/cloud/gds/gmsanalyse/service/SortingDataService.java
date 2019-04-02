package com.cloud.gds.gmsanalyse.service;

public interface SortingDataService {
	/**
	 * 匹配特殊字符和空格，并添加全文搜索匹配符
	 */
	String replaceSpecialSign(Object titleSObject);
}
