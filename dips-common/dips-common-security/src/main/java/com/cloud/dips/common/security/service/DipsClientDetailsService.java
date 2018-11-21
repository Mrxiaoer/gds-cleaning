package com.cloud.dips.common.security.service;

import javax.sql.DataSource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import com.cloud.dips.common.core.constant.SecurityConstants;

/** 
* @author : DingBin
* @date 创建时间：2018年11月5日 下午2:08:26 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class DipsClientDetailsService extends JdbcClientDetailsService {

	public DipsClientDetailsService(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * 重写原生方法支持redis缓存
	 *
	 * @param clientId
	 * @return
	 * @throws InvalidClientException
	 */
	@Override
	@Cacheable(value = SecurityConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
	public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
		return super.loadClientByClientId(clientId);
	}
}

