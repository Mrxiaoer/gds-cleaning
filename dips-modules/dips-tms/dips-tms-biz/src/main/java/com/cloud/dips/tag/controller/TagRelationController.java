package com.cloud.dips.tag.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.tag.service.GovTagRelationService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author ZB
 *
 */
@RestController
@RequestMapping("/tagRelation")
public class TagRelationController {
	@Autowired
	private GovTagRelationService service;

	@SysLog("添加标签关联")
	@PostMapping("/saveTagRelation")
	@ApiOperation(value = "添加标签关联", notes = "添加标签关联", httpMethod = "POST")
	public R<Boolean> saveTagRelation(@RequestParam Map<String, Object> params) {
		return new R<Boolean>(service.saveTagRelation(params));
	}

	@SysLog("删除标签关联")
	@PostMapping("/deleteTagRelation")
	@ApiOperation(value = "删除标签关联", notes = "删除标签关联", httpMethod = "POST")
	public R<Boolean> deleteTagRelation(@RequestParam Integer relationId,@RequestParam String node) {
		return new R<Boolean>(service.deleteTagRelation(relationId, node));
	}

}
