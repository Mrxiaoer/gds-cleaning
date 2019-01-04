package com.cloud.dips.tag.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.security.service.DipsUser;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.entity.GovTagRelation;
import com.cloud.dips.tag.api.entity.GovTagRelationType;
import com.cloud.dips.tag.api.vo.GovTagRelationVO;
import com.cloud.dips.tag.mapper.GovTagRelationMapper;
import com.cloud.dips.tag.service.GovTagRelationService;
import com.cloud.dips.tag.service.GovTagRelationTypeService;
import com.cloud.dips.tag.service.GovTagService;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZB
 */
@Slf4j
@Service
public class GovTagRelationServiceImpl extends ServiceImpl<GovTagRelationMapper, GovTagRelation>
		implements GovTagRelationService {
	
	@Autowired
	private GovTagRelationTypeService govTagRelationTypeService;
	
	@Autowired
	private GovTagService tagService;
	
	private static String RD="relationId";
	private static String NUMBER="number";
	private static String NODE="node";
	private static String TKW="tagKeyWords";
	
	@Override
	public Boolean saveTagRelation(Map<String, Object> params) {
		String number = params.getOrDefault(NUMBER, "def").toString();
		GovTagRelationType govTagRelationType = new GovTagRelationType();
		EntityWrapper<GovTagRelationType> e = new EntityWrapper<GovTagRelationType>();
		e.where("type_number = {0}", number);
		govTagRelationType = govTagRelationTypeService.selectOne(e);
		if (StrUtil.isBlank(params.getOrDefault(RD, "").toString()) ||
			StrUtil.isBlank(params.getOrDefault(NODE, "").toString()) ||
			govTagRelationType == null) {
			return Boolean.FALSE;
		} else {
			Integer gRelationId = Integer.parseInt(params.get(RD).toString());
			String gNode = params.get(NODE).toString();
			EntityWrapper<GovTagRelation> e2 = new EntityWrapper<GovTagRelation>();
			e2.where("relation_id = {0}", gRelationId)
				.where("node = {0}", gNode)
				.where("type_id = {0}", govTagRelationType.getTypeId());
			List<GovTagRelation> list=this.selectList(e2);
			for(GovTagRelation govTagRelation:list){
				GovTag tag = tagService.selectById(govTagRelation.getTagId());
				tag.setRefers(tag.getRefers()-1);
				tagService.updateById(tag);
			}
			this.delete(e2);
			if(StrUtil.isNotBlank(params.getOrDefault(TKW, "").toString())){
				String[] tagKeyWords = params.getOrDefault(TKW, "").toString().split(",");
				for (String tagname : tagKeyWords) {
					if(tagname.length()<=60){
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
						tag.setRefers(1);
						tag = tagService.save(tag,null);
						bean.setTagId(tag.getTagId());
					}
					EntityWrapper<GovTagRelation> e4 = new EntityWrapper<GovTagRelation>();
					e4.where("relation_id = {0}", bean.getRelationId())
						.where("node = {0}", bean.getNode())
						.where("type_id = {0}", bean.getTypeId())
						.where("tag_id = {0}", bean.getTagId());
					if (this.selectOne(e4) == null) {
						this.insert(bean);
					}   
				}
				}
			}
			return Boolean.TRUE;
		}
	}

	@Override
	public Boolean deleteTagRelation(Integer relationId, String node) {
		EntityWrapper<GovTagRelation> e1= new EntityWrapper<GovTagRelation>();
		e1.eq("relation_id", relationId).eq("node", node);
		e1.setSqlSelect("tag_id");
		List<Object> objects=this.selectObjs(e1);
		for(Object object:objects){
			Integer tagId=Integer.parseInt(object.toString());
			GovTag tag = tagService.selectById(tagId);
			if(tag!=null){
				EntityWrapper<GovTagRelation> e2= new EntityWrapper<GovTagRelation>();
				e2.eq("relation_id", relationId).eq("node", node).eq("tag_id", tagId);
				tag.setRefers(tag.getRefers()-this.selectCount(e2));
				tagService.updateById(tag);
			}	
		}
		return this.delete(e1);
	}

	@Override
	public List<GovTagRelationVO> getTags(Integer relationId, String node,String fob) {
		return baseMapper.getTags(relationId, node,fob);
	}

}
