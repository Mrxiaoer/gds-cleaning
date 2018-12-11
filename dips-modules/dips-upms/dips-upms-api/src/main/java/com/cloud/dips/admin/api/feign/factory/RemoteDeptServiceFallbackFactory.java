package com.cloud.dips.admin.api.feign.factory;

import com.cloud.dips.admin.api.feign.RemoteDeptService;
import com.cloud.dips.admin.api.feign.fallback.RemoteDeptServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author BigPan
 */
@Component
public class RemoteDeptServiceFallbackFactory implements FallbackFactory<RemoteDeptService> {

	@Override
	public RemoteDeptService create(Throwable throwable) {
		RemoteDeptServiceFallbackImpl remoteDeptServiceFallback = new RemoteDeptServiceFallbackImpl();
		remoteDeptServiceFallback.setCause(throwable);
		return remoteDeptServiceFallback;
	}
}
