package com.cloud.dips.tag.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.tag.api.entity.GovTagTypeRelation;


/**
 * @author ZB
 */
public interface GovTagTypeRelationService extends IService<GovTagTypeRelation> {

	/**
	 * 保存标签与分类关联
	 * @param tagId
	 * @param typeIds
	 * @return
	 */
	public Boolean saveTagTypeRelation(Integer tagId,List<Integer> typeIds);
}

