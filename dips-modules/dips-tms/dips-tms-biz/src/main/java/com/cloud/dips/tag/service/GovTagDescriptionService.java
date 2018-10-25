package com.cloud.dips.tag.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.tag.api.entity.GovTagDescription;

/**
 * @author ZB
 */
public interface GovTagDescriptionService extends IService<GovTagDescription> {

	Boolean deleteByTagId(Integer tagId);
}

