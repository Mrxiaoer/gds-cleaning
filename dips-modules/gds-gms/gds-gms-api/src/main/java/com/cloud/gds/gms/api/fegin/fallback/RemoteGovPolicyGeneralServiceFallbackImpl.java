package com.cloud.gds.gms.api.fegin.fallback;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.gms.api.entity.GovPolicyGeneral;
import com.cloud.gds.gms.api.fegin.RemoteGovPolicyGeneralService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-17
 */
@Slf4j
@Component
public class RemoteGovPolicyGeneralServiceFallbackImpl implements RemoteGovPolicyGeneralService {
	@Setter
	private Throwable cause;

	@Override
	public R gainAll(Map<String, Object> params) {
		log.error("feign 获取数据失败", cause);
		return null;
	}

	@Override
	public List<Long> gainList(Map<String, Object> params) {
		System.out.println(cause);
		log.error("feign 获取数据失败", cause);
		return null;
	}

	@Override
	public GovPolicyGeneral info(Long id) {
		log.error("feign 获取数据失败", cause);
		return null;
	}
}
