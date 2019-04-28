package com.cloud.gds.gmsanalyse.service.impl;

import com.cloud.gds.gmsanalyse.bo.DeconstructionListBo;
import com.cloud.gds.gmsanalyse.service.AnalyseAlgorithmService;
import com.cloud.gds.gmsanalyse.vo.Analyse;
import com.cloud.gds.gmsanalyse.vo.Child;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 政策分析算法分析
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-02
 */
@Service
public class AnalyseAlgorithmServiceImpl implements AnalyseAlgorithmService {

	@Override
	public Analyse policyWonk(DeconstructionListBo bo, Integer featureNum) {
		String result = "";
		// 统计词频
		ArrayList<Map.Entry<Object, Integer>> list = countWordFrequency(bo.getOne());
		// 特征词与数量
		List<Map.Entry<Object, Integer>> subList = list.subList(0, featureNum);

		List<Child> childList = spliceFeatureChild(subList);

		List<String> stringList = new ArrayList<>();
		//分析每个词对应政策的情况
		for (Map.Entry<Object, Integer> entry : subList) {
			// 当前词
			String s = String.valueOf(entry.getKey());
			List<Long> oneList = new ArrayList<>();
			for (int i = 0; i < bo.getOne().size(); i++) {
				if (bo.getOne().get(i).equals(s)) {
//					System.out.println(i);
					oneList.add(bo.getTwo().get(i));
				}
			}
			ArrayList<Map.Entry<Object, Integer>> tempList = countWordFrequency(oneList);
			// 当前词总结性政策情况
			String s1 = spliceString(s, tempList, bo.getMap());
			stringList.add(s1);
		}
		// 拼接各词总结性的语句
		if (stringList.size() > 0) {
			result = spliceSummary(stringList);
		}
		Analyse analyse = new Analyse();
		analyse.setList(childList);
		analyse.setSummary(result);
		return analyse;
	}

	/**
	 * list列表数据统计与排序操作
	 *
	 * @param one 排序的数组
	 * @param <T> 排序数据类型
	 * @return
	 */
	private <T> ArrayList<Map.Entry<Object, Integer>> countWordFrequency(List<T> one) {
		Map<Object, Integer> map = new TreeMap<Object, Integer>();
		Set uniqueSet = new HashSet(one);
		for (Object temp : uniqueSet) {
			map.put(temp, Collections.frequency(one, temp));
		}
		// 排序 倒序
		ArrayList<Map.Entry<Object, Integer>> list1 = new ArrayList<>(map.entrySet());
		Collections.sort(list1, new Comparator<Map.Entry<Object, Integer>>() {
			@Override
			public int compare(Map.Entry<Object, Integer> o1, Map.Entry<Object, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		return list1;
	}

	/**
	 * 拼装特征词与数量
	 *
	 * @param subList
	 * @return
	 */
	private List<Child> spliceFeatureChild(List<Map.Entry<Object, Integer>> subList) {
		List<Child> childList = new ArrayList<>();
		for (Map.Entry<Object, Integer> entry : subList) {
			Child child = new Child();
			child.setFeatureName(String.valueOf(entry.getKey()));
			child.setFeatureNum(entry.getValue());
			childList.add(child);
		}
		return childList;
	}

	/**
	 * 拼装单词总结语句
	 *
	 * @param s        政策词名称
	 * @param tempList 政策id统计集
	 * @param titleMap 政策名称集合
	 * @return
	 */
	private String spliceString(String s, ArrayList<Map.Entry<Object, Integer>> tempList, Map<Long, String> titleMap) {
		String s1 = "提到 " + s + " 行为的政策有";
		// 现阶段取前5个政策
		List<Map.Entry<Object, Integer>> tempListBeing;
		if (tempList.size() >= 5) {
			tempListBeing = tempList.subList(0, 5);
		} else {
			tempListBeing = tempList.subList(0, tempList.size());
		}
		for (Map.Entry<Object, Integer> temp : tempListBeing) {
			s1 = s1 + "《" + titleMap.get(temp.getKey()) + "》";
		}
		s1 = s1 + "等" + tempList.size() + "篇政策,其中《" + titleMap.get(tempList.get(0).getKey()) + "》" + "出现的次数最多，共出现" + tempList.get(0).getValue() + "次";
		System.out.println(s1);
		return s1;

	}

	/**
	 * 拼装总结语句
	 *
	 * @param stringList 各词总结字符串
	 * @return
	 */
	private String spliceSummary(List<String> stringList) {
		String result = "";
		for (int i = 0; i < stringList.size() - 1; i++) {
			result += stringList.get(i) + ";";
		}
		result += stringList.get(stringList.size() - 1) + "。";
		return result;
	}

	@Override
	public String govAnalysis(List<Long> ids) {
		String result = null;
		String[] arguments = null;
		//配置python算法程序文件路径，python环境驱动并填入json文件所在路径
		arguments = new String[]{"/anaconda3/bin/python", "/usr/local/data-cleaning/analysis_similarity/deepAnalyse.py", ids.toString()};

		try {
			String line = null;
			//调用python 分析政策情况
			Process process = Runtime.getRuntime().exec(arguments);
			//将得到的数据进行io处理
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = in.readLine()) != null) {
				//算法得到的json数据
				result = line;
			}
			//关闭流
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
