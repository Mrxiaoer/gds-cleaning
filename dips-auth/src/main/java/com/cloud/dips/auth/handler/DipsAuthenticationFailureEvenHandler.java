package com.cloud.dips.auth.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.cloud.dips.common.security.handler.BaseAuthenticationFailureEvenHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BigPan
 */
@Slf4j
@Component
public class DipsAuthenticationFailureEvenHandler extends BaseAuthenticationFailureEvenHandler {

	/**
	 * 处理登录失败方法
	 * <p>
	 *
	 * @param authenticationException 登录的authentication 对象
	 * @param authentication          登录的authenticationException 对象
	 */
	@Override
	public void handle(AuthenticationException authenticationException, Authentication authentication) {
		log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
	}
}
