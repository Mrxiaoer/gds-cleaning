/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.entity.SysUserDetail;
import com.cloud.dips.admin.mapper.SysUserDetailMapper;
import com.cloud.dips.admin.service.SysUserDetailService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Wilson
 * @date 2017/10/31
 */
@Slf4j

@Service

public class SysUserDetailServiceImpl extends ServiceImpl<SysUserDetailMapper, SysUserDetail> implements SysUserDetailService {

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	

}
