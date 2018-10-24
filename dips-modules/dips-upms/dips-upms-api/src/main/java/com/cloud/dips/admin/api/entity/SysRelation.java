/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 登记关联表
 *
 * @author C.Z.H
 * @since 2018-08-15
 */
@Data
@TableName("gov_relation")
public class SysRelation implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 关联编码
	 */
	@TableField(value = "g_node")
	private String node;

	/**
	 * 关联ID
	 */
	@TableField(value = "g_relation_id")
	private Integer relationId;

	/**
	 * 被关联ID
	 */
	@TableField(value = "g_correlation_id")
	private Integer correlationId;

	/**
	 * 关联类型ID
	 */
	@TableField(value = "g_type_id")
	private Integer typeId;

	public SysRelation() {
	}

	public SysRelation(String node, Integer relationId, Integer correlationId, Integer typeId) {
		this.node = node;
		this.relationId = relationId;
		this.correlationId = correlationId;
		this.typeId = typeId;
	}
}
