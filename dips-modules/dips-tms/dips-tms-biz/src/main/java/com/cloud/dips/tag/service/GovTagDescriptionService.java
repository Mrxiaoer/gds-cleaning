package com.cloud.dips.tag.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.tag.api.entity.GovTagDescription;

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
}

