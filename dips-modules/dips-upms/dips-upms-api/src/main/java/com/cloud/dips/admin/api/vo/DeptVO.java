package com.cloud.dips.admin.api.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cloud.dips.admin.api.dto.UserRealNameDTO;

import lombok.Data;

/**
 * @author C.Z.H
 * @date 2018/08/15
 */
@Data
public class DeptVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Integer deptId;
	/**
	 * 部门编号
	 */
	private String number;
	/**
	 * 部门名称
	 */
	private String title;
	/**
	 * 创建人名称
	 */
	private String creatorName;
	/**
	 * 成立时间
	 */
	private Date startTime;
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
	 * 是否财务结算公司
	 */
	private String isFinancialName;
	/**
	 * 是否内网结算部门
	 */
	private Integer isIntranet;
	/**
	 * 是否内网结算部门
	 */
	private String isIntranetName;
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
	 * 是否删除  -1：已删除  0：正常
	 */
	private String status;
	/**
	 * 父级ID
	 */
	private Integer parentId;
	/**
	 * 部门负责人
	 * */
	private UserRealNameDTO master;
	/**
	 * 部门成员
	 * */
	private List<UserRealNameDTO> memberList;
	/**
	 * 部门助手
	 * */
	private List<UserRealNameDTO> writerList;
	/**
	 * 卓越标签
	 * */
	private List<CommonVo> abilityTags;
	/**
	 * 专业标签
	 * */
	private List<CommonVo> projectTags;
	/**
	 * 进步标签
	 * */
	private List<CommonVo> learningTags;
	/**
	 * 部门资质
	 * */
	private List<CommonVo> aptitudeList;

}
