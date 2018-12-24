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

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cloud.dips.common.core.constant.CommonConstant;

import lombok.Data;

/**
 * <p>
 * 字典名称列表
 * </p>
 *
 * @author Wilson
 * @since 2017-11-19
 */
@Data
@TableName("gov_dict")
public class SysDict implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 字典编码
	 */
	@TableField("number")
	@NotBlank(message="字典编码不能为空")
	@Length(max=60,message="字典编码过长")
	private String number;
	/**
	 * 字典名称
	 */
	@TableField("name")
	@NotBlank(message="字典名称不能为空")
	@Length(max=60,message="字典名称过长")
	private String name;

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
	 * 所属系统
	 */
	@TableField("system")
	private String system=CommonConstant.SYSTEM_NAME;	
	
	@Override
	public String toString() {
		return "SysDict [id=" + id + ", number=" + number + ", name=" + name + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", system=" + system + "]";
	}
}
