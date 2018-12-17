package com.cloud.dips.admin.api.feign.fallback;

import org.springframework.stereotype.Component;

import com.cloud.dips.admin.api.entity.SysCity;
import com.cloud.dips.admin.api.feign.RemoteCityService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author RCG
 */
@Slf4j
@Component
public class RemoteCityServiceFallbackImpl implements RemoteCityService {
	@Setter
	private Throwable cause;

	@Override
	public SysCity findByDeptId(Integer id) {
		log.error("feign 查询机构信息失败:{}", cause);
		return null;
	}
}
