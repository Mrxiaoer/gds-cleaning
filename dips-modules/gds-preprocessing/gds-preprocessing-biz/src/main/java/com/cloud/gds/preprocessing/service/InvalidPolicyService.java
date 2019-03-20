package com.cloud.gds.preprocessing.service;

import com.cloud.gds.preprocessing.entity.BasePolicy;

import java.util.List;

/**
 * 清洗国策数据
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-19
 */
public interface InvalidPolicyService {

	/**
	 * 清除采集表中相同名称的数据
	 *
	 * @return
	 */
	boolean cleanInvalidInScape();
}
