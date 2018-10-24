/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Wilson
 * @since 2017-10-29
 */
@Data
@TableName("gov_user")
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "g_user_id", type = IdType.AUTO)
	private Integer userId;
	/**
	 * 会员组
	 */
	@TableField("g_membergroup_id")
	private Integer membergroupId=0;
	/**
	 * 二级密码
	 */
	@TableField("g_safe_password")
	private String safePassword;
	/**
	 * 用户名
	 */
	@TableField("g_username")
	private String username;
	/**
	 * 密码
	 */
	@TableField("g_password")
	private String password;
	/**
	 * 真实姓名
	 */
	@TableField("g_real_name")
	private String realName;
	/**
	 * 随机盐
	 */
	@JsonIgnore
	@TableField("g_salt")
	private String salt;
	/**
	 * 邮箱
	 */
	@TableField("g_email")
	private String email;
	/**
	 * 创建时间
	 */
	@TableField("g_create_time")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@TableField("g_update_time")
	private Date updateTime;
	/**
	 * 0-正常，1-删除
	 */
	@TableLogic
	@TableField("g_del_flag")
	private String delFlag;

	/**
	 * 手机
	 */
	@TableField("g_phone")
	private String phone;
	/**
	 * 头像
	 */
	@TableField("g_avatar")
	private String avatar;

	/**
	  部门ID
	 /*
	//@TableField("dept_id")
	//private Integer deptId;
	
	/**
	 * 状态(0:正常,1:锁定,2:待验证)
	 */
	@TableField("g_status")
	private Integer status=0;
	/**
	 * 性别
	 */
	@TableField("g_gender")
	private String gender;
	
	/**
	 * 出生年月
	 */
	@TableField("g_birth_date")
	private Date birthDate;
	
	/**
	 * 身份证号
	 */
	@TableField("g_id_card")
	private String idCard;
	
	/**
	 * 等级
	 */
	@TableField("g_rank")
	private Integer rank=99999;
	
	/**
	 * 类型(0:会员,1:管理员)
	 */
	@TableField("g_type")
	private Integer type=0;
	
	/**
	 * qq openid
	 */
	@TableField("g_qq_openid")
	private String qqOpenid;
	/**
	 * weibouid
	 */
	@TableField("g_weibo_uid")
	private String weiboUid;
	
	/**
	 * wxOpenid
	 */
	@TableField("g_weixin_openid")
	private String wxOpenid;
	
	/**
	 * 是否订阅 0否 1是
	 */
	@TableField("g_subscribe")
	private Integer subscribe = 0;
	
	/**
	 * 用户工号
	 */
	@TableField("g_staff_id")
	private String staffID;// 用户工号
	
	/**
	 * 用户积分
	 */
	@TableField("g_integral")
	private Integer integral=0;
	
	/**
	 * 帐户金额
	 */
	@TableField("g_amount")
	private BigDecimal amount;
	
	/**
	 * 登录时长
	 */
	@TableField("g_logintime")
	private Integer loginTime = 0;
	
	
}
