package com.cloud.gds.gms.api.fegin.factory;

import com.cloud.gds.gms.api.fegin.RemoteGovPolicyGeneralService;
import com.cloud.gds.gms.api.fegin.fallback.RemoteGovPolicyGeneralServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-17
 */
@Component
public class GovPolicyGeneralServiceFallbackFactory implements FallbackFactory<RemoteGovPolicyGeneralService> {

	@Override
	public RemoteGovPolicyGeneralService create(Throwable throwable) {
		RemoteGovPolicyGeneralServiceFallbackImpl govPolicyGeneralServiceFallback = new RemoteGovPolicyGeneralServiceFallbackImpl();
		govPolicyGeneralServiceFallback.setCause(throwable);
		return govPolicyGeneralServiceFallback;
	}
}
