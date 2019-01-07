package com.cloud.gds.cleaning.api.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 过滤数据参数接受
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-07
 */
@Data
public class DataDto {

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
	/**
	 * 滤网大小
	 */
	private  Float screenSize;

}
