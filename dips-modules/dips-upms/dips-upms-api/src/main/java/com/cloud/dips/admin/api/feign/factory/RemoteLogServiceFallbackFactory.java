package com.cloud.dips.admin.api.feign.factory;

import org.springframework.stereotype.Component;

import com.cloud.dips.admin.api.feign.RemoteLogService;
import com.cloud.dips.admin.api.feign.fallback.RemoteLogServiceFallbackImpl;

import feign.hystrix.FallbackFactory;

/**
 * @author RCG
 * @date 2018/11/19
 */
@Component
public class RemoteLogServiceFallbackFactory implements FallbackFactory<RemoteLogService> {

	@Override
	public RemoteLogService create(Throwable throwable) {
		RemoteLogServiceFallbackImpl remoteLogServiceFallback = new RemoteLogServiceFallbackImpl();
		remoteLogServiceFallback.setCause(throwable);
		return remoteLogServiceFallback;
	}
}
