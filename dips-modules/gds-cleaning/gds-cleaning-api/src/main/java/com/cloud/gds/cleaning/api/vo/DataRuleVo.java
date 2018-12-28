package com.cloud.gds.cleaning.api.vo;

import lombok.Data;

import java.util.List;

/**
 * 根据单条用户规则id查询规则具体vo
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-03
 */
@Data
public class DataRuleVo {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;
	/**
	 * 数据名称
	 */
	private String name;
	/**
	 * 字段集合
	 */
	private List<DataSetVo> detail;
	/**
	 * 状态（0，未清洗；1，已清洗）
	 */
	private Integer state;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 数据来源
	 */
	private Long deptId;

}
