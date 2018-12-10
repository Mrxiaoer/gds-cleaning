package com.cloud.dips.tag.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.tag.api.entity.GovTagDescription;
import com.cloud.dips.tag.api.vo.GovTagDescriptionVO;

/**
 * @author ZB
 */
public interface GovTagDescriptionService extends IService<GovTagDescription> {
	/**
	 * 删除标签
	 * @param tagId 标签id
	 * @return 布尔值
	 */
	Boolean deleteByTagId(Integer tagId);
	
	/**
	 * 分页查询描述
	 * @param query
	 * @return
	 */
	Page<GovTagDescriptionVO> selectAllPage(Query<GovTagDescriptionVO> query);
}

