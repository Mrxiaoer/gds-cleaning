package com.cloud.gds.cleaning.api.dto;

import cn.hutool.json.JSONObject;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * 未分析或需要重新分析的数据
 *
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2018-12-19
 */
@Data
public class WillAnalysisData {

	/**
	 * 阀值
	 */
	Float threshold;

	/**
	 * 字段名
	 */
	List<String> params;

	/**
	 * 权重
	 */
	List<Float> weights;

	/**
	 * 是否为近似字段
	 */
	List<Boolean> approximates;

	/**
	 * 标准数据
	 * 例："standard":{"length":10,"type":1,"nameEn":"mc","nameCn":"名称"}
	 */
	Map<String, Object> standard;

	/**
	 * 近义数据
	 * 例："similarity":{"nameEn":{"xm":0.5,"mz":0.8},"nameCn":{"姓名":0.6,"名字":0.8}}
	 */
	Map<String, Map<String, Float>> similarity;

	/**
	 * 需要分析的数据id
	 */
	List<Long> needAnalysisDataId;

	/**
	 * 待分析数据
	 */
	List<JSONObject> data;

}
