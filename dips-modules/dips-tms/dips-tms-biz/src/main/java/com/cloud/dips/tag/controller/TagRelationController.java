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
import com.cloud.dips.common.security.service.DipsUser;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.entity.GovTagRelation;
import com.cloud.dips.tag.api.entity.GovTagRelationType;
import com.cloud.dips.tag.service.GovTagRelationService;
import com.cloud.dips.tag.service.GovTagRelationTypeService;
import com.cloud.dips.tag.service.GovTagService;
import com.google.common.collect.Maps;
import com.hankcs.hanlp.HanLP;

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
	private GovTagRelationTypeService govTagRelationTypeService;

	@Autowired
	private GovTagService tagService;

	private static String RD="relationId";
	private static String NUMBER="number";
	private static String NODE="node";
	private static String TKW="tagKeyWords";

	@SysLog("添加标签关联")
	@PostMapping("/saveTagRelation")
	@ApiOperation(value = "添加标签关联", notes = "添加标签关联", httpMethod = "POST")
	public R<Boolean> saveTagRelation(@RequestParam Map<String, Object> params) {
		
		String number = params.getOrDefault(NUMBER, "def").toString();
		GovTagRelationType govTagRelationType = new GovTagRelationType();
		EntityWrapper<GovTagRelationType> e = new EntityWrapper<GovTagRelationType>();
		e.where("type_number = {0}", number);
		govTagRelationType = govTagRelationTypeService.selectOne(e);
		
		if (StrUtil.isBlank(params.getOrDefault(RD, "").toString()) ||
			StrUtil.isBlank(params.getOrDefault(NODE, "").toString()) ||
			govTagRelationType == null) {
			return new R<>(false);
		} else {
			
			Integer gRelationId = Integer.parseInt(params.get(RD).toString());
			String gNode = params.get(NODE).toString();
			
			EntityWrapper<GovTagRelation> e2 = new EntityWrapper<GovTagRelation>();
			e2.where("relation_id = {0}", gRelationId)
				.where("node = {0}", gNode)
				.where("type_id = {0}", govTagRelationType.getTypeId());
			List<GovTagRelation> list=service.selectList(e2);
			for(GovTagRelation govTagRelation:list){
				GovTag tag = tagService.selectById(govTagRelation.getTagId());
				tag.setRefers(tag.getRefers()-1);
				tagService.updateById(tag);
			}
			service.delete(e2);

			if(StrUtil.isNotBlank(params.getOrDefault(TKW, "").toString())){
				String[] tagKeyWords = params.getOrDefault(TKW, "").toString().split(",");
				for (String tagname : tagKeyWords) {
					EntityWrapper<GovTag> e3 = new EntityWrapper<GovTag>();
					e3.where("name = {0}", tagname);
					GovTag tag = tagService.selectOne(e3);

					GovTagRelation bean = new GovTagRelation();
					bean.setNode(gNode);
					bean.setTypeId(govTagRelationType.getTypeId());
					bean.setRelationId(gRelationId);
					if (tag != null) {
						tag.setRefers(tag.getRefers()+1);
						tagService.updateById(tag);
						bean.setTagId(tag.getTagId());
					} else {
						tag = new GovTag();
						tag.setName(tagname);
						DipsUser user = SecurityUtils.getUser();
						tag.setCreatorId(user.getId());
						tag.applyDefaultValue();
						tag.setRefers(1);
						tag = tagService.save(tag);
						bean.setTagId(tag.getTagId());
					}

					EntityWrapper<GovTagRelation> e4 = new EntityWrapper<GovTagRelation>();
					e4.where("relation_id = {0}", bean.getRelationId())
						.where("node = {0}", bean.getNode())
						.where("type_id = {0}", bean.getTypeId())
						.where("tag_id = {0}", bean.getTagId());
					if (service.selectOne(e4) == null) {
						service.insert(bean);
					}
                      
				}
			}
			return new R<>(true);
		}
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
	
	@PostMapping("/extractTag")
	@ApiOperation(value = "提取标签", notes = "根据内容提取标签", httpMethod = "POST")
	public List<String> extractTag(@RequestParam String content,@RequestParam Integer num) {
		List<String> keywordList = HanLP.extractKeyword(content, num);
		return keywordList;
	}

	@SysLog("标签合并")
	@PostMapping("/tagRelationMerge")
	@ApiOperation(value = "标签合并", notes = "标签合并", httpMethod = "POST")
	public R<Boolean> tagRelationMerge(@RequestParam String tagName1,@RequestParam String tagName2) {
		EntityWrapper<GovTag> e = new EntityWrapper<GovTag>();
		e.where("name = {0}", tagName1);
		GovTag tag1 = tagService.selectOne(e);
		EntityWrapper<GovTag> e1 = new EntityWrapper<GovTag>();
		e1.where("name = {0}", tagName2);
		GovTag tag2 = tagService.selectOne(e1);
		if(tag1==null || tag2==null){
			return new R<>(false);	
		}else{
			EntityWrapper<GovTagRelation> e2 = new EntityWrapper<GovTagRelation>();
			e2.where("tag_id = {0}", tag2.getTagId());
			List<GovTagRelation> list=service.selectList(e2);
			tag2.setRefers(0);
			tagService.updateById(tag2);
			Map<String, Object> params=Maps.newHashMapWithExpectedSize(4);
			Integer num=list.size();
			for(GovTagRelation bean:list){
				EntityWrapper<GovTagRelation> e3 = new EntityWrapper<GovTagRelation>();
				params.put("tag_id",tag1.getTagId());
				params.put("relation_id",bean.getRelationId());
				params.put("type_id",bean.getTypeId());
				params.put("node",bean.getNode());
				e3.allEq(params);
				GovTagRelation govTagRelation=service.selectOne(e3);
				if(govTagRelation!=null){
					params.put("tag_id",bean.getTagId());
					service.deleteByMap(params);
					num--;
				}
			}	
			service.updateForSet("tag_id="+tag1.getTagId(), e2);
			tag1.setRefers(tag1.getRefers()+num);
			tagService.updateById(tag1);
			return new R<>(true);
		}	
	}
}
