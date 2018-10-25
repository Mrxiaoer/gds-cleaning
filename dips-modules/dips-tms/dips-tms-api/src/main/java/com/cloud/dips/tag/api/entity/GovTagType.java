package com.cloud.dips.tag.api.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

@Data
@TableName("gov_tag_type")
public class GovTagType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	@TableId(value = "g_type_id", type = IdType.AUTO)
	private Integer typeId;

	/**
	 * 上级分类ID
	 */
	@TableField("g_parent_id")
	private Integer parentId;
	/**
	 * 标签分类名称
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
		return "GovTagType [typeId=" + typeId + ", parentId=" + parentId + ", name=" + name + ", creationDate="
				+ creationDate + "]";
	}
}
