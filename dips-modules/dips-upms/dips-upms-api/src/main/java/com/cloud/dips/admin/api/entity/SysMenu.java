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
 * 菜单权限表
 * </p>
 *
 * @author Wilson
 * @since 2017-11-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_menu")
public class SysMenu extends Model<SysMenu> {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@TableId(value = "g_menu_id",type = IdType.INPUT)
	private Integer menuId;
	/**
	 * 菜单名称
	 */
	@TableField("g_name")
	private String name;
	/**
	 * 菜单权限标识
	 */
	@TableField("g_permission")
	private String permission;
	/**
	 * 父菜单ID
	 */
	@TableField("g_parent_id")
	private Integer parentId;
	/**
	 * 图标
	 */
	@TableField("g_icon")
	private String icon;
	/**
	 * VUE页面
	 */
	@TableField("g_component")
	private String component;
	/**
	 * 排序值
	 */
	@TableField("g_sort")
	private Integer sort;
	/**
	 * 菜单类型 （0菜单 1按钮）
	 */
	@TableField("g_type")
	private String type;
	/**
	 * 创建时间
	 */
	@TableField("g_create_time")
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	@TableField("g_update_time")
	private LocalDateTime updateTime;
	/**
	 * 0--正常 1--删除
	 */
	@TableLogic
	@TableField("g_del_flag")
	private String delFlag;
	/**
	 * 前端URL
	 */
	@TableField("g_path")
	private String path;


	@Override
	protected Serializable pkVal() {
		return this.menuId;
	}

}
