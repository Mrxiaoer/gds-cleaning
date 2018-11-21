package com.cloud.dips.admin.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.entity.SysOauthClientDetails;
import com.cloud.dips.admin.mapper.SysOauthClientDetailsMapper;
import com.cloud.dips.admin.service.SysOauthClientDetailsService;
import com.cloud.dips.common.core.constant.SecurityConstants;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Service
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetails> implements SysOauthClientDetailsService {

	/**
	 * 通过ID删除客户端
	 *
	 * @param id
	 * @return
	 */
	@Override
	@CacheEvict(value = SecurityConstants.CLIENT_DETAILS_KEY, key = "#id")
	public Boolean deleteClientDetailsById(String id) {
		return this.deleteById(id);
	}

	/**
	 * 根据客户端信息
	 *
	 * @param clientDetails
	 * @return
	 */
	@Override
	@CacheEvict(value = SecurityConstants.CLIENT_DETAILS_KEY, key = "#clientDetails.clientId")
	public Boolean updateClientDetailsById(SysOauthClientDetails clientDetails) {
		return this.updateById(clientDetails);
	}
}
