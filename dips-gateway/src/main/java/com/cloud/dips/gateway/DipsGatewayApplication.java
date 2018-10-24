/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author Wilson
 * @date 2018年06月21日
 * 网关应用
 */
@SpringCloudApplication
public class DipsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DipsGatewayApplication.class, args);
	}
}
