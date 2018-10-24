/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户详情表
 * </p>
 *
 * @author RCG
 * @since 2018-08-14
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("gov_user_clob")
public class SysUserClob extends Model<SysUserClob> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableField("g_user_id")
	private Integer userId;
	/**
	 * 组织
	 */
	@TableField("g_key")
	private String key;
	
	/**
	 * 组织
	 */
	@TableField("g_value")
	private String value;
	
	public SysUserClob() {
	}
	
	public SysUserClob(Integer userId, String key, String value) {
		this.userId = userId;
		this.key = key;
		this.value = value;
	}

	@Override
	public String toString() {
		return "SysUserClob [userId=" + userId + ", key=" + key + ", value=" + value + "]";
	}

	@Override
	protected Serializable pkVal() {
		return this.userId;
	}

	
}
