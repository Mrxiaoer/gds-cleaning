/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.feign.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cloud.dips.admin.api.entity.SysDict;
import com.cloud.dips.admin.api.feign.RemoteDictService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Wilson
 * @date 2018/6/26
 */
@Slf4j

@Component

public class RemoteDictServiceFallbackImpl implements RemoteDictService {
	
	/**
	 * 
	 * 通过类型查询字典值域列表
	 * 
	 * @param type 类型
	 * 
	 * @param from 内外标志
	 * 
	 * @return R
	 * 
	 */

	@Override

	public List<SysDict> list(String type, String from) {
		
		log.error("feign 查询字典信息失败:{}", type);
		
		return null;

	}

}
