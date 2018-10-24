package com.cloud.dips.auth.endpoint;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.constant.SecurityConstants;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.security.service.DipsUser;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author BigPan
 * 删除token端点
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class DipsTokenEndpoint {
	private static final String DIPS_OAUTH_ACCESS = SecurityConstants.DIPS_PREFIX + SecurityConstants.OAUTH_PREFIX + "access:";
	private final TokenStore tokenStore;
	private final RedisTemplate redisTemplate;

	/**
	 * 退出token
	 *
	 * @param authHeader Authorization
	 */
	@GetMapping("/removeToken")
	public R<Boolean> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
		if (StringUtils.hasText(authHeader)) {
			String tokenValue = authHeader.replace("Bearer", "").trim();
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
			if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
				return new R<>(false, "退出失败，token 为空");
			}
			tokenStore.removeAccessToken(accessToken);
		}

		return new R<>(Boolean.TRUE);
	}

	/**
	 * 令牌管理调用
	 *
	 * @param token token
	 * @param from  内部调用标志
	 * @return
	 */
	@DeleteMapping("/delToken/{token}")
	public R<Boolean> delToken(@PathVariable("token") String token, @RequestHeader(required = false) String from) {
		if (StrUtil.isBlank(from)) {
			return null;
		}
		return new R<>(redisTemplate.delete(DIPS_OAUTH_ACCESS + token));
	}


	/**
	 * 查询token
	 *
	 * @param params 分页参数
	 * @param from   标志
	 * @return
	 */
	@PostMapping("/listToken")
	public Page tokenList(@RequestBody Map<String, Object> params, @RequestHeader(required = false) String from) {
		if (StrUtil.isBlank(from)) {
			return null;
		}

		List<Map<String, String>> list = new ArrayList<>();
		//根据分页参数获取对应数据
		List<String> pages = findKeysForPage(DIPS_OAUTH_ACCESS + "*", MapUtil.getInt(params, "page"), MapUtil.getInt(params, "limit"));

		for (String page : pages) {
			String accessToken = StrUtil.subAfter(page, DIPS_OAUTH_ACCESS, true);
			OAuth2AccessToken token = tokenStore.readAccessToken(accessToken);
			Map<String, String> map = new HashMap<>(8);


			map.put("token_type", token.getTokenType());
			map.put("token_value", token.getValue());
			map.put("expires_in", token.getExpiresIn() + "");


			OAuth2Authentication oAuth2Auth = tokenStore.readAuthentication(token);
			Authentication authentication = oAuth2Auth.getUserAuthentication();

			map.put("client_id", oAuth2Auth.getOAuth2Request().getClientId());
			map.put("grant_type", oAuth2Auth.getOAuth2Request().getGrantType());

			if (authentication instanceof UsernamePasswordAuthenticationToken) {
				UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

				if (authenticationToken.getPrincipal() instanceof DipsUser) {
					DipsUser user = (DipsUser) authenticationToken.getPrincipal();
					map.put("user_id", user.getId() + "");
					map.put("user_name", user.getUsername() + "");
				}
			} else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
				//刷新token方式
				PreAuthenticatedAuthenticationToken authenticationToken = (PreAuthenticatedAuthenticationToken) authentication;
				if (authenticationToken.getPrincipal() instanceof DipsUser) {
					DipsUser user = (DipsUser) authenticationToken.getPrincipal();
					map.put("user_id", user.getId() + "");
					map.put("user_name", user.getUsername() + "");
				}
			}
			list.add(map);
		}

		Page result = new Page(MapUtil.getInt(params, "page"), MapUtil.getInt(params, "limit"));
		result.setRecords(list);
		result.setTotal(redisTemplate.keys(DIPS_OAUTH_ACCESS + "*").size());
		return result;
	}

	private List<String> findKeysForPage(String patternKey, int pageNum, int pageSize) {
		ScanOptions options = ScanOptions.scanOptions().match(patternKey).build();
		RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
		Cursor cursor = (Cursor) redisTemplate.executeWithStickyConnection(redisConnection -> {
			return new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize);
		});
		List<String> result = new ArrayList<>();
		int tmpIndex = 0;
		int startIndex = (pageNum - 1) * pageSize;
		int end = pageNum * pageSize;

		assert cursor != null;
		while (cursor.hasNext()) {
			if (tmpIndex >= startIndex && tmpIndex < end) {
				result.add(cursor.next().toString());
				tmpIndex++;
				continue;
			}
			if (tmpIndex >= end) {
				break;
			}
			tmpIndex++;
			cursor.next();
		}
		return result;
	}
}
