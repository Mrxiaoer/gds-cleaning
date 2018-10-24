/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 部门大文本字段
 *
 * @author C.Z.H
 * @since 2018-08-15
 */
@Data
@TableName("gov_dept_clob")
public class SysDeptClob extends Model<SysDeptClob> {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门ID
	 */
	@TableId(value = "g_dept_id", type = IdType.INPUT)
	private Integer id;

	/**
	 * 键
	 */
	@TableField("g_key")
	private String key;

	/**
	 * 值
	 */
	@TableField("g_value")
	private String value;

	public SysDeptClob() {
	}

	public SysDeptClob(Integer id, String key, String value) {
		this.id = id;
		this.key = key;
		this.value = value;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysDeptClob{" +
			"id=" + id +
			", key='" + key + '\'' +
			", value='" + value + '\'' +
			'}';
	}
}
