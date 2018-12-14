package com.cloud.dips.tag.api.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotBlank;

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
	
	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer tagId;
	/**
	 * 标签名称
	 */
	@TableField("name")
	@NotBlank(message="标签名称不能为空")
	private String name;
	/**
	 * 标签创建时间
	 */
	@TableField("create_time")
	private Date createTime=new Timestamp(System.currentTimeMillis());
	/**
	 * 标签更新时间
	 */
	@TableField("update_time")
	private Date updateTime=new Timestamp(System.currentTimeMillis());
	/**
	 * 标签应用次数
	 */
	@TableField("refers")
	private Integer refers=0;
	/**
	 * 标签优先级
	 */
	@TableField("order_num")
	private Integer orderNum=1;
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
	 * 创建者ID
	 */
	@TableField("creator_id")
	private Integer creatorId;
	
	/**
	 * 所属系统
	 */
	@TableField("system")
	private String system;
	/**
	 * 标签状态 1正常 0待审
	 */
	@TableField("status")
	private Integer status=1;
	/**
	 * 标签启用 1启用 0废除
	 */
	@TableField("enable")
	private Integer enable=1;

}