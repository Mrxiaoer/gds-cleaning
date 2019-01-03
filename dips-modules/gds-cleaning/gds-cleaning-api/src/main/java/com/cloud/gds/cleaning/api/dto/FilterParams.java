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

	private Long fileId;
	private Long centerId;
	private Float threshold;

}
