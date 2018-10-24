/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.common.log.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloud.dips.admin.api.entity.SysLog;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.security.util.SecurityUtils;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;

/**
 * 系统日志工具类
 *
 * @author L.cm
 */
public class SysLogUtils {
	public static SysLog getSysLog() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SysLog sysLog = new SysLog();
		if(SecurityUtils.getUser()==null){
			sysLog.setCreateBy("anonymousUser");
		}else{
			sysLog.setCreateBy(SecurityUtils.getUser().getUsername());
		}
		sysLog.setType(CommonConstant.STATUS_NORMAL);
		sysLog.setRemoteAddr(HttpUtil.getClientIP(request));
		sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
		sysLog.setMethod(request.getMethod());
		sysLog.setUserAgent(request.getHeader("user-agent"));
		sysLog.setParams(HttpUtil.toParams(request.getParameterMap()));
		sysLog.setServiceId(SecurityUtils.getClientId());
		return sysLog;
	}
}
