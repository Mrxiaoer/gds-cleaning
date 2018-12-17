/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloud.dips.admin.api.entity.SysCity;
import com.cloud.dips.admin.api.feign.factory.RemoteCityServiceFallbackFactory;
import com.cloud.dips.common.core.constant.ServiceNameConstant;

/**
 * @author RCG
 * @date 2018/12/11
 */
@FeignClient(value = ServiceNameConstant.UMPS_SERVICE, fallbackFactory = RemoteCityServiceFallbackFactory.class)
public interface RemoteCityService {
	/**
	 * 通过部门ID查询
	 *
	 * @return R
	 */
	@GetMapping("/city/deptId/{id}")
	SysCity findByDeptId(@PathVariable("id") Integer id);
}
