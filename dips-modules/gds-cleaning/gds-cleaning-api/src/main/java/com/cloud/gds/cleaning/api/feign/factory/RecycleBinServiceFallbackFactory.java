package com.cloud.gds.cleaning.api.feign.factory;

import com.cloud.gds.cleaning.api.feign.RecycleBinService;
import com.cloud.gds.cleaning.api.feign.fallback.RecycleBinServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-11
 */
@Component
public class RecycleBinServiceFallbackFactory implements FallbackFactory<RecycleBinService> {

	@Override
	public RecycleBinService create(Throwable throwable) {

		RecycleBinServiceFallbackImpl recycleBinServiceFallback = new RecycleBinServiceFallbackImpl();
		recycleBinServiceFallback.setCause(throwable);
		return recycleBinServiceFallback;
	}
}
