package com.cloud.gds.cleaning.config;

import com.cloud.dips.common.security.component.BaseResourceServerConfigurerAdapter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.RequestContextFilter;

/**
 * @Author : lolilijve
 * @Email : 1042703214@qq.com
 * @Date : 2018-11-21
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Component
public class ResourceServerConfigurer extends BaseResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/v2/api-docs","/actuator/**","/clean_pool/**","/data_pool/**","/data_rule/**","/result_set/**","/analysis/**","/recycle/bin/**","/excel/**","/combine/**")
			.permitAll().anyRequest().authenticated().and().csrf().disable();
	}

}
