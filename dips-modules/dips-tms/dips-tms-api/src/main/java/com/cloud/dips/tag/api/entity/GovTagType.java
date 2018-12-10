package com.cloud.dips.tag.api.entity;

import java.io.Serializable;
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
@TableName("gov_tag_type")
public class GovTagType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer typeId;

	/**
	 * 上级分类ID
	 */
	@TableField("parent_id")
	private Integer parentId=0;
	/**
	 * 标签分类名称
	 */
	@TableField("name")
	private String name;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime=new Date();
}
