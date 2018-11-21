package com.cloud.dips.auth.handler;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cloud.dips.common.security.handler.BaseAuthenticationSuccessEventHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BigPan
 */
@Slf4j
@Component
public class DipsAuthenticationSuccessEventHandler extends BaseAuthenticationSuccessEventHandler {

	/**
	 * 处理登录成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 *
	 * @param authentication 登录对象
	 */
	@Override
	public void handle(Authentication authentication) {
		log.info("用户：{} 登录成功", authentication.getPrincipal());
	}
}
