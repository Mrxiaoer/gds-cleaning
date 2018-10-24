/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.dto;

import com.cloud.dips.admin.api.entity.SysUser;
import lombok.Data;


@Data
public class UserRealNameDTO extends SysUser {

	/**
	 * 用户ID
	 * */
	private Integer userId;

	/**
	 * 用户真实姓名
	 */
	private String realName;

}
