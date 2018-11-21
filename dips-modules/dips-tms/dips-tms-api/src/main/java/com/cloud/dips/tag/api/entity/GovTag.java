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

/**
 * 
 * @author ZB
 *
 */
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
	@TableId(value = "id", type = IdType.AUTO)
	private Integer tagId;
	/**
	 * 标签名称
	 */
	@TableField("name")
	private String name;
	/**
	 * 标签创建时间
	 */
	@TableField("creation_date")
	private Date creationDate;
	/**
	 * 标签应用次数
	 */
	@TableField("refers")
	private Integer refers=0;
	/**
	 * 标签优先级
	 */
	@TableField("priority")
	private Integer priority=1;
	/**
	 * 标签分类id
	 */
	@TableField("type_id")
	private Integer typeId=0;
	/**
	 * 标签级别id
	 */
	@TableField("level_id")
	private Integer levelId=0;
	/**
	 * 标签浏览量
	 */
	@TableField("views")
	private Integer views=0;
	/**
	 * 标签介绍
	 */
	@TableField("description")
	private String description="";
	/**
	 * 关联标签
	 */
	@TableField("relation")
	private String relation;
	/**
	 * 创建者ID
	 */
	@TableField("creator_id")
	private Integer creatorId;
	
	/**
	 * 所属系统
	 */
	@TableField("system")
	private String system;

	@Override
	public String toString() {
		return "GovTag [tagId=" + tagId + ", name=" + name + ", creationDate=" + creationDate + ", refers=" + refers
				+ ", priority=" + priority + ", typeId=" + typeId + ", levelId=" + levelId + ", views=" + views
				+ ", description=" + description + ", relation=" + relation + ", creatorId=" + creatorId + ", system="
				+ system + "]";
	}

}