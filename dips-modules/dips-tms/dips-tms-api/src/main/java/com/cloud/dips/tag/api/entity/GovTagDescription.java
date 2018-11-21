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
@TableName("gov_tag_description")
public class GovTagDescription implements Serializable{
	
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
	private Integer descriptionId;


	/**
	 * 描述
	 */
	@TableField("description")
	private String description;
	/**
	 * 关联标签id
	 */
	@TableField("tag_id")
	private Integer tagId;
	/**
	 * 创建时间
	 */
	@TableField("creation_date")
	private Date creationDate;
	/**
	 * 创建者id
	 */
	@TableField("creator_id")
	private Integer creatorId;
	
	@Override
	public String toString() {
		return "GovTagDescription [descriptionId=" + descriptionId + ", description=" + description + ", tagId=" + tagId
				+ ", creationDate=" + creationDate + ", creatorId=" + creatorId + "]";
	}

}