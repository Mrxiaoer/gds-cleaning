package com.cloud.dips.admin.api.dto;

import com.cloud.dips.admin.api.entity.SysDept;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author C.Z.H
 * @date 2018/08/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptDTO extends SysDept {

	/**
	 * 部门编号
	 */
	private String number;
	/**
	 * 部门名称
	 */
	private String title;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 排序
	 */
	private Integer orderNum;
	/**
	 * 机构分类
	 */
	private String category;
	/**
	 * 是否财务结算公司
	 */
	private Integer isFinancial;
	/**
	 * 是否内网结算部门
	 */
	private Integer isIntranet;
	/**
	 * 宣传图路径
	 */
	private String image;
	/**
	 * 联系方式
	 */
	private String input;
	/**
	 * 部门简介
	 */
	private String introduce;
	/**
	 * 组织架构
	 */
	private String structure;
	/**
	 * 核心优势
	 */
	private String advantage;
	/**
	 * 父级ID
	 */
	private Integer parentId;
	/**
	 * 部门负责人
	 * */
	private Integer masterId;
	/**
	 * 部门成员
	 * */
	private Integer[] members;
	/**
	 * 部门助手
	 * */
	private Integer[] writers;
	/**
	 * 卓越标签
	 * */
	private List<String> abilityTags;
	/**
	 * 专业标签
	 * */
	private List<String> projectTags;
	/**
	 * 进步标签
	 * */
	private List<String> learningTags;
	/**
	 * 部门资质
	 * */
	private Integer[] aptitudes;
}
