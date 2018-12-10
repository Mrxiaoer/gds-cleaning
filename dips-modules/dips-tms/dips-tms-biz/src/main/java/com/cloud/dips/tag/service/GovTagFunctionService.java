package com.cloud.dips.tag.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.tag.api.entity.GovTagFunction;

/**
 * @author ZB
 */
public interface GovTagFunctionService extends IService<GovTagFunction> {

	GovTagFunction getByNumber(String number);
}

