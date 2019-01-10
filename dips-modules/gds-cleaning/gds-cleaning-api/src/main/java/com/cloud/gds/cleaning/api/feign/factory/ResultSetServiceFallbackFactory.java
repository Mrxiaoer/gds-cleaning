package com.cloud.gds.cleaning.api.feign.factory;

import com.cloud.gds.cleaning.api.feign.ResultSetService;
import com.cloud.gds.cleaning.api.feign.fallback.ResultSetServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@Component
public class ResultSetServiceFallbackFactory implements FallbackFactory<ResultSetService> {

	@Override
	public ResultSetService create(Throwable throwable) {
		ResultSetServiceFallbackImpl resultSetServiceFallback = new ResultSetServiceFallbackImpl();
		resultSetServiceFallback.setCause(throwable);
		return resultSetServiceFallback;
	}
}
