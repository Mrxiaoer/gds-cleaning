/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysDictValue;
import com.cloud.dips.admin.api.vo.DictVauleVO;

/**
 * <p>
 * 字典值表 Mapper 接口
 * </p>
 *
 * @author ZB
 */
public interface SysDictValueMapper extends BaseMapper<SysDictValue> {
	/**
	 * 通过字典id查询字典值列表
	 * @param dId 字典id
	 * @return
	 */
	List<DictVauleVO> selectDictVauleVo(@Param("dId") Integer dId);
}
