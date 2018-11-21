package com.cloud.dips.tag.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.dips.admin.api.dto.UserInfo;
import com.cloud.dips.admin.api.feign.RemoteUserService;
import com.cloud.dips.common.core.constant.SecurityConstants;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.tag.api.dto.GovTagDescriptionDTO;
import com.cloud.dips.tag.api.entity.GovTagDescription;
import com.cloud.dips.tag.service.GovTagDescriptionService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author ZB
 *
 */
@RestController
@RequestMapping("/tagDescription")
public class TagDescriptionController {
	@Autowired
	private GovTagDescriptionService service;
	
	@Autowired
	private RemoteUserService remoteUserService;

	
	@SysLog("添加标签描述")
	@PostMapping("/saveTagDescription")
	@ApiOperation(value = "添加标签描述", notes = "添加标签描述", httpMethod = "POST")
	public R<Boolean> saveTag(@RequestBody GovTagDescriptionDTO govTagDescriptionDto) {
			GovTagDescription govTagDescription = new GovTagDescription();
			BeanUtils.copyProperties(govTagDescriptionDto, govTagDescription);
			// 获取当前用户 
			String username = SecurityUtils.getUser().getUsername();
			R<UserInfo> userInfo = remoteUserService.info(username, SecurityConstants.FROM_IN);
			govTagDescription.setCreatorId(userInfo.getData().getSysUser().getId());
			govTagDescription.applyDefaultValue();
			return new R<>(service.insert(govTagDescription));
	}


	
}
