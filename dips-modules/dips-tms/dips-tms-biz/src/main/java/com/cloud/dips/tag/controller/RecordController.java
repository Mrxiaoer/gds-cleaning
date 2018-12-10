package com.cloud.dips.tag.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.dips.tag.api.vo.CommonVO;
import com.cloud.dips.tag.api.vo.GovTagModificationRecordVO;
import com.cloud.dips.tag.service.GovTagMergeRecordService;
import com.cloud.dips.tag.service.GovTagModificationRecordService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author ZB
 *
 */
@RestController
@RequestMapping("/record")
public class RecordController {
	@Autowired
	private GovTagMergeRecordService mergeRecordservice;
	@Autowired
	private GovTagModificationRecordService modificationRecordservice;

	
	@GetMapping("/map/{tagId}")
	@ApiOperation(value = "标签合并记录集合", notes = "标签合并记录集合",httpMethod="GET")
	public Map<String, List<CommonVO>> recordMap(@PathVariable(value="tagId") Integer tagId) {
		Map<String, List<CommonVO>> map=new HashMap<String, List<CommonVO>>(0);
		map.put("merge", mergeRecordservice.selectMergeTag(tagId));
		map.put("include", mergeRecordservice.selectIncludeTag(tagId));
		return map;
	}

	@GetMapping("/list/{tagId}")
	@ApiOperation(value = "标签记录集合", notes = "标签记录集合",httpMethod="GET")
	public List<GovTagModificationRecordVO> recordList(@PathVariable(value="tagId") Integer tagId,
			@RequestParam(value="limit",required=false) Integer limit) {
		return modificationRecordservice.getByTagId(tagId, limit);
	}
	
}
