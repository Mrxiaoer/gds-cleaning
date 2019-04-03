package com.cloud.gds.gms.service.impl;

import com.cloud.gds.gms.service.GovAnalyseService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 政策分析接口
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-02
 */
@Service
public class GovAnalyseServiceImpl implements GovAnalyseService {

	public String gain(List<Long> ids) {
		String result = null;
		String[] arguments = null;
		//配置python算法程序文件路径，python环境驱动并填入json文件所在路径
		arguments = new String[]{"/anaconda3/bin/python","/usr/local/data-cleaning/analysis_similarity/deepAnalyse.py", ids.toString()};

		try {
			//调用python 分析政策情况
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
