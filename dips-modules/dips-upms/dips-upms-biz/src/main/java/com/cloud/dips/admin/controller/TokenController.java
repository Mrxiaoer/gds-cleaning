package com.cloud.dips.admin.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.feign.RemoteTokenService;
import com.cloud.dips.common.core.constant.SecurityConstants;
import com.cloud.dips.common.core.util.R;

import lombok.AllArgsConstructor;

/**
 * @author RCG
 * @date 2018/11/19
 * token 管理
 */
@RestController
@AllArgsConstructor
@RequestMapping("/token")
public class TokenController {
	private final RemoteTokenService remoteTokenService;

	/**
	 * 分页token 信息
	 *
	 * @param params 参数集
	 * @return token集合
	 */
	@GetMapping("/page")
	public Page token(@RequestParam Map<String, Object> params) {
		return remoteTokenService.selectPage(params, SecurityConstants.FROM_IN);
	}

	/**
	 * 删除
	 *
	 * @param id ID
	 * @return success/false
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_token_del')")
	public R<Boolean> delete(@PathVariable String id) {
		return remoteTokenService.deleteTokenById(id, SecurityConstants.FROM_IN);
	}
}
