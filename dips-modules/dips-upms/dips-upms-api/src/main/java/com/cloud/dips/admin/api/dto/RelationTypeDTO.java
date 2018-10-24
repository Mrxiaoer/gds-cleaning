/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.dto;

import com.cloud.dips.admin.api.entity.SysRelationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * 关联类型表DTO
 *
 * @author C.Z.H
 * @since 2018-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RelationTypeDTO extends SysRelationType {

	/**
	 * 关联类型名称
	 */
	private String name;

	/**
	 * 关联类型编码
	 */
	private String number;

}
