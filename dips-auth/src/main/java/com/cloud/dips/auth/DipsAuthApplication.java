/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.auth;


import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Wilson
 * @date 2018年06月21日
 * 认证授权中心
 */
@SpringCloudApplication
@EnableFeignClients({"com.cloud.dips.admin.api.feign"})
public class DipsAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(DipsAuthApplication.class, args);
	}
}
