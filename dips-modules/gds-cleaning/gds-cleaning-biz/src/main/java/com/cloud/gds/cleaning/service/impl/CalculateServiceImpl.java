package com.cloud.gds.cleaning.service.impl;

import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.service.CalculateService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 清洗算法调用接口实现方法
 *
 * @Author : maoxinmin
 * @Email : 331228638@qq.com
 * @Date : 2018-12-27
 */
@Service
public class CalculateServiceImpl implements CalculateService {


	/**
	 * 数据分析聚类及数据相似度计算接口调用
	 */
	@Override
	public String analysisSimilarity(int analysisType, String jsonFileUrl) {
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.println("path:"+path);
		String result = null;
		String[] arguments = null;
		//配置python算法程序文件路径，python环境驱动并填入json文件所在路径
		if (analysisType == DataCleanConstant.QUICK_ANALYSIS) {
			//调用快速分析算法，采用层次聚类
			arguments = new String[]{"/anaconda3/bin/python",
				path + "/analysis_similarity/quickAnalyse.py", jsonFileUrl};
		} else if (analysisType == DataCleanConstant.DEEP_ANALYSIS) {
			//调用深度分析算法，计算矩阵，采用DBSCAN算法
			arguments = new String[]{"/anaconda3/bin/python",
				path + "/analysis_similarity/deepAnalyse.py", jsonFileUrl};
		}
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

	@Override
	public String standardSimilarity(String jsonStr) {
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String result = null;
		//配置正确的python环境驱动并引入标准json数据
		String[] arguments = new String[]{"/anaconda3/bin/python",
			path + "/analysis_similarity/centroidAnalyse.py", jsonStr};
		try {
			//调用python标准分析程序
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
