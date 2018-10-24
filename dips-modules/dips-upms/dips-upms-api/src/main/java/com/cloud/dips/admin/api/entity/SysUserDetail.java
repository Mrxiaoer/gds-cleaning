/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户详情表
 * </p>
 *
 * @author RCG
 * @since 2018-08-14
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("gov_user_detail")
public class SysUserDetail extends Model<SysUserDetail> {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId(value = "g_user_id", type = IdType.INPUT)
	private Integer userId;
	/**
	 * 验证生成时间
	 */
	@TableField("g_validation_date")
	private Date validationDate;
	/**
	 * 登录错误时间
	 */
	@TableField("g_login_error_date")
	private Date loginErrorDate;
	/**
	 * 登录错误次数
	 */
	@TableField("g_login_error_count")
	private Integer loginErrorCount;
	/**
	 * 上次登录日期
	 */
	@TableField("g_prev_login_date")
	private Date prevLoginDate;
	/**
	 * 上次登录IP
	 */
	@TableField("g_prev_login_ip")
	private String prevLoginIp;
	/**
	 * 最后登录日期
	 */
	@TableField("g_last_login_date")
	private Date lastLoginDate;
	/**
	 * 最后登录IP
	 */
	@TableField("g_last_login_ip")
	private String lastLoginIp;
	/**
	 * 加入日期
	 */
	@TableField("g_creation_date")
	private Date creationDate;
	/**
	 * 加入IP
	 */
	@TableField("g_creation_ip")
	private String creationIp;
	/**
	 * 登录次数
	 */
	@TableField("g_logins")
	private Integer logins;
	/**
	 * 是否有头像
	 */
	@TableField("g_is_with_avatar")
	private Boolean withAvatar;
	/**
	 * 自我介绍
	 */
	@TableField("g_bio")
	private String bio;
	/**
	 * 来自
	 */
	@TableField("g_come_from")
	private String comeFrom;
	/**
	 * QQ
	 */
	@TableField("g_qq")
	private String qq;
	/**
	 * weixin
	 */
	@TableField("g_weixin")
	private String weixin;
	/**
	 * 第三方头像
	 */
	@TableField("g_head_img")
	private String headImg;
	/**
	 * 昵称
	 */
	@TableField("g_nick_name")
	private String nickName;
	/**
	 * 个性签名
	 */
	@TableField("g_sign_name")
	private String signName;

	
	/**
	 * 民族
	 */
	@TableField("g_nation")
	private String nation;
	/**
	 * 婚姻
	 */
	@TableField("g_marry")
	private String marry;
	/**
	 * 生育状况
	 */
	@TableField("g_bear")
	private String bear;
	/**
	 * 政治面貌
	 */
	@TableField("g_politics")
	private String politics;
	/**
	 * 户口类别
	 */
	@TableField("g_account_category")
	private String accountCategory;
	/**
	 * 户籍地址
	 */
	@TableField("g_permanent_address")
	private String permanentAddress;
	/**
	 * 现住地址
	 */
	@TableField("g_present_address")
	private String presentAddress;
	/**
	 * 学历
	 */
	@TableField("g_education")
	private String educationL;
	/**
	 * 毕业时间
	 */
	@TableField("g_graduation_time")
	private String graduationTime;
	/**
	 * 毕业学校
	 */
	@TableField("g_school")
	private String school;
	/**
	 * 专业
	 */
	@TableField("g_major")
	private String major;
	/**
	 * 外部证书
	 */
	@TableField("g_external_certificate")
	private String externalCertificate;
	/**
	 * 外语水平
	 */
	@TableField("g_language_level")
	private String languageLevel;

	/**
	 * 入职时间
	 */
	@TableField("g_start_time")
	private String startTime;
	/**
	 * 转正时间
	 */
	@TableField("g_regular_time")
	private String regularTime;
	
	/**
	 * 应急联系方式
	 */
	@TableField("g_emergency")
	private String emergency;
	/**
	 * 个人主页
	 */
	@TableField("g_home_url")
	private String homeUrl;

	
	
	
	
	
	
	@Override
	protected Serializable pkVal() {
		return this.userId;
	}







	@Override
	public String toString() {
		return "SysUserDetail [userId=" + userId + ", validationDate=" + validationDate + ", loginErrorDate="
				+ loginErrorDate + ", loginErrorCount=" + loginErrorCount + ", prevLoginDate=" + prevLoginDate
				+ ", prevLoginIp=" + prevLoginIp + ", lastLoginDate=" + lastLoginDate + ", lastLoginIp=" + lastLoginIp
				+ ", creationDate=" + creationDate + ", creationIp=" + creationIp + ", logins=" + logins
				+ ", withAvatar=" + withAvatar + ", bio=" + bio + ", comeFrom=" + comeFrom + ", qq=" + qq + ", weixin="
				+ weixin + ", headImg=" + headImg + ", nickName=" + nickName + ", signName=" + signName + ", nation="
				+ nation + ", marry=" + marry + ", bear=" + bear + ", politics=" + politics + ", accountCategory="
				+ accountCategory + ", permanentAddress=" + permanentAddress + ", presentAddress=" + presentAddress
				+ ", educationL=" + educationL + ", graduationTime=" + graduationTime + ", school=" + school
				+ ", major=" + major + ", externalCertificate=" + externalCertificate + ", languageLevel="
				+ languageLevel + ", startTime=" + startTime + ", regularTime=" + regularTime + ", emergency="
				+ emergency + ", homeUrl=" + homeUrl + "]";
	}







	
	
	
	
}
