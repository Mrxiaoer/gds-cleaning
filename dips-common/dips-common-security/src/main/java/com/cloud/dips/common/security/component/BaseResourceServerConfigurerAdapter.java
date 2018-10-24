/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: BigPan
 *
 */

package com.cloud.dips.common.security.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * @author BigPan
 * @date 2018/6/22
 */
public abstract class BaseResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {
	@Autowired
	protected ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;
	@Autowired
	protected DipsAccessDeniedHandler dipsAccessDeniedHandler;
	@Autowired
	protected RemoteTokenServices remoteTokenServices;
	@Autowired
	protected UserDetailsService userDetailsService;

	@Override
	public abstract void configure(HttpSecurity http) throws Exception;

	/**
	 * why add  resourceId
	 * https://stackoverflow.com/questions/28703847/how-do-you-set-a-resource-id-for-a-token
	 *
	 * @param resources
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
		DefaultUserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();
		userTokenConverter.setUserDetailsService(userDetailsService);
		accessTokenConverter.setUserTokenConverter(userTokenConverter);
		remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
		resources.authenticationEntryPoint(resourceAuthExceptionEntryPoint)
			.accessDeniedHandler(dipsAccessDeniedHandler)
			.tokenServices(remoteTokenServices);
	}


}
