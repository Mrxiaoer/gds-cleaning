/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.common.log.event;

import org.springframework.context.ApplicationEvent;

import com.cloud.dips.admin.api.entity.SysLog;

/**
 * @author Wilson
 * 系统日志事件
 */
public class SysLogEvent extends ApplicationEvent {

	private static final long serialVersionUID = 6762838341760471109L;

	public SysLogEvent(SysLog source) {
		super(source);
	}
}
