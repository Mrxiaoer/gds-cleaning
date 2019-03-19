package com.cloud.gds.cleaning.api.feign;

import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.feign.factory.AttributeMatchServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author : lolilijve
 * @Email : 1042703214@qq.com
 * @Date : 2018-11-22
 */
@FeignClient(value = DataCleanConstant.MODULE_NAME, fallbackFactory = AttributeMatchServiceFallbackFactory.class)
public interface AttributeMatchService {}
