package com.cloud.dips.tag;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

import com.cloud.dips.common.security.feign.EnableDipsFeignClients;
import com.cloud.dips.common.swagger.annotation.EnableDipsSwagger2;
@EnableDipsSwagger2
@SpringCloudApplication
@EnableDipsFeignClients
public class DipsTagApplication {
	public static void main(String[] args) {
		SpringApplication.run(DipsTagApplication.class, args);
	}
}
