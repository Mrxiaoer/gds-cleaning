package com.cloud.gds.preprocessing.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.cloud.dips.common.core.datascope.DataScopeInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-3-19
 */
@Configuration
@MapperScan("com.cloud.gds.preprocessing.mapper")
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

	/**
	 * 数据权限插件
	 *
	 * @return DataScopeInterceptor
	 */
	@Bean
	public DataScopeInterceptor dataScopeInterceptor() {
		return new DataScopeInterceptor();
	}

}
