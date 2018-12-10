package com.cloud.dips.tag.api.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @author ZB
 *
 */
@Data
public class GovTagModificationRecordVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 记录创建时间
	 */
	private Date createTime;
	/**
	 * 修改描述
	 */
	private String description;
	/**
	 * 创建者ID
	 */
	private Integer creatorId;
	/**
	 * 创建者真实姓名
	 */
	private String creatorRealName;
	/**
	 * 标签id
	 */
	private Integer tagId;
	/**
	 * 标签名称
	 */
	private String tagName;

}
