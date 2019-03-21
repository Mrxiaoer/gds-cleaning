package com.cloud.gds.preprocessing.utils;


import org.junit.Test;

import javax.sql.rowset.FilteredRowSet;
import java.util.ArrayList;
import java.util.List;

public class ConversionUtilsTest {

	@Test
	public void testStyle(){
		// （1|通知 2|公告 3|报告 4|意见 5|办法 6|通报 7|其他）
		List<String> list = new ArrayList<>();
		list.add("通知1");
		list.add("公告1");
		list.add("报告");
		list.add("意见");
		list.add("办法");
		list.add("");
		list.add("通报");
		list.add("其他");
		for (String s:list){
			Integer i = ConversionUtils.styleS2I(s);
			System.out.println(i);
		}

	}
}
