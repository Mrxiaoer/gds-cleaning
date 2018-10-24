/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.feign.fallback;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.dips.admin.api.entity.SysAttachment;
import com.cloud.dips.admin.api.feign.RemoteAttachmentService;
import com.cloud.dips.common.core.util.R;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Wilson
 * @date 2018/6/26
 */
@Slf4j
@Component
public class RemoteAttachmentServiceFallbackImpl implements RemoteAttachmentService {

	/**
	 * 保存日志
	 *
	 * @param sysLog
	 * @return R
	 */
	@Override
	public R<Boolean> uploadAttachment(SysAttachment sysAttachment) {
		log.error("feign 上传附件失败:{}");
		return null;
	}
	
	@Override
	public R<Boolean> saveAttach(MultipartFile file, Integer userId, HttpServletRequest request) {
		log.error("feign 上传附件失败:{}");
		return null;
	}

	
}
