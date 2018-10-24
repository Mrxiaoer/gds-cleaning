/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysUserClob;

/**
 * <p>
 * 用户大字段Mapper 接口
 * </p>
 *
 * @author RCG
 * @since 2017-10-29
 */
public interface SysUserClobMapper extends BaseMapper<SysUserClob> {
	SysUserClob findOne(@Param("userId") Integer userId, @Param("key") String key);
	Boolean updateByIdAndKey(@Param("userId") Integer userId, @Param("key") String key, @Param("value") String value);
}
