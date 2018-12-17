/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cloud.dips.admin.api.entity.SysLog;
import com.cloud.dips.admin.api.feign.factory.RemoteLogServiceFallbackFactory;
import com.cloud.dips.common.core.constant.ServiceNameConstant;
import com.cloud.dips.common.core.util.R;
/**
 * @author Wilson
 * @date 2018/6/28
 */
@FeignClient(value = ServiceNameConstant.UMPS_SERVICE, fallbackFactory = RemoteLogServiceFallbackFactory.class)
public interface RemoteLogService {
	/**
	 * 保存日志
	 *
	 * @param sysLog 日志实体
	 * @return success、false
	 */
	@PostMapping("/log/create")
	R<Boolean> saveLog(@RequestBody SysLog sysLog);
}
