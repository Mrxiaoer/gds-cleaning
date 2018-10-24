/*
 *
 *      Copyright (c) 2018-2025, BigPan All rights reserved.
 *
 */

package com.cloud.dips.common.core.constant;

/**
 * @author BigPan
 * @date 2017-12-18
 */
public interface SecurityConstants {
	/**
	 * 角色前缀
	 */
	String ROLE = "ROLE_";
	/**
	 * 前缀
	 */
	String DIPS_PREFIX = "dips_";

	/**
	 * oauth 相关前缀
	 */
	String OAUTH_PREFIX = "oauth:";
	/**
	 * 项目的license
	 */
	String DIPS_LICENSE = "made by dips";
	
	/**
	 * 标志
	 */
	String FROM = "from";

	/**
	 * 内部
	 */
	String FROM_IN = "Y";

	/**
	 * {bcrypt} 加密的特征码
	 */
	String BCRYPT = "{bcrypt}";
	/**
	 * sys_oauth_client_details 表的字段，不包括client_id、client_secret
	 */
	String CLIENT_FIELDS = "g_client_id, CONCAT('{noop}',g_client_secret) as g_client_secret, g_resource_ids, g_scope, "
		+ "g_authorized_grant_types, g_web_server_redirect_uri, g_authorities, g_access_token_validity, "
		+ "g_refresh_token_validity, g_additional_information, g_autoapprove";

	/**
	 * JdbcClientDetailsService 查询语句
	 */
	String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
		+ " from gov_oauth_client_details";

	/**
	 * 默认的查询语句
	 */
	String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by g_client_id";

	/**
	 * 按条件client_id 查询
	 */
	String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where g_client_id = ?";

	/**
	 * 手机号登录URL
	 */
	String MOBILE_TOKEN_URL = "/mobile/token";

	/**
	 * 微信获取OPENID
	 */
	String WX_AUTHORIZATION_CODE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token" +
		"?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
}
