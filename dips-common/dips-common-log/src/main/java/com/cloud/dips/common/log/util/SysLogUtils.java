/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.common.log.util;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloud.dips.admin.api.entity.SysLog;
import com.cloud.dips.common.core.constant.CommonConstant;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;

/**
 * 系统日志工具类
 *
 * @author L.cm
 */
public class SysLogUtils {
	public static SysLog getSysLog() {
		HttpServletRequest request = ((ServletRequestAttributes) Objects
			.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		SysLog sysLog = new SysLog();
		sysLog.setCreateBy(Objects.requireNonNull(getUsername()));
		sysLog.setType(CommonConstant.STATUS_NORMAL);
		sysLog.setRemoteAddr(HttpUtil.getClientIP(request));
		sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
		sysLog.setMethod(request.getMethod());
		sysLog.setUserAgent(request.getHeader("user-agent"));
		sysLog.setParams(HttpUtil.toParams(request.getParameterMap()));
		sysLog.setServiceId(getClientId());
		return sysLog;
	}

	/**
	 * 获取客户端
	 *
	 * @return clientId
	 */
	private static String getClientId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
			return auth2Authentication.getOAuth2Request().getClientId();
		}
		return null;
	}

	/**
	 * 获取用户名称
	 *
	 * @return username
	 */
	private static String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		return authentication.getName();
	}

}
