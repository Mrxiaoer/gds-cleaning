package com.cloud.gds.cleaning.api.dto;

import lombok.Data;

/**
 * 过滤参数
 *
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2019-01-03
 */
@Data
public class FilterParams {

	/**
	 * 清洗池id
	 */
	private String fileId;
	/**
	 * 中心点id
	 */
	private Long centerId;
	/**
	 * 阀值
	 */
	private Float threshold;

}
