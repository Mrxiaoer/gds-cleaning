package com.cloud.dips.gateway.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-26
 */
@Configuration
public class CorsConfig {

	@Bean
	public CorsWebFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedMethod(HttpMethod.GET);
		config.addAllowedMethod(HttpMethod.POST);
		config.addAllowedMethod(HttpMethod.PUT);
		config.addAllowedMethod(HttpMethod.OPTIONS);
		config.addAllowedMethod(HttpMethod.DELETE);
		config.addAllowedMethod(HttpMethod.PATCH);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.setAllowCredentials(true);
		List<String> strings = new ArrayList<>();
		strings.add("GET");
		strings.add("POST");
		strings.add("OPTIONS");
		strings.add("DELETE");
		strings.add("PUT");
		strings.add("PATCH");
		config.setAllowedMethods(strings);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
		source.registerCorsConfiguration("/**", config);

		return new CorsWebFilter(source);
	}
}
