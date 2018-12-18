package com.cloud.gds.cleaning.api.feign.factory;

import com.cloud.gds.cleaning.api.feign.DataFieldService;
import com.cloud.gds.cleaning.api.feign.fallback.DataFieldServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * fallbackFactory
 *
 * @Author : lolilijve
 * @Email : 1042703214@qq.com
 * @Date : 2018-11-21
 */
@Component
public class DataFieldServiceFallbackFactory implements FallbackFactory<DataFieldService> {

	@Override
	public DataFieldService create(Throwable throwable) {
		DataFieldServiceFallbackImpl dataFieldServiceFallback = new DataFieldServiceFallbackImpl();
		dataFieldServiceFallback.setCause(throwable);
		return dataFieldServiceFallback;
	}

}
