/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author Wilson
 * @since 2017-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_role")
public class SysRole extends Model<SysRole> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "g_role_id", type = IdType.AUTO)
	private Integer roleId;
	@TableField("g_role_name")
	private String roleName;
	@TableField("g_role_code")
	private String roleCode;
	@TableField("g_role_desc")
	private String roleDesc;
	@TableField("g_create_time")
	private LocalDateTime createTime;
	@TableField("g_update_time")
	private LocalDateTime updateTime;
	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@TableLogic
	@TableField("g_del_flag")
	private String delFlag;

	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}

}
