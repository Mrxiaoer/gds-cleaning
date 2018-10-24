/*
 *    Copyright (c) 2018-2025, BigPan All rights reserved.
 *
 */

package com.cloud.dips.codegen.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author BigPan
 * @date 2017/10/29
 */
@Configuration
@MapperScan("com.cloud.dips.codegen.mapper")
public class MybatisPlusConfigurer {
	/**
	 * 分页插件
	 *
	 * @return PaginationInterceptor
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
}
