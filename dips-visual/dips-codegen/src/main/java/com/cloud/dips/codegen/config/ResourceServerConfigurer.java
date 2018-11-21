/*
 *    Copyright (c) 2018-2025, BigPan All rights reserved.
 */

package com.cloud.dips.codegen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.cloud.dips.common.security.component.BaseResourceServerConfigurerAdapter;

import lombok.AllArgsConstructor;

/**
 * @author BigPan
 * @date 2018/6/22
 */
@Configuration
@EnableResourceServer
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfigurer extends BaseResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/actuator/**", "/v2/api-docs").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable();
	}

	/**
	 * 重写抽象类实现，不需要调用feign 获取 userdetils
	 *
	 * @param resources
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.authenticationEntryPoint(resourceAuthExceptionEntryPoint)
		.accessDeniedHandler(dipsAccessDeniedHandler);
	}
}
