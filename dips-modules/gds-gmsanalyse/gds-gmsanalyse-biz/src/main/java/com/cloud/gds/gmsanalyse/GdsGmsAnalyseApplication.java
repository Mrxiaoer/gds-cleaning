package com.cloud.gds.gmsanalyse;

import com.cloud.dips.common.security.feign.EnableDipsFeignClients;
import com.cloud.dips.common.swagger.annotation.EnableDipsSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 数据分析启动类
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-03
 */
@EnableDipsSwagger2
@SpringCloudApplication
@EnableDipsFeignClients
public class GdsGmsAnalyseApplication {
	public static void main(String[] args) {
		SpringApplication.run(GdsGmsAnalyseApplication.class, args);
	}

}
