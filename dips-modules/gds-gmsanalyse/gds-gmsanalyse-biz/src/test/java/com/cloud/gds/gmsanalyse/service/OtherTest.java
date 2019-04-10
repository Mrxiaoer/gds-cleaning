package com.cloud.gds.gmsanalyse.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import lombok.Data;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-04
 */
public class OtherTest {

	@Test
	public void json() {
		String string = "{\n" +
			"\"informationId\":[{\n" +
			"\"name\":\"1112\",\n" +
			"\"sum\":\"ddd1\"\n" +
			"},{\n" +
			"\"name\":\"1121\",\n" +
			"\"sum\":\"dd1d\"\n" +
			"},{\n" +
			"\"name\":\"1411\",\n" +
			"\"sum\":\"d4dd\"\n" +
			"}],\n" +
			"\"summary\":\"dhsgfsagk hk\"\n" +
			"}";
		String query = "{\"informationId\":[{\"name\":\"111\",\"sum\":\"ddd\"},{\"name\":\"111\",\"sum\":\"ddd\"},{\"name\":\"111\",\"sum\":\"ddd\"}],\"summary\":\"dhsgfsagk hk\"}";
		JSONObject myJson = JSONObject.parseObject(query);
		Map<String, Object> map = (Map<String, Object>) myJson;
		Object a = map.get("informationId");
		String b = String.valueOf(map.get("summary"));
		Gson gson = new Gson();
		Parent parent = gson.fromJson(query, Parent.class);

		System.out.println(parent);

		List<Child> list = JSONArray.parseArray(a.toString(), Child.class);
//		for (Object object : jsonArray) {
//			Map<String, Object> map1 = (Map<String, Object>) JSONObject.parse(object.toString());
//			System.out.println("name: " + map1.get("name"));
//			System.out.println("sum: " + map1.get("sum"));
//		}
//		System.out.println(b);


	}


	@Data
	public class Parent {
		private String summary;
		private List<Child> informationId;
	}

	@Data
	public class Child {
		private String name;
		private String sum;
	}

}
