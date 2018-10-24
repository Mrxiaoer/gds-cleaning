package com.cloud.dips.admin.api.feign.fallback;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.feign.RemoteTokenService;
import com.cloud.dips.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author BigPan
 * feign token  fallback
 */
@Slf4j
@Component
public class RemoteTokenServiceFallbackImpl implements RemoteTokenService {
	@Setter
	private Throwable cause;

	/**
	 * 分页查询token 信息
	 *
	 * @param params 分页参数
	 * @param from   内部调用标志
	 * @return page
	 */
	@Override
	public Page selectPage(Map<String, Object> params, String from) {
		log.error("调用认证中心查询token 失败", cause);
		return null;
	}

	/**
	 * 删除token
	 *
	 *
	 * @param s
	 * @param id
	 * @return
	 */
	@Override
	public R<Boolean> deleteTokenById(String s, String id) {
		log.error("删除token 失败 {}", id, cause);
		return null;
	}
}
