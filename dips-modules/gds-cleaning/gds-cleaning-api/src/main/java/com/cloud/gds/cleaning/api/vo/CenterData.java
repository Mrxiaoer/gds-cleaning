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

	private Long id;
//	private Long fieldId;
	private Long isManual;
	private Long count;
	private String fieldValue;

}
