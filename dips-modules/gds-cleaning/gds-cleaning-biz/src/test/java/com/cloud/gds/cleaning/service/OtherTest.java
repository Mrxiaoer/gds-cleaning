package com.cloud.gds.cleaning.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.time.LocalTime;
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
	public void timeTest1() {
		long start = System.currentTimeMillis();
		long xx = 0;
		for (int i = 0; i < 99999; i++) {
			xx = System.currentTimeMillis();
		}
		long end = System.currentTimeMillis();
		System.out.println("==>" + (end - start) + "====" + xx);
	}

	@Test
	public void timeTest2() {
		long start = System.currentTimeMillis();
		LocalTime xx = null;
		for (int i = 0; i < 99999; i++) {
			xx = LocalTime.now();
		}
		long end = System.currentTimeMillis();
		System.out.println("==>" + (end - start) + "====" + xx);
	}

	@Test
	public void xxxx() {
		System.out.println(StrUtil.isNotBlank(null));
	}

	@Test
	public void Test() {
		Integer a = new Integer(200);
		Integer b = new Integer(200);
		Integer c = 200;
		Integer e = 200;
		int d = 200;

		System.out.println("两个new出来的对象    ==判断" + (a == b));
		System.out.println("两个new出来的对象    equal判断" + a.equals(b));
		System.out.println("new出的对象和用int赋值的Integer   ==判断" + (a == c));
		System.out.println("new出的对象和用int赋值的Integer   equal判断" + (a.equals(c)));
		System.out.println("两个用int赋值的Integer    ==判断" + (c == e));
		System.out.println("两个用int赋值的Integer    equal判断" + (c.equals(e)));
		System.out.println("基本类型和new出的对象   ==判断" + (d == a));
		System.out.println("基本类型和new出的对象   equal判断" + (a.equals(d)));
		System.out.println("基本类型和自动装箱的对象   ==判断" + (d == c));
		System.out.println("基本类型和自动装箱的对象   equal判断" + (c.equals(d)));
	}

}
