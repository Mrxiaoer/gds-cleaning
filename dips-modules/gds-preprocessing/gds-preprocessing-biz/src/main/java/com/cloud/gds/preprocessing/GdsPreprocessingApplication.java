package com.cloud.gds.preprocessing;

import com.cloud.dips.common.security.feign.EnableDipsFeignClients;
import com.cloud.dips.common.swagger.annotation.EnableDipsSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 国策数据预处理模块
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-15
 */
@EnableDipsSwagger2
@SpringCloudApplication
@EnableDipsFeignClients
public class GdsPreprocessingApplication {
	public static void main(String[] args) {
		SpringApplication.run(GdsPreprocessingApplication.class, args);
	}

}
