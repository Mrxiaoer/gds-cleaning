/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.dto.RelationTypeDTO;
import com.cloud.dips.admin.api.entity.SysRelationType;
import com.cloud.dips.common.core.util.Query;

/**
 *
 * 关联类型
 *
 */
public interface SysRelationTypeService extends IService<SysRelationType> {
	Boolean insertType(RelationTypeDTO typeDTO);

	SysRelationType selectByNumber(String number);

	/**
	 * 分页查询信息
	 *
	 * @param query 查询条件
	 *
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Page<SysRelationType> selectAllPage(Query query);
}
