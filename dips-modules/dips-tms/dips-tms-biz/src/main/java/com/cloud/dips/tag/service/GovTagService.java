package com.cloud.dips.tag.service;


import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.vo.GovTagVO;
import com.cloud.dips.tag.api.vo.MapVO;

/**
 * @author ZB
 */
public interface GovTagService extends IService<GovTag> {
	/**
	 * 分页查询信息
	 *
	 * @param query 查询条件
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Page<GovTagVO> selectAllPage(Query query);
	/**
	 * 通过ID查询标签
	 *
	 * @param id
	 * 
	 * @return 标签信息
	 */
	GovTagVO selectGovTagVoById(Integer id);
	/**
	 * 删除标签
	 *
	 * @param govTag
	 * 
	 * @return boolean
	 */
	Boolean deleteGovTagById(GovTag govTag);
	/**
	 * 保存标签
	 * 
	 * @param govTag
	 * 
	 * @return
	 */
	GovTag save(GovTag govTag,List<Integer> typeIds);
	
	/**
	 * 根据标签名称查标签
	 * @param tagName
	 * @return
	 */
	Integer findByGovTagName(String tagName);
	/**
	 * 根据标签分类统计标签
	 * @return
	 */
	List<MapVO> coutnByType();
	/**
	 * 根据日期统计标签
	 * @param date
	 * @return
	 */
	List<MapVO> coutnByDate(Date date);
	/**
	 * 获取所有标签集合
	 * @return
	 */
	List<GovTagVO> getAllTag();
	
}

