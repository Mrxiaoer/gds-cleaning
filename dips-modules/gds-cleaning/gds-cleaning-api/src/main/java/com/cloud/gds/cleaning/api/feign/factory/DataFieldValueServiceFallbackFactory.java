package com.cloud.gds.cleaning.api.feign.factory;

import com.cloud.gds.cleaning.api.feign.DataFieldValueService;
import com.cloud.gds.cleaning.api.feign.fallback.DataFieldValueServiceFallbackImpl;
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
public class DataFieldValueServiceFallbackFactory implements FallbackFactory<DataFieldValueService> {

	@Override
	public DataFieldValueService create(Throwable throwable) {
		DataFieldValueServiceFallbackImpl dataFieldValueServiceFallback = new DataFieldValueServiceFallbackImpl();
		dataFieldValueServiceFallback.setCause(throwable);
		return dataFieldValueServiceFallback;
	}

}
