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
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色与部门对应关系
 * </p>
 *
 * @author Wilson
 * @since 2018-01-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_role_dept")
public class SysRoleDept extends Model<SysRoleDept> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "g_id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 角色ID
	 */
	@TableField("g_role_id")
	private Integer roleId;
	/**
	 * 部门ID
	 */
	@TableField("g_dept_id")
	private Integer deptId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
