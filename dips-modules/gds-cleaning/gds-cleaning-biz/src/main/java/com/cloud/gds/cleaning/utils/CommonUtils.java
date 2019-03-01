package com.cloud.gds.cleaning.utils;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;

/**
 * 常用工具类的封装
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-03
 */
public class CommonUtils {

	/**
	 * 2个SortedMap比较是否一致
	 *
	 * @param one
	 * @param two
	 * @return
	 */
	public static Boolean checkSortedMap(SortedMap<String, String> one, SortedMap<String, String> two) {
		Set<Map.Entry<String, String>> es = one.entrySet();
		for (Entry<String, String> entry : es) {
			String k = (String) entry.getKey();
			if (two.containsKey(k)) {
				two.remove(k);
			} else {
				return false;
			}
		}
		return two.size() <= 0;
	}

	/**
	 * 生成uuid
	 *
	 * @return
	 */
	public static String generateUUID() {
		String uuid = UUID.randomUUID().toString()
			.replaceAll("-", "").substring(0, 32);
		return uuid;
	}

}
