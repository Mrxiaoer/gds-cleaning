/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.eureka;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Wilson
 * @date 2018年06月21日
 * 服务中心
 */
@EnableEurekaServer
@SpringBootApplication
public class DipsEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DipsEurekaApplication.class, args);
	}
}
