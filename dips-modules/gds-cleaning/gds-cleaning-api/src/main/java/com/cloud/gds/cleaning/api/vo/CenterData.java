package com.cloud.gds.cleaning.api.vo;

import lombok.Data;

/**
 * 分析中心数据回显
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-04
 */
@Data
public class CenterData {

	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 操作方式(1、自动 2、人工)
	 */
	private Long isManual;
	/**
	 * 统计个数
	 */
	private Long count;
	/**
	 * 内容
	 */
	private String fieldValue;

}
