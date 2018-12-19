package com.cloud.dips.tag.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.tag.api.entity.GovTagRelation;
import com.cloud.dips.tag.api.vo.GovTagRelationVO;


/**
 * @author ZB
 */
public interface GovTagRelationService extends IService<GovTagRelation> {

	/**
	 * 保存关联
	 * @param params
	 * @return
	 */
	public Boolean saveTagRelation(Map<String, Object> params);
	/**
	 * 删除关联
	 * @param relationId
	 * @param node
	 * @return
	 */
	public Boolean deleteTagRelation(Integer relationId,String node);
	/**
	 * 获取标签集合
	 * @param relationId
	 * @param node
	 * @param fob
	 * @return
	 */
	public List<GovTagRelationVO> getTags(Integer relationId,String node,String fob);
	
}

