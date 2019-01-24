/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.gds.cleaning;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.SpringCloudApplication;

import com.cloud.dips.common.security.feign.EnableDipsFeignClients;
import com.cloud.dips.common.swagger.annotation.EnableDipsSwagger2;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.RequestContextFilter;

/**
 * @author Wilson
 * @date 2018年06月21日
 * 用户统一管理系统
 */
@EnableDipsSwagger2
@SpringCloudApplication
@EnableDipsFeignClients
public class GdsCleaningApplication {
	public static void main(String[] args) {
		SpringApplication.run(GdsCleaningApplication.class, args);
	}

}
