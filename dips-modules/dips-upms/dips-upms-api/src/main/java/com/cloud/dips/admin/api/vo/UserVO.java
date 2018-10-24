/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.cloud.dips.admin.api.entity.SysRole;

import lombok.Data;

/**
 * @author Wilson
 * @date 2017/10/29
 */
@Data
public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Integer userId;

	/**
	 * 会员组
	 */
	private Integer membergroupId;
	/**
	 * 二级密码
	 */
	private String safePassword;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 随机盐
	 */
	private String salt;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 0-正常，1-删除
	 */
	private String delFlag;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 部门集合
	 */
	private List<DeptVO> deptList;

	/**
	 * 角色列表
	 */
	private List<SysRole> roleList;
	
	/**
	 * 状态(0:正常,1:锁定,2:待验证)
	 */
	private Integer status;
	
	/**
	 * 性别
	 */
	private String gender;
	
	/**
	 * 出生年月
	 */
	private Date birthDate;
	
	/**
	 * 身份证号
	 */
	private String idCard;	
	
	/**
	 * 等级
	 */
	private Integer rank;
	
	/**
	 * 类型(0:会员,1:管理员)
	 */
	private Integer type;
	
	/**
	 * qq openid
	 */
	private String qqOpenid;
	
	/**
	 * weibouid
	 */
	private String weiboUid;
	
	/**
	 * wxOpenid
	 */
	private String wxOpenid;
	
	/**
	 * 是否订阅 0否 1是
	 */
	private Integer subscribe;
	
	/**
	 * 用户工号
	 */
	private String staffID;// 用户工号
	
	/**
	 * 用户积分
	 */
	private Integer integral;
	
	/**
	 * 帐户金额
	 */
	private BigDecimal amount;
	
	/**
	 * 登录时长
	 */
	private Integer loginTime;
	
	/**
	 * 验证生成时间
	 */
	private Date validationDate;
	/**
	 * 登录错误时间
	 */
	private Date loginErrorDate;
	/**
	 * 登录错误次数
	 */
	private Integer loginErrorCount;
	/**
	 * 上次登录日期
	 */
	private Date prevLoginDate;
	/**
	 * 上次登录IP
	 */
	private String prevLoginIp;
	/**
	 * 最后登录日期
	 */
	private Date lastLoginDate;
	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;
	/**
	 * 加入日期
	 */
	private Date creationDate;
	/**
	 * 加入IP
	 */
	private String creationIp;
	/**
	 * 登录次数
	 */
	private Integer logins;
	/**
	 * 是否有头像
	 */
	private Boolean withAvatar;
	/**
	 * 自我介绍
	 */
	private String bio;
	/**
	 * 来自
	 */
	private String comeFrom;
	/**
	 * QQ
	 */
	private String qq;
	/**
	 * weixin
	 */
	private String weixin;
	/**
	 * 第三方头像
	 */
	private String headImg;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 个性签名
	 */
	private String signName;
	
	/**
	 * 入职时间
	 */
	private Date startTime;
	/**
	 * 转正时间
	 */
	private Date regularTime;
	
	/**
	 * 工作经历
	 */
	private String workText;
	/**
	 * 学习经历
	 */
	private String education;
	
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
	 * 民族
	 */
	private String nation;
	/**
	 * 婚姻
	 */
	private String marry;
	/**
	 * 生育状况
	 */
	private String bear;
	/**
	 * 政治面貌
	 */
	private String politics;
	/**
	 * 户口类别
	 */
	private String accountCategory;
	/**
	 * 户籍地址
	 */
	private String permanentAddress;
	/**
	 * 现住地址
	 */
	private String presentAddress;
	/**
	 * 学历
	 */
	private String educationL;
	/**
	 * 毕业学校
	 */
	private String school;
	/**
	 * 专业
	 */
	private String major;
	/**
	 * 外语水平
	 */
	private String languageLevel;
	
	private String graduationTime;
	private String externalCertificate;
	private String emergency;

	private String homeUrl;
	
}
