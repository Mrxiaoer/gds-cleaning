package com.cloud.gds.cleaning.api.feign.factory;

import com.cloud.gds.cleaning.api.feign.CleanPoolService;
import com.cloud.gds.cleaning.api.feign.fallback.CleanPoolServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@Component
public class CleanPoolServiceFallbackFactory implements FallbackFactory<CleanPoolService> {

	@Override
	public CleanPoolService create(Throwable throwable) {
		CleanPoolServiceFallbackImpl cleanPoolServiceFallback = new CleanPoolServiceFallbackImpl();
		cleanPoolServiceFallback.setCause(throwable);
		return cleanPoolServiceFallback;
	}

}
