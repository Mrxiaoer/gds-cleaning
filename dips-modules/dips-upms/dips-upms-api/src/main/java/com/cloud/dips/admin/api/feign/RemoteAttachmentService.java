/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.feign;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.dips.admin.api.entity.SysAttachment;
import com.cloud.dips.admin.api.feign.fallback.RemoteAttachmentServiceFallbackImpl;
import com.cloud.dips.common.core.constant.ServiceNameConstant;
import com.cloud.dips.common.core.util.R;

/**
 * @author Wilson
 * @date 2018/6/28
 */
@FeignClient(value = ServiceNameConstant.UMPS_SERVICE, fallback = RemoteAttachmentServiceFallbackImpl.class)
public interface RemoteAttachmentService {
	/**
	 * 上传附件
	 *
	 */
	@PostMapping("/upload")
	R<Boolean> uploadAttachment(@RequestBody SysAttachment sysAttachment);
	
	@PostMapping("/upload/uploadAttach")
	R<Boolean> saveAttach(@RequestParam(value="file",required=false)MultipartFile file,@RequestParam Integer userId,HttpServletRequest request);
}
