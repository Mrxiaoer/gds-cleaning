package com.cloud.gds.cleaning.api.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 导入数据的内容
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-11-28
 */
@Data
public class DataPoolVo {

	/**
	 *
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
	 * 备注
	 */
	private String remark;

}
