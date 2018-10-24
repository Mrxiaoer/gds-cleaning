package com.cloud.dips.tag.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class GovTagDescriptionVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Integer descriptionId;


	/**
	 * 描述
	 */
	private String description;
	/**
	 * 关联标签id
	 */
	private Integer tagId;
	/**
	 * 创建时间
	 */
	private Date creationDate;
	/**
	 * 创建者id
	 */
	private Integer creatorId;
	/**
	 * 创建者真实姓名
	 */
	private String creatorRealName;
	

}
