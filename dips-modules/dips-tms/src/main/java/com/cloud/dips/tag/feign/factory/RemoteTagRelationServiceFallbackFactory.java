package com.cloud.dips.tag.feign.factory;

import org.springframework.stereotype.Component;

import com.cloud.dips.tag.feign.RemoteTagRelationService;
import com.cloud.dips.tag.feign.fallback.RemoteTagRelationServiceFallbackImpl;

import feign.hystrix.FallbackFactory;

@Component
public class RemoteTagRelationServiceFallbackFactory implements FallbackFactory<RemoteTagRelationService> {

	@Override
	public RemoteTagRelationService create(Throwable throwable) {
		RemoteTagRelationServiceFallbackImpl remoteTagRelationServiceFallback = new RemoteTagRelationServiceFallbackImpl();
		remoteTagRelationServiceFallback.setCause(throwable);
		return remoteTagRelationServiceFallback;
	}
}
