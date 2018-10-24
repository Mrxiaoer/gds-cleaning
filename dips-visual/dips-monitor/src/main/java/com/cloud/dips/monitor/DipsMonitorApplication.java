/*
 *
 *      Copyright (c) 2018-2025, BigPan All rights reserved.
 *
 */

package com.cloud.dips.monitor;


import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author BigPan
 * @date 2018年06月21日
 * 监控中心
 */
@EnableAdminServer
@SpringBootApplication
public class DipsMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DipsMonitorApplication.class, args);
	}
}
