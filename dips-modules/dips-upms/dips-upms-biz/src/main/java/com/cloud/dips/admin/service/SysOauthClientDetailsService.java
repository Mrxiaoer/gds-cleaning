package com.cloud.dips.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.entity.SysOauthClientDetails;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
public interface SysOauthClientDetailsService extends IService<SysOauthClientDetails> {

	/**
	 * 通过ID删除客户端
	 *
	 * @param id
	 * @return
	 */
	Boolean deleteClientDetailsById(String id);

	/**
	 * 根据客户端信息
	 *
	 * @param sysOauthClientDetails
	 * @return
	 */
	Boolean updateClientDetailsById(SysOauthClientDetails sysOauthClientDetails);
}
