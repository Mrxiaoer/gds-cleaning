package com.cloud.dips.tag.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

@Data
@TableName("gov_tag_level")
public class GovTagLevel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	@TableId(value = "g_level_id", type = IdType.AUTO)
	private Integer levelId;

	/**
	 * 上级级别ID
	 */
	@TableField("g_parent_id")
	private Integer parentId;
	/**
	 * 标签级别名称
	 */
	@TableField("g_name")
	private String name;
	/**
	 * 创建时间
	 */
	@TableField("g_creation_date")
	private Date creationDate;
	
	@Override
	public String toString() {
		return "GovTagLevel [levelId=" + levelId + ", parentId=" + parentId + ", name=" + name + ", creationDate="
				+ creationDate + "]";
	}
	
}
