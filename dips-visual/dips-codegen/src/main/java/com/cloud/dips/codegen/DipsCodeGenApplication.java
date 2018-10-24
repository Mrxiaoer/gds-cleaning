/*
 *    Copyright (c) 2018-2025, BigPan All rights reserved.
 */

package com.cloud.dips.codegen;

import com.cloud.dips.common.swagger.annotation.EnableDipsSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author BigPan
 * @date 2018/07/29
 * 代码生成模块
 */
@EnableDipsSwagger2
@SpringCloudApplication
public class DipsCodeGenApplication {

	public static void main(String[] args) {
		SpringApplication.run(DipsCodeGenApplication.class, args);
	}
}
