package com.cloud.dips.admin.api.feign.factory;

import org.springframework.stereotype.Component;

import com.cloud.dips.admin.api.feign.RemoteDictService;
import com.cloud.dips.admin.api.feign.fallback.RemoteDictServiceFallbackImpl;

import feign.hystrix.FallbackFactory;

/**
 * @author BigPan
 */
@Component
public class RemoteDictServiceFallbackFactory implements FallbackFactory<RemoteDictService> {

	@Override
	public RemoteDictService create(Throwable throwable) {
		RemoteDictServiceFallbackImpl remoteDictServiceFallback = new RemoteDictServiceFallbackImpl();
		remoteDictServiceFallback.setCause(throwable);
		return remoteDictServiceFallback;
	}
}
