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
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 部门关系表
 * </p>
 *
 * @author Wilson
 * @since 2018-01-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_dept_relation")
public class SysDeptRelation extends Model<SysDeptRelation> {

	private static final long serialVersionUID = 1L;

	/**
	 * 祖先节点
	 */
	@TableField(value = "g_ancestor")
	private Integer ancestor;
	/**
	 * 后代节点
	 */
	@TableField(value = "g_descendant")
	private Integer descendant;


	@Override
	protected Serializable pkVal() {
		return this.ancestor;
	}

}
