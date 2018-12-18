package com.cloud.gds.cleaning.api.feign.fallback;

import com.cloud.gds.cleaning.api.feign.DataFieldValueService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author : lolilijve
 * @Email : 1042703214@qq.com
 * @Date : 2018-11-27
 */
@Slf4j
@Component
public class DataFieldValueServiceFallbackImpl implements DataFieldValueService {

	@Setter
	private Throwable cause;

}
