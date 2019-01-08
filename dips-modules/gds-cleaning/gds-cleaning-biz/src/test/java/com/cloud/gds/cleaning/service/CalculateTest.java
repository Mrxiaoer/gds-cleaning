package com.cloud.gds.cleaning.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CalculateTest {
	@Test
	public void PythonTest() {
		Long time1=System.currentTimeMillis();
		String jsonFileUrl = "/usr/local/data-cleaning/12.json";
		String result = null;
		String[] arguments = null;
		//配置python算法程序文件路径，python环境驱动并填入json文件所在路径
		//调用快速分析算法，采用层次聚类
		arguments = new String[]{"/anaconda3/bin/python",
			"/usr/local/data-cleaning/analysis_similarity/deepAnalyse.py", jsonFileUrl};

		try {
			//调用python的相似度计算程序
			Process process = Runtime.getRuntime().exec(arguments);
			//将得到的数据进行io处理
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				//读取计算得到的json数据
				result = line;
			}
			//关闭流
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Long time2=System.currentTimeMillis();
		//返回得到的json数据
		System.out.println(result);
		System.out.println((time2-time1)/1000);
	}
}
