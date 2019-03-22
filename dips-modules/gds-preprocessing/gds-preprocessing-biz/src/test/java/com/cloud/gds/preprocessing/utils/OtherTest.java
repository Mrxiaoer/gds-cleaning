package com.cloud.gds.preprocessing.utils;

import org.junit.Test;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-22
 */
public class OtherTest {

	@Test
	public void f() {
		String info = "mldn";
		final String info1 = info;
		final String info2 = "mldn";
		String A = "www." + info + ".fun";
		String B = "www." + info1 + ".fun";
		String C = "www." + info2 + ".fun";
		String D = "www.mldn.fun";
		System.out.println(A == D);
		System.out.println(B == D);
		System.out.println(C == D);
	}

}
