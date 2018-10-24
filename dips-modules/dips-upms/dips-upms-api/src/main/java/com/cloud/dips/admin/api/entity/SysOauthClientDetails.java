/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 客户端信息
 * </p>
 *
 * @author Wilson
 * @since 2018-05-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_oauth_client_details")
public class SysOauthClientDetails extends Model<SysOauthClientDetails> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "g_client_id", type = IdType.INPUT)	
	private String clientId;
	@TableField("g_resource_ids")
	private String resourceIds;
	@TableField("g_client_secret")
	private String clientSecret;
	@TableField("g_scope")
	private String scope;
	@TableField("g_authorized_grant_types")
	private String authorizedGrantTypes;
	@TableField("g_web_server_redirect_uri")
	private String webServerRedirectUri;
	@TableField("g_authorities")
	private String authorities;
	@TableField("g_access_token_validity")
	private Integer accessTokenValidity;
	@TableField("g_refresh_token_validity")
	private Integer refreshTokenValidity;
	@TableField("g_additional_information")
	private String additionalInformation;
	@TableField("g_autoapprove")
	private String autoapprove;

	@Override
	protected Serializable pkVal() {
		return this.clientId;
	}

}
