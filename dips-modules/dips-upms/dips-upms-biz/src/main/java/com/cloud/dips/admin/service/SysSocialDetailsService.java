package com.cloud.dips.admin.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.entity.SysSocialDetails;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
public interface SysSocialDetailsService extends IService<SysSocialDetails> {

	/**
	 * 绑定社交账号
	 *
	 * @param state 类型
	 * @param code  code
	 * @return
	 */
	Boolean bindSocial(String state, String code);

	/**
	 * 通过客户端信息查询openId
	 *
	 * @param inStr appid @ code
	 * @return
	 */
	Map<String, String> findOpenId(String inStr);
}

