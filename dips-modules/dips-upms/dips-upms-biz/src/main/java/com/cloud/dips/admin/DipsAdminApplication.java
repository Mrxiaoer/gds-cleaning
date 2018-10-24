/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin;


import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

import com.cloud.dips.common.security.feign.EnableDipsFeignClients;
import com.cloud.dips.common.swagger.annotation.EnableDipsSwagger2;

/**
 * @author Wilson
 * @date 2018年06月21日
 * 用户统一管理系统
 */
@EnableDipsSwagger2
@SpringCloudApplication
@EnableDipsFeignClients
public class DipsAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(DipsAdminApplication.class, args);
	}

}
