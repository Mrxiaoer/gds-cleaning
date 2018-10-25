package com.cloud.dips.tag.api.feign.factory;

import com.cloud.dips.tag.api.feign.RemoteTagRelationService;
import com.cloud.dips.tag.api.feign.fallback.RemoteTagRelationServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author C.Z.H
 */
@Component
public class RemoteTagRelationServiceFallbackFactory implements FallbackFactory<RemoteTagRelationService> {

	@Override
	public RemoteTagRelationService create(Throwable throwable) {
		RemoteTagRelationServiceFallbackImpl remoteTagRelationServiceFallback = new RemoteTagRelationServiceFallbackImpl();
		remoteTagRelationServiceFallback.setCause(throwable);
		return remoteTagRelationServiceFallback;
	}
}
