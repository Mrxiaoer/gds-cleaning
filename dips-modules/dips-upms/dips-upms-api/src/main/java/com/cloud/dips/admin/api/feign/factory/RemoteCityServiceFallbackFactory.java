package com.cloud.dips.admin.api.feign.factory;

import org.springframework.stereotype.Component;

import com.cloud.dips.admin.api.feign.RemoteCityService;
import com.cloud.dips.admin.api.feign.fallback.RemoteCityServiceFallbackImpl;

import feign.hystrix.FallbackFactory;

/**
 * @author RCG
 */
@Component
public class RemoteCityServiceFallbackFactory implements FallbackFactory<RemoteCityService> {

	@Override
	public RemoteCityService create(Throwable throwable) {
		RemoteCityServiceFallbackImpl remoteCityServiceFallback = new RemoteCityServiceFallbackImpl();
		remoteCityServiceFallback.setCause(throwable);
		return remoteCityServiceFallback;
	}
}
