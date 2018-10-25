package com.cloud.dips.tag.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.dips.admin.api.dto.UserInfo;
import com.cloud.dips.admin.api.feign.RemoteUserService;
import com.cloud.dips.common.core.constant.SecurityConstants;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.entity.GovTagRelation;
import com.cloud.dips.tag.api.entity.GovTagRelationType;
import com.cloud.dips.tag.service.GovTagRelationService;
import com.cloud.dips.tag.service.GovTagRelationTypeService;
import com.cloud.dips.tag.service.GovTagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tagRelation")
public class TagRelationController {
	@Autowired
	private GovTagRelationService service;

	@Autowired
	private GovTagRelationTypeService govTagRelationTypeService;

	@Autowired
	private GovTagService tagService;

	@Autowired
	private RemoteUserService remoteUserService;


	@SysLog("添加标签关联")
	@PostMapping("/saveTagRelation")
	@ApiOperation(value = "添加标签关联", notes = "添加标签关联", httpMethod = "POST")
	public R<Boolean> saveTagRelation(@RequestParam Map<String, Object> params) {
		
		String number = params.getOrDefault("number", "def").toString();
		GovTagRelationType govTagRelationType = new GovTagRelationType();
		EntityWrapper<GovTagRelationType> e = new EntityWrapper<GovTagRelationType>();
		e.where("g_type_number = {0}", number);
		govTagRelationType = govTagRelationTypeService.selectOne(e);
		
		if (StrUtil.isBlank(params.getOrDefault("gRelationId", "").toString()) ||
			StrUtil.isBlank(params.getOrDefault("gNode", "").toString()) ||
			govTagRelationType == null) {
			return new R<>(false);
		} else {
			
			Integer gRelationId = Integer.parseInt(params.get("gRelationId").toString());
			String gNode = params.get("gNode").toString();
			
			EntityWrapper<GovTagRelation> e2 = new EntityWrapper<GovTagRelation>();
			e2.where("g_relation_id = {0}", gRelationId)
				.where("g_node = {0}", gNode)
				.where("g_type_id = {0}", govTagRelationType.getTypeId());
			List<GovTagRelation> list=service.selectList(e2);
			for(GovTagRelation govTagRelation:list){
				GovTag tag = tagService.selectById(govTagRelation.getGTagId());
				tag.setRefers(tag.getRefers()-1);
				tagService.updateById(tag);
			}
			service.delete(e2);

			if(StrUtil.isNotBlank(params.getOrDefault("tagKeyWords", "").toString())){
				String[] tagKeyWords = params.getOrDefault("tagKeyWords", "").toString().split(",");
				for (String tagname : tagKeyWords) {
					EntityWrapper<GovTag> e3 = new EntityWrapper<GovTag>();
					e3.where("g_name = {0}", tagname);
					GovTag tag = tagService.selectOne(e3);

					GovTagRelation bean = new GovTagRelation();
					bean.setGNode(gNode);
					bean.setGTypeId(govTagRelationType.getTypeId());
					bean.setGRelationId(gRelationId);
					if (tag != null) {
						tag.setRefers(tag.getRefers()+1);
						tagService.updateById(tag);
						bean.setGTagId(tag.getTagId());
					} else {
						tag = new GovTag();
						tag.setName(tagname);
						String username = SecurityUtils.getUser().getUsername();
						R<UserInfo> userInfo = remoteUserService.info(username, SecurityConstants.FROM_IN);
						tag.setCreatorId(userInfo.getData().getSysUser().getUserId());
						tag.applyDefaultValue();
						tag.setRefers(1);
						tag = tagService.save(tag);
						bean.setGTagId(tag.getTagId());
					}

					EntityWrapper<GovTagRelation> e4 = new EntityWrapper<GovTagRelation>();
					e4.where("g_relation_id = {0}", bean.getGRelationId())
						.where("g_node = {0}", bean.getGNode())
						.where("g_type_id = {0}", bean.getGTypeId())
						.where("g_tag_id = {0}", bean.getGTagId());
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
		if (StrUtil.isBlank(params.getOrDefault("gRelationId", "").toString()) ||
			StrUtil.isBlank(params.getOrDefault("gNode", "").toString())) {
			return new R<>(false);
		} else {
			Integer gRelationId = Integer.parseInt(params.get("gRelationId").toString());
			String gNode = params.get("gNode").toString();
			EntityWrapper<GovTagRelation> e = new EntityWrapper<GovTagRelation>();
			e.where("g_relation_id = {0}", gRelationId)
				.where("g_node = {0}", gNode);
			List<GovTagRelation> list=service.selectList(e);
			for(GovTagRelation govTagRelation:list){
				GovTag tag = tagService.selectById(govTagRelation.getGTagId());
				tag.setRefers(tag.getRefers()-1);
				tagService.updateById(tag);
			}
			service.delete(e);
		}
		return new R<>(true);
	}
}
