package com.cloud.dips.tag.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

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
@TableName("gov_tag_level")
public class GovTagLevel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer levelId;
	/**
	 * 标签级别名称
	 */
	@TableField("name")
	@NotBlank(message="级别名称不能为空")
	@Length(max=60,message="级别名称过长")
	private String name;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime=new Date();
	
}
