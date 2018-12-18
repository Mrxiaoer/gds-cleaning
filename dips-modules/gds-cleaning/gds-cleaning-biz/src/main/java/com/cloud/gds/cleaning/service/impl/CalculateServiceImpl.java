package com.cloud.gds.cleaning.service.impl;

import com.cloud.gds.cleaning.service.CalculateService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 清洗算法调用接口实现方法
 *
 * @Author : maoxinmin
 * @Email : 331228638@qq.com
 * @Date : 2018-12-11
 */
@Service
public class CalculateServiceImpl implements CalculateService {

	@Override
	public String Similarity(String str) {
		String result = null;
		//配置正确的文件路径和python环境驱动并引入json数据
		String[] arguments = new String[]{"/anaconda3/bin/python", "/usr/local/python-files/phrase_similarity/calculation.py", str};
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
		//返回得到的json数据
		return result;

	}
}
