package com.cloud.gds.cleaning.api.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 分析结果池Vo
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-30
 */
@Data
public class DARVo {

	/**
	 *id
	 */
	private Long id;
	/**
	 * 字段名id
	 */
	private Long fieldId;
	/**
	 * 导入数据的字段内容（json格式）
	 */
	private JSONObject fieldValue;
	/**
	 * 相似度
	 */
	private Double similarity;

}
