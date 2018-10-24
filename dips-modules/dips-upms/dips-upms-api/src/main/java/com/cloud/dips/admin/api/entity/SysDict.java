/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author Wilson
 * @since 2017-11-19
 */
@Data
@TableName("gov_dict")
public class SysDict extends Model<SysDict> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(value = "g_id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 数据值
	 */
	@TableField("g_value")
	private String value;
	/**
	 * 标签名
	 */
	@TableField("g_label")
	private String label;
	/**
	 * 类型
	 */
	@TableField("g_type")
	private String type;
	/**
	 * 描述
	 */
	@TableField("g_description")
	private String description;
	/**
	 * 排序（升序）
	 */
	@TableField("g_sort")
	private BigDecimal sort;
	/**
	 * 创建时间
	 */
	@TableField("g_create_time")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@TableField("g_update_time")
	private Date updateTime;
	/**
	 * 备注信息
	 */
	@TableField("g_remarks")
	private String remarks;
	/**
	 * 删除标记
	 */
	
	@TableLogic
	@TableField("g_del_flag")
	private String delFlag;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
