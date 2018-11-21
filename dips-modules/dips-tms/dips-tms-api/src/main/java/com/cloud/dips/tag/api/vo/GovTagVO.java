package com.cloud.dips.tag.api.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author ZB
 *
 */
@Data
public class GovTagVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Integer tagId;
	/**
	 * 标签名称
	 */
	private String name;
	/**
	 * 标签创建时间
	 */
	private Date creationDate;
	/**
	 * 标签更新时间
	 */
	private Date updateDate;
	/**
	 * 标签应用次数
	 */
	private Integer refers;
	/**
	 * 标签优先级
	 */
	private Integer priority;
	/**
	 * 标签分类id
	 */
	private Integer typeId;
	/**
	 * 标签分类名称
	 */
	private String typeName;
	/**
	 * 标签级别id
	 */
	private Integer levelId;
	/**
	 * 标签级别名称
	 */
	private String levelName;
	/**
	 * 应用领域id
	 */
	private Integer areaId;
	/**
	 * 应用领域名称
	 */
	private String areaName;
	/**
	 * 标签浏览量
	 */
	private Integer views;
	/**
	 * 标签介绍
	 */
	private String description;
	/**
	 * 关联标签
	 */
	private String relation;
	/**
	 * 创建者ID
	 */
	private Integer creatorId;
	/**
	 * 创建者真实姓名
	 */
	private String creatorRealName;
	
	
	/**
	 * 标签描述列表
	 */
	private List<GovTagDescriptionVO> tagDescriptionList;	
}