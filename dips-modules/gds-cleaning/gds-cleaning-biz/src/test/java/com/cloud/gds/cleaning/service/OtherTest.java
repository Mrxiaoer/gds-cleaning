package com.cloud.gds.cleaning.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import lombok.Data;
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

	@Test
	public void random() {
		Random r = new Random(1000);
		for (int i = 1; i <= 4; i++) {
			System.out.println("第" + i + "次:" + r.nextInt());
		}
	}

	@Test
	public void HTMLEncoding() {
		String url = "https://hei-ha.oss-cn-beijing.aliyuncs"
			+ ".com/file/20190104/%E5%9C%A8%E7%BA%BF%E9%A2%84%E8%A7%88%E6%B5%8B"
			+ "%E8%AF%95.docx?Expires=1546570089&OSSAccessKeyId=TMP"
			+ ".AQFjEfOj9O8mY87VRqHzv1cgAgKuqufDJlhZPL9iHKhrr9nXvFnITdPHJf1-ADAtAhQOvg9AHXubg_YgYD"
			+ "-3huq7fAFsBgIVAOdM3jwMH_Kl5XOc9bnpuY89bqlE&Signature=%2FBexsDyAnbuE49vO07DC1iZKpjE%3D";

		String result = "https://view.officeapps.live.com/op/view.aspx?src=";

		try {
			result += URLEncoder.encode(url, "ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		System.out.println(result);
	}

	@Test
	public void smileTest1(){
		Person xx=new Person();
		xx.setName("wyn");
		xxxxx(xx);
		System.out.println(xx.toString());
	}

	private void xxxxx(Object xx){
		// xx=new Person();
		((Person) xx).setAge(18);
	}

	@Data
	class Person{
		int age;
		String name;
		int sex;
	}

	@Test
	public void cleanBlank(){
		// xx=new Person();
		String str = StrUtil.trim("\n" + "                \n" + "                \n" + "               \n"
			+ "2018-10-30               \n" + "               ");
		System.out.println("="+str+"=");
	}
}
