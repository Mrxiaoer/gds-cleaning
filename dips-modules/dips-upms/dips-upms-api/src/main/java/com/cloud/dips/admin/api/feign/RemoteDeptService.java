/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloud.dips.admin.api.feign.factory.RemoteDeptServiceFallbackFactory;
import com.cloud.dips.admin.api.vo.DeptCityVO;
import com.cloud.dips.common.core.constant.ServiceNameConstant;

/**
 * @author RCG
 * @date 2018/12/11
 */
@FeignClient(value = ServiceNameConstant.UMPS_SERVICE, fallbackFactory = RemoteDeptServiceFallbackFactory.class)
public interface RemoteDeptService {
	/**
	 * 机构 城市 集合
	 *
	 * @return R
	 */
	@GetMapping("/dept/city/list")
	List<DeptCityVO> list();
	
	/**
	 * 机构 城市 集合
	 *
	 * @return R
	 */
	@GetMapping("/dept/find/{name}")
	Integer findDeptIdByName(@PathVariable("name") String name);
	
	/**
	 * map 集合 id为键，DeptCityVO为值
	 *
	 * @return R
	 */
	@GetMapping("/dept/map")
	Map<Integer,DeptCityVO> getDeptCityVOMap();

}
