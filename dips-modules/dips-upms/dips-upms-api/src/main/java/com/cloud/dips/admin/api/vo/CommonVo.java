package com.cloud.dips.admin.api.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer commonId;

	/**
	 * name
	 */
	private String commonName;
}
