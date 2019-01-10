package com.cloud.gds.cleaning.api.feign.factory;

import com.cloud.gds.cleaning.api.feign.CleanPoolService;
import com.cloud.gds.cleaning.api.feign.DataAnalysisService;
import com.cloud.gds.cleaning.api.feign.fallback.CleanPoolServiceFallbackImpl;
import com.cloud.gds.cleaning.api.feign.fallback.DataAnalysisServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@Component
public class DataAnalysisServiceFallbackFactory implements FallbackFactory<DataAnalysisService> {

	@Override
	public DataAnalysisService create(Throwable throwable) {
		DataAnalysisServiceFallbackImpl dataAnalysisServiceFallback = new DataAnalysisServiceFallbackImpl();
		dataAnalysisServiceFallback.setCause(throwable);
		return dataAnalysisServiceFallback;
	}

}
