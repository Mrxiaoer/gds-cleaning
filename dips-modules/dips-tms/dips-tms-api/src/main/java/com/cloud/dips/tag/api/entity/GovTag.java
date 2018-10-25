package com.cloud.dips.tag.api.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

@Data
@TableName("gov_tag")
public class GovTag implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    @Transient
    public void applyDefaultValue() {
        if (getCreationDate()==null) {
            setCreationDate(new Timestamp(System.currentTimeMillis()));
        }
    }
	
	/**
	 * 主键ID
	 */
	@TableId(value = "g_tag_id", type = IdType.AUTO)
	private Integer tagId;
	/**
	 * 标签名称
	 */
	@TableField("g_name")
	private String name;
	/**
	 * 标签创建时间
	 */
	@TableField("g_creation_date")
	private Date creationDate;
	/**
	 * 标签应用次数
	 */
	@TableField("g_refers")
	private Integer refers=0;
	/**
	 * 标签优先级
	 */
	@TableField("g_priority")
	private Integer priority=1;
	/**
	 * 标签分类id
	 */
	@TableField("g_type_id")
	private Integer typeId;
	/**
	 * 标签级别id
	 */
	@TableField("g_level_id")
	private Integer levelId;
	/**
	 * 标签浏览量
	 */
	@TableField("g_views")
	private Integer views=0;
	/**
	 * 标签介绍
	 */
	@TableField("g_description")
	private String description;
	/**
	 * 关联标签
	 */
	@TableField("g_relation")
	private String relation;
	/**
	 * 创建者ID
	 */
	@TableField("g_creator_id")
	private Integer creatorId;
	
	@Override
	public String toString() {
		return "GovTag [tagId=" + tagId + ", name=" + name + ", creationDate=" + creationDate + ", refers=" + refers
				+ ", priority=" + priority + ", typeId=" + typeId + ", levelId=" + levelId
				+ ", views=" + views + ", description=" + description + ", relation=" + relation + ", creatorId="
				+ creatorId + "]";
	}
	

}