package com.cloud.gds.cleaning.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.Test;

/**
 * 零碎的测试
 *
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2018-12-19
 */
public class OtherTest {

	@Test
	public void JSONObjRemoveTest() {
		String str = "{\"name\": \"222\",\"length\": 8,\"age\": 9,\"city\": \"中国浙江\"}";
		JSONObject jsonObj = JSONUtil.parseObj(str);
		jsonObj.remove("name");
		System.out.println(jsonObj);
	}

	@Test
	public void JSONOTest() {
		// System.out.println(com.alibaba.fastjson.JSONObject.parseObject("2"));
		System.out.println(JSONUtil.isJsonObj(""));
		System.out.println(JSONUtil.isJsonObj("{'name':2}"));
		// JSONUtil.parseObj("",false);
	}
	@Test
	public void xxx() {
		Integer i = 1;
		Integer j = 1;
		System.out.println(i.equals(j));
	}
}
