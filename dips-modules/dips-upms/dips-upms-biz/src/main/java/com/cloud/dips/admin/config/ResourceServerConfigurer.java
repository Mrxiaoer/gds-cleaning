/*
 *
 *      Copyright (c) 2018-2025, BigPan All rights reserved.
 *
 */

package com.cloud.dips.admin.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.cloud.dips.common.security.component.BaseResourceServerConfigurerAdapter;

/**
 * @author BigPan
 * @date 2018/6/22
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfigurer extends BaseResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/actuator/**"
				, "/user/info/*"
				, "/social/info/**"
				, "/log/**"
				, "/upload/**"
				, "/v2/api-docs").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable();
	}


}
