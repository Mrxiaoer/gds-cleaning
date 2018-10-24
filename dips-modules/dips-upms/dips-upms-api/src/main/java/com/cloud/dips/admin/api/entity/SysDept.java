/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 *
 * 部门管理
 *
 * @author C.Z.H
 * @since 2018-08-15
 */
@Data
@TableName("gov_dept")
public class SysDept extends Model<SysDept> {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门ID
	 */
	@TableId(value = "g_dept_id", type = IdType.AUTO)
	private Integer deptId;

	/**
	 * 部门编号
	 */
	@TableField("g_dept_number")
	private String number;

	/**
	 * 部门名称
	 */
	@TableField("g_dept_title")
	private String title;

	/**
	 * 创建人ID
	 */
	@TableField("g_creator_id")
	private Integer creatorId;

	/**
	 * 创建时间
	 */
	@TableField("g_start_time")
	private Date startTime;

	/**
	 * 更新时间
	 */
	@TableField("g_update_time")
	private Date updateTime;

	/**
	 * 排序
	 */
	@TableField("g_order_num")
	private Integer orderNum;

	/**
	 * 机构分类
	 */
	@TableField("g_category")
	private String category;

	/**
	 * 是否财务结算公司
	 */
	@TableField("g_isFinancial")
	private Integer isFinancial;

	/**
	 * 是否内网结算部门
	 */
	@TableField("g_isIntranet")
	private Integer isIntranet;

	/**
	 * 宣传图路径
	 */
	@TableField("g_image")
	private String image;

	/**
	 * 联系方式
	 */
	@TableField("g_dept_input")
	private String input;

	/**
	 * 是否删除  1：已删除  0：正常
	 */
	@TableField("g_status")
	@TableLogic
	private String status;

	/**
	 * 父级ID
	 */
	@TableField("g_parent_id")
	private Integer parentId;

	@Override
	protected Serializable pkVal() {
		return this.deptId;
	}

	@Override
	public String toString() {
		return "SysDept{" +
			"deptId=" + deptId +
			", number='" + number + '\'' +
			", title='" + title + '\'' +
			", creatorId=" + creatorId +
			", startTime=" + startTime +
			", updateTime=" + updateTime +
			", orderNum=" + orderNum +
			", category='" + category + '\'' +
			", isFinancial=" + isFinancial +
			", isIntranet=" + isIntranet +
			", image='" + image + '\'' +
			", input='" + input + '\'' +
			", status='" + status + '\'' +
			", parentId=" + parentId +
			'}';
	}
}
