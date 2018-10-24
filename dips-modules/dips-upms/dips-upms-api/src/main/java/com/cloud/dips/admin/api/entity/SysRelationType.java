/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 关联类型表
 *
 * @author C.Z.H
 * @since 2018-08-15
 */
@Data
@TableName("gov_relation_type")
public class SysRelationType implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 关联类型ID
	 */
	@TableId(value = "g_type_id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 关联类型名称
	 */
	@TableField("g_type_name")
	private String name;

	/**
	 * 关联类型编码
	 */
	@TableField("g_type_number")
	private String number;

	public SysRelationType() {

	}

	public SysRelationType(Integer id, String name, String number) {
		this.id = id;
		this.name = name;
		this.number = number;
	}
}
