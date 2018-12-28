package com.cloud.dips.tag.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.cloud.dips.tag.api.entity.GovTagTypeRelation;
import com.cloud.dips.tag.mapper.GovTagTypeRelationMapper;
import com.cloud.dips.tag.service.GovTagTypeRelationService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZB
 */
@Slf4j
@Service
public class GovTagTypeRelationServiceImpl extends ServiceImpl<GovTagTypeRelationMapper, GovTagTypeRelation>
		implements GovTagTypeRelationService {
	
	@Override
	public Boolean saveTagTypeRelation(Integer tagId, List<Integer> typeIds) {
		this.deleteById(tagId);
		if(CollectionUtils.isEmpty(typeIds)){
			return Boolean.TRUE;
		}else{
			List<GovTagTypeRelation> list=new ArrayList<GovTagTypeRelation>();
			typeIds=removeDuplicate(typeIds);
			for(Integer typeId:typeIds){
				GovTagTypeRelation bean=new GovTagTypeRelation(tagId,typeId);
				list.add(bean);
			}
			return this.insertBatch(list);
		}	
	}

	/**
	 * list去重
	 * @param list
	 * @return
	 */
	public static List<Integer> removeDuplicate(List<Integer> list) {   
	    HashSet<Integer> h = new HashSet<Integer>(list);   
	    list.clear();   
	    list.addAll(h);   
	    return list;   
	}
	
	
	
	
}
