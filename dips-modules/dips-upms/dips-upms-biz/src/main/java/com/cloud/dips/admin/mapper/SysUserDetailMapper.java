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
import com.cloud.dips.admin.api.entity.SysUserDetail;

/**
 * <p>
 * 用户细节 Mapper 接口
 * </p>
 *
 * @author rcg
 * @since 2017-10-29
 */
public interface SysUserDetailMapper extends BaseMapper<SysUserDetail> {
	SysUserDetail findOne(@Param("userId") Integer userId);
}
