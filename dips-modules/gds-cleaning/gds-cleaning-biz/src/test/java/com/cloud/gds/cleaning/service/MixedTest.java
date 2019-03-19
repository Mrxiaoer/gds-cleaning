package com.cloud.gds.cleaning.service;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-15
 */
public class MixedTest {

	@Test
	public void hashSet() {

		String a = "http://www.yjbys.com/";
		String b = "http://www.yjbys.com/";
		String c = "http://www.yjby1s.com/11";
		Set<Object> set = new HashSet<>();

		set.add(a);
		set.add(c);
		if (set.contains(b)){
			System.out.println("存在");
		}else {
			System.out.println("不存在");
		}
		set.add(b);
		System.out.println(set);
	}

	@Test
	public void getAllHave(){
		Set<String> set1 = new HashSet<>();
		Set<String> set2 = new HashSet<>();
		set1.add("1");
		set1.add("2");
		set1.add("3");
		set2.add("2");
		set2.add("3");
		set2.add("4");

		System.out.println(set1.retainAll(set2));
		System.out.println(set1);

	}


}
