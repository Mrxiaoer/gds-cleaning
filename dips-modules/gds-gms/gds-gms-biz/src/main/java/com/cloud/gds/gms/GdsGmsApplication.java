package com.cloud.gds.gms;

import com.cloud.dips.common.security.feign.EnableDipsFeignClients;
import com.cloud.dips.common.swagger.annotation.EnableDipsSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 国策基础启动类
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-03
 */
@EnableDipsSwagger2
@SpringCloudApplication
@EnableDipsFeignClients
public class GdsGmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(GdsGmsApplication.class, args);
	}
}
