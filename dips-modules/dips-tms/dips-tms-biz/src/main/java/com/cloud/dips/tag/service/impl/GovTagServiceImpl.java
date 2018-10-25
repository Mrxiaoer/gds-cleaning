package com.cloud.dips.tag.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.vo.GovTagVO;
import com.cloud.dips.tag.mapper.GovTagMapper;
import com.cloud.dips.tag.service.GovTagService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZB
 */
@Slf4j
@Service
public class GovTagServiceImpl extends ServiceImpl<GovTagMapper, GovTag>
		implements GovTagService {

	@Autowired
	private GovTagMapper mapper;
	

	/**
	 * 分页查询标签
	 *
	 * @param query 查询条件
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<GovTagVO> selectAllPage(Query query) {

		Object tagname = query.getCondition().get("tagname");
		Object typename = query.getCondition().get("typename");

		query.setRecords(mapper.selectGovTagVoPage(query, tagname,typename));

		return query;

	}
	
	/**
	 * 通过ID查询标签
	 *
	 * @param ID
	 * 
	 * @return 标签
	 */
	@Override
	public GovTagVO selectGovTagVoById(Integer id) {
		return mapper.selectGovTagVoById(id);
	}


	/**
	 * 删除标签
	 *
	 * @param govTag 标签
	 *            
	 * @return Boolean
	 */
	@Override
	public Boolean deleteGovTagById(GovTag govTag) {
		this.deleteById(govTag.getTagId());
		return Boolean.TRUE;
	}

	/**
	 * 保存标签
	 * 
	 * @param govTag
	 * 
	 * @return
	 */
	@Override
	public GovTag save(GovTag govTag) {
		this.insert(govTag);
		return govTag;
	}
	
	/**
	 * 根据标签名称查标签
	 * @param tagName
	 * @return
	 */
	@Override
	public Integer findByGovTagName(String tagName){
		return mapper.findByGovTagName(tagName);
	}


}
