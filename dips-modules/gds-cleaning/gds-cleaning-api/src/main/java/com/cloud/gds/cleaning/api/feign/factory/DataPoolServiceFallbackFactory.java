package com.cloud.gds.cleaning.api.feign.factory;

import com.cloud.gds.cleaning.api.feign.DataPoolService;
import com.cloud.gds.cleaning.api.feign.fallback.DataPoolServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@Component
public class DataPoolServiceFallbackFactory implements FallbackFactory<DataPoolService> {

	@Override
	public DataPoolService create(Throwable throwable) {
		DataPoolServiceFallbackImpl dataPoolServiceFallback = new DataPoolServiceFallbackImpl();
		dataPoolServiceFallback.setCause(throwable);
		return dataPoolServiceFallback;
	}

}
