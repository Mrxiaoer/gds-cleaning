package com.cloud.dips.tag.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.security.service.DipsUser;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.tag.dto.GovTagDescriptionDTO;
import com.cloud.dips.tag.entity.GovTagDescription;
import com.cloud.dips.tag.service.GovTagDescriptionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/tagDescription")
public class TagDescriptionController {
	@Autowired
	private GovTagDescriptionService service;

	
	@SysLog("添加标签描述")
	@PostMapping("/saveTagDescription")
	@ApiOperation(value = "添加标签描述", notes = "添加标签描述", httpMethod = "POST")
	public R<Boolean> saveTag(@RequestBody GovTagDescriptionDTO govTagDescriptionDto) {
			GovTagDescription govTagDescription = new GovTagDescription();
			BeanUtils.copyProperties(govTagDescriptionDto, govTagDescription);
			// 获取当前用户 
			DipsUser user = SecurityUtils.getUser();
			govTagDescription.setCreatorId(user.getId());
			govTagDescription.applyDefaultValue();
			return new R<>(service.insert(govTagDescription));
	}


	
}
