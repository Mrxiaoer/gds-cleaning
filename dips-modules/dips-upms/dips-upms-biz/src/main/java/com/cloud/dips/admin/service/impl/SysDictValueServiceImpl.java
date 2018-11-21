/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.entity.SysDictValue;
import com.cloud.dips.admin.api.vo.DictVauleVO;
import com.cloud.dips.admin.mapper.SysDictValueMapper;
import com.cloud.dips.admin.service.SysDictValueService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 字典值表 服务实现类
 * </p>
 *
 * @author ZB
 */
@Slf4j
@Service
public class SysDictValueServiceImpl extends ServiceImpl<SysDictValueMapper, SysDictValue> implements SysDictValueService {

	@Autowired
	private SysDictValueMapper mapper;
	
	@Override
	public List<DictVauleVO> selectDictVauleVo(Integer dId) {
		return mapper.selectDictVauleVo(dId);
	}

}
