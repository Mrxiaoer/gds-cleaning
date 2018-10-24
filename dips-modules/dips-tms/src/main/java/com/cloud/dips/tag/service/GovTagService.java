package com.cloud.dips.tag.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.tag.entity.GovTag;
import com.cloud.dips.tag.vo.GovTagVO;

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
	GovTag save(GovTag govTag);
	
	/**
	 * 根据标签名称查标签
	 * @param tagName
	 * @return
	 */
	Integer findByGovTagName(String tagName);
	
}

