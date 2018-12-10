/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.entity.SysDictValue;
import com.cloud.dips.admin.api.vo.DictValueVO;

/**
 * <p>
 * 字典值表 服务类
 * </p>
 *
 * @author ZB
 */
public interface SysDictValueService extends IService<SysDictValue> {
	
	/**
	 * 通过字典id查询字典值列表
	 * @param dId
	 * @return
	 */
	List<DictValueVO> selectDictValueVo(Integer dId);
}
