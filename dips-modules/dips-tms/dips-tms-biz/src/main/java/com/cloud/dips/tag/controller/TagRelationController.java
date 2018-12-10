package com.cloud.dips.tag.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.entity.GovTagRelation;
import com.cloud.dips.tag.service.GovTagRelationService;
import com.cloud.dips.tag.service.GovTagService;

import cn.hutool.core.util.StrUtil;
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

	@Autowired
	private GovTagService tagService;

	private static String RD="relationId";
	private static String NODE="node";

	@SysLog("添加标签关联")
	@PostMapping("/saveTagRelation")
	@ApiOperation(value = "添加标签关联", notes = "添加标签关联", httpMethod = "POST")
	public R<Boolean> saveTagRelation(@RequestParam Map<String, Object> params) {
		return new R<Boolean>(service.saveTagRelation(params));
	}

	@SysLog("删除标签关联")
	@PostMapping("/deleteTagRelation")
	@ApiOperation(value = "删除标签关联", notes = "删除标签关联", httpMethod = "POST")
	public R<Boolean> deleteTagRelation(@RequestParam Map<String, Object> params) {
		if (StrUtil.isBlank(params.getOrDefault(RD, "").toString()) ||
			StrUtil.isBlank(params.getOrDefault(NODE, "").toString())) {
			return new R<>(false);
		} else {
			Integer gRelationId = Integer.parseInt(params.get(RD).toString());
			String gNode = params.get(NODE).toString();
			EntityWrapper<GovTagRelation> e = new EntityWrapper<GovTagRelation>();
			e.where("relation_id = {0}", gRelationId)
				.where("node = {0}", gNode);
			List<GovTagRelation> list=service.selectList(e);
			for(GovTagRelation govTagRelation:list){
				GovTag tag = tagService.selectById(govTagRelation.getTagId());
				tag.setRefers(tag.getRefers()-1);
				tagService.updateById(tag);
			}
			service.delete(e);
		}
		return new R<>(true);
	}

}
