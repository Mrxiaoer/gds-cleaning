package com.cloud.gds;

import com.cloud.gds.gmsanalyse.utils.SerializeUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-18
 */
public class CommonUtils {


	private static List<Integer> B(List<Integer> a, List<Integer> b) {
		a.addAll(b);
		return a;
	}


	@Test
	public void serializeTest() throws IOException, ClassNotFoundException {
		List<String> strings = new ArrayList<>();
		strings.add("2019年农产品质量安全工作");
		strings.add("了《2019年上海市农产品质量安全工作要点》");
		strings.add("乡村振兴战略, 四个严要求, 绿色优质农产品供给");

		// 序列化
		byte[] buf = null;
		try {
			buf = SerializeUtils.serializeObject(strings);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 反序列化
		List<String> object = (List<String>) SerializeUtils.deserializeObject(buf);

	}
}
