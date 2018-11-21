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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 字典值列表
 * @author ZB
 *
 */
@Data
@TableName("gov_dict_value")
public class SysDictValue implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 字典键
	 */
	@TableField("key")
	@NotBlank(message="字典键不能为空")
	private String key;
	/**
	 * 字典值
	 */
	@TableField("value")
	@NotBlank(message="字典值不能为空")
	private String value;

	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime = new Date();
	/**
	 * 更新时间
	 */
	@TableField("update_time")
	private Date updateTime = new Date();
	/**
	 * 所属字典id
	 */
	@TableField("dict_id")
	@NotNull(message="所属字典不能为空")
	private Integer dictId;
	/**
	 * 父类id
	 */
	@TableField("parent_id")
	private Integer parentId=0;
	/**
	 * 排序
	 */
	@TableField("sort")
	private Integer sort=0;
	
	
	@Override
	public String toString() {
		return "SysDictValue [id=" + id + ", key=" + key + ", value=" + value + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", dictId=" + dictId + ", parentId=" + parentId + ", sort=" + sort
				+ "]";
	}
	
}
