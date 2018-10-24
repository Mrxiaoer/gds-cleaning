/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.entity.SysAttachment;
import com.cloud.dips.admin.mapper.SysAttachmentMapper;
import com.cloud.dips.admin.service.SysAttachmentService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author Wilson
 * @since 2017-11-20
 */
@Slf4j
@Service
public class SysAttachmentServiceImpl extends ServiceImpl<SysAttachmentMapper, SysAttachment> implements SysAttachmentService {
}
