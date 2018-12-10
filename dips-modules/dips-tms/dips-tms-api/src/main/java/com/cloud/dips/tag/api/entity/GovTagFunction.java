package com.cloud.dips.tag.api.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 标签功能表
 * @author ZB
 *
 */
@Data
@TableName("gov_tag_function")
public class GovTagFunction implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 功能名称
	 */
	@TableField("name")
	@NotBlank(message="功能名称不能为空")
	private String name;
	/**
	 * 功能编码
	 */
	@TableField("number")
	@NotBlank(message="功能编码不能为空")
	private String number;
	/**
	 * 功能描述
	 */
	@TableField("description")
	private String description="";
	/**
	 * 是否启用 1启用 0关闭
	 */
	@TableField("enable")
	private Integer enable=1;

}