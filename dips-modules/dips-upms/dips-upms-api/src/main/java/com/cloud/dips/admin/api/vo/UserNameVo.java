package com.cloud.dips.admin.api.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserNameVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Integer userId;
	
	/**
	 * 真实姓名
	 */
	private String realName;
}
