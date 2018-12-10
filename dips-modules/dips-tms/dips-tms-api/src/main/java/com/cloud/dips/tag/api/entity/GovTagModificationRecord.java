package com.cloud.dips.tag.api.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 标签修改记录表
 * @author ZB
 *
 */
@Data
@TableName("gov_tag_modification_record")
public class GovTagModificationRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 记录创建时间
	 */
	@TableField("create_time")
	private Date createTime = new Date();
	/**
	 * 修改描述
	 */
	@TableField("description")
	private String description="";
	/**
	 * 创建者ID
	 */
	@TableField("creator_id")
	private Integer creatorId;
	/**
	 * 标签id
	 */
	@TableField("tag_id")
	private Integer tagId;

}