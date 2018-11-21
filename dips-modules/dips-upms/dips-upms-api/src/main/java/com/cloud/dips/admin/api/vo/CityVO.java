package com.cloud.dips.admin.api.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/** 
* @author : DingBin
* @date 创建时间：2018年11月19日 下午3:42:27 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
@Data
public class CityVO implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 城市ID
	 */
	private Integer id;
	/**
	 * 城市名称
	 */
	private String name;
	/**
	 * 城市编号
	 */
	private String number;
	/**
	 * 城市代码
	 */
	private String code;
	/**
	 * 城市等级
	 */
	private Integer level;
	
	/**
	 * 城市等级名称
	 */
	private String levleName;
	/**
	 * 排序
	 */
	private Integer orderNum;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime modifiedTime;
	/**
	 * 是否删除 -1：已删除 0：正常
	 */
	private String isDeleted;
	/**
	 * 父级ID
	 */
	private Integer parentId;

}

