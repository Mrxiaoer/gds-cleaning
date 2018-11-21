package com.cloud.dips.tag;

import com.cloud.dips.common.security.feign.EnableDipsFeignClients;
import com.cloud.dips.common.swagger.annotation.EnableDipsSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 
 * @author ZB
 *
 */
@EnableDipsSwagger2
@SpringCloudApplication
@EnableDipsFeignClients
public class DipsTagApplication {
	public static void main(String[] args) {
		SpringApplication.run(DipsTagApplication.class, args);
	}
}
