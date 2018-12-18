package com.cloud.gds.cleaning.api.feign;

import com.cloud.gds.cleaning.api.feign.factory.DataFieldValueServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author : lolilijve
 * @Email : 1042703214@qq.com
 */
@FeignClient(value = "dna-dataclean", fallbackFactory = DataFieldValueServiceFallbackFactory.class)
public interface DataFieldValueService {}
