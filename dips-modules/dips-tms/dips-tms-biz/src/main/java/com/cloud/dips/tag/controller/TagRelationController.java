package com.cloud.dips.tag.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.tag.api.vo.CommonVO;
import com.cloud.dips.tag.api.vo.GovTagRelationVO;
import com.cloud.dips.tag.service.GovTagRelationService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author ZB
 *
 */
@RestController
@RequestMapping("/relation")
public class TagRelationController {
	@Autowired
	private GovTagRelationService service;

	@SysLog("添加标签关联")
	@PostMapping("/save")
	@ApiOperation(value = "添加标签关联", notes = "添加标签关联", httpMethod = "POST")
	public R<Boolean> saveTagRelation(@RequestParam Map<String, Object> params) {
		return new R<Boolean>(service.saveTagRelation(params));
	}

	@SysLog("删除标签关联")
	@PostMapping("/delete")
	@ApiOperation(value = "删除标签关联", notes = "删除标签关联", httpMethod = "POST")
	public R<Boolean> deleteTagRelation(@RequestParam Integer relationId,@RequestParam String node) {
		return new R<Boolean>(service.deleteTagRelation(relationId, node));
	}
	
	@GetMapping("/get_tags")
	@ApiOperation(value = "获取关联标签", notes = "获取关联标签", httpMethod = "GET")
	public R<Map<String, List<CommonVO>>> getTags(@RequestParam Map<String, Object> params) {
		String node = params.getOrDefault("node", "").toString();
		String relationId = params.getOrDefault("relationId", "").toString();
		if(StringUtils.isNotBlank(node) && StringUtils.isNumeric(relationId)){
			String fob = params.getOrDefault("fob", "b").toString();
			List<GovTagRelationVO> relations=service.getTags(Integer.parseInt(relationId), node,fob);
			Map<String, List<CommonVO>> map=new HashMap<String, List<CommonVO>>(0);
			for(GovTagRelationVO relation:relations){
				map.put(relation.getTypeNumber(), relation.getTagList());
			}
			return new R<>(map);
		}else{
			return new R<>(null,"缺少必要参数或参数有误！");
		}
	}


}
