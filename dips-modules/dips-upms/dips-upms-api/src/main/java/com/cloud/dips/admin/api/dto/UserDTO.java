/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.dto;

import com.cloud.dips.admin.api.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author Wilson
 * @date 2017/11/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {
	/**
	 * 角色ID
	 */
	private List<Integer> role;

	/**
	 * 新密码
	 */
	private String newpassword1;
	
	private String workText;
	
	private String education;

	private String qq;

	private String weixin;
	
	private Date validationDate;
	
	private Date loginErrorDate;
	
	private Integer loginErrorCount;
	
	private Date prevLoginDate;
	
	private String prevLoginIp;
	
	private Date lastLoginDate;
	
	private String lastLoginIp;
	
	private Date creationDate;
	
	private String creationIp;
	
	private Integer logins;
	
	private Boolean withAvatar;
	
	private String bio;
	
	private String comeFrom;
	
	private String headImg;
	
	private String nickName;
	
	private String signName;
	
	private Date startTime;
	
	private Date regularTime;
	
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
	
	private Date graduationTime;
	private String externalCertificate;
	

	private String emergency;

	private String homeUrl;

}
