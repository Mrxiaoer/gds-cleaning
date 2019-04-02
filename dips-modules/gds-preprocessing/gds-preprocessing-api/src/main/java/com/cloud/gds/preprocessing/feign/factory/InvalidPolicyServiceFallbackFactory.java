package com.cloud.gds.preprocessing.feign.factory;

import com.cloud.gds.preprocessing.feign.InvalidPolicyService;
import com.cloud.gds.preprocessing.feign.fallback.InvalidPolicyServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 政策预清洗
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-29
 */
@Component
public class InvalidPolicyServiceFallbackFactory implements FallbackFactory<InvalidPolicyService> {

	@Override
	public InvalidPolicyService create(Throwable throwable) {
		InvalidPolicyServiceFallbackImpl invalidPolicyServiceFallback = new InvalidPolicyServiceFallbackImpl();
		invalidPolicyServiceFallback.setCause(throwable);
		return invalidPolicyServiceFallback;
	}

}
