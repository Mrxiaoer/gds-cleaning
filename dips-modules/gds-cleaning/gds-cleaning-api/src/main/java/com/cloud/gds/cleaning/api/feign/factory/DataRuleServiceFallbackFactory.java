package com.cloud.gds.cleaning.api.feign.factory;

import com.cloud.gds.cleaning.api.feign.DataRuleService;
import com.cloud.gds.cleaning.api.feign.fallback.DataAnalysisServiceFallbackImpl;
import com.cloud.gds.cleaning.api.feign.fallback.DataRuleServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@Component
public class DataRuleServiceFallbackFactory implements FallbackFactory<DataRuleService> {

	@Override
	public DataRuleService create(Throwable throwable) {
		DataRuleServiceFallbackImpl dataRuleServiceFallback = new DataRuleServiceFallbackImpl();
		dataRuleServiceFallback.setCause(throwable);
		return dataRuleServiceFallback;
	}

}
