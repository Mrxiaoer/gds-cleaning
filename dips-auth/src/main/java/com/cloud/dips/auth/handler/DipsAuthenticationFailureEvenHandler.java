package com.cloud.dips.auth.handler;

import com.cloud.dips.common.security.handler.AuthenticationFailureEvenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author BigPan
 */
@Slf4j
@Component
public class DipsAuthenticationFailureEvenHandler extends AuthenticationFailureEvenHandler {

	/**
	 * 处理登录成功方法
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
