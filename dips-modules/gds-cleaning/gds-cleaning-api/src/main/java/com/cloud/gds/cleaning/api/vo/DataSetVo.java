package com.cloud.gds.cleaning.api.vo;

import lombok.Data;

/**
 * 数据集合Vo
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-04
 */
@Data
public class DataSetVo {

	private static final long serialVersionUID = 1L;

	/**
	 * 字段名称
	 */
	private String label;
	/**
	 * 参数名称
	 */
	private String prop;
	/**
	 * 权重
	 */
	private Float weight;

	/**
	 * 有无同义
	 */
	private Integer isSynonymous;

}
