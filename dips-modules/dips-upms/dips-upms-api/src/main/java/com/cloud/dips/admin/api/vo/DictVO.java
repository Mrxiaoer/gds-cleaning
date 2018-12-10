package com.cloud.dips.admin.api.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author ZB
 */
@Data
public class DictVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Integer id;
	/**
	 * 字典编码
	 */
	private String number;
	/**
	 * 字典名称
	 */
	private String name;

	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 所属系统
	 */
	private String system;

	private List<DictValueVO> dictValueVoList;

}
