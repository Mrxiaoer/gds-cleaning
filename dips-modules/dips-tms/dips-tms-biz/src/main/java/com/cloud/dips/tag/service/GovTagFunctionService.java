package com.cloud.dips.tag.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.tag.api.entity.GovTagFunction;

/**
 * @author ZB
 */
public interface GovTagFunctionService extends IService<GovTagFunction> {

	/**
	 * 根据编码找功能
	 * @param number
	 * @return
	 */
	GovTagFunction getByNumber(String number);
}

