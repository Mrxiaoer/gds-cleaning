package com.cloud.gds.cleaning.api.feign.fallback;

import com.cloud.gds.cleaning.api.feign.DataAnalysisService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@Slf4j
@Component
public class DataAnalysisServiceFallbackImpl implements DataAnalysisService {

	@Setter
	private Throwable cause;

}
