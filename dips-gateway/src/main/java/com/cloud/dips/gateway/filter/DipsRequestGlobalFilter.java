package com.cloud.dips.gateway.filter;

import com.cloud.dips.common.core.constant.SecurityConstants;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * @author BigPan
 * <p>
 * 全局拦截器，作用所有的微服务
 * <p>
 * 1. 对请求头中参数进行处理 from 参数进行清洗
 * 2. 重写StripPrefix = 1,支持全局
 * 3. 支持swagger添加X-Forwarded-Prefix header  （F SR2 已经支持，不需要自己维护）
 */
@Component
public class DipsRequestGlobalFilter implements GlobalFilter, Ordered {
	private static final String HEADER_NAME = "X-Forwarded-Prefix";
//	private static final String MAX_AGE = "18000L";

	/**
	 * Process the Web request and (optionally) delegate to the next
	 * {@code WebFilter} through the given {@link GatewayFilterChain}.
	 *
	 * @param exchange the current server exchange
	 * @param chain    provides a way to delegate to the next filter
	 * @return {@code Mono<Void>} to indicate when request processing is complete
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 1. 清洗请求头中from 参数
		ServerHttpRequest request = exchange.getRequest().mutate()
			.headers(httpHeaders -> httpHeaders.remove(SecurityConstants.FROM))
			.build();

		// 2. 重写StripPrefix
		addOriginalRequestUrl(exchange, request.getURI());
		String rawPath = request.getURI().getRawPath();
		String newPath = "/" + Arrays.stream(StringUtils.tokenizeToStringArray(rawPath, "/"))
			.skip(1L).collect(Collectors.joining("/"));
		ServerHttpRequest newRequest = request.mutate()
			.path(newPath)
			.build();
		exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());

//		ServerHttpResponse response = exchange.getResponse();
//		HttpHeaders headers = response.getHeaders();
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, PUT, OPTIONS, DELETE, PATCH");
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
//		headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
//		headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
//		headers.setAccessControlAllowCredentials(true);
//		headers.setContentType(MediaType.TEXT_HTML);
//		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//		if (request.getMethod() == HttpMethod.OPTIONS) {
//			response.setStatusCode(HttpStatus.NO_CONTENT);
//			return Mono.empty();
//		}
		return chain.filter(exchange.mutate()
				.request(newRequest.mutate()
					.build()).build());
	}

	@Override
	public int getOrder() {
		return -1000;
	}
}
