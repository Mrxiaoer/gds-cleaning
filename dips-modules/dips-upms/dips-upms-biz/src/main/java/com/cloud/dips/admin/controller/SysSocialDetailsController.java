package com.cloud.dips.admin.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.dto.UserInfo;
import com.cloud.dips.admin.api.entity.SysSocialDetails;
import com.cloud.dips.admin.service.SysSocialDetailsService;
import com.cloud.dips.admin.service.SysUserService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;

import lombok.AllArgsConstructor;


/**
 * 系统社交登录账号表
 *
 * @author RCG
 * @date 2018-11-19
 */
@RestController
@RequestMapping("/social")
@AllArgsConstructor
public class SysSocialDetailsController {
	private final SysUserService sysUserService;
	private final SysSocialDetailsService sysSocialDetailsService;


	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	public Page page(@RequestParam Map<String, Object> params) {
		return sysSocialDetailsService.selectPage(new Query<>(params), new EntityWrapper<>());
	}


	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Integer id) {
		SysSocialDetails sysSocialDetails = sysSocialDetailsService.selectById(id);
		return new R<>(sysSocialDetails);
	}

	/**
	 * 保存
	 *
	 * @param sysSocialDetails
	 * @return R
	 */
	@PostMapping("/create")
	public R save(@Valid @RequestBody SysSocialDetails sysSocialDetails) {
		sysSocialDetailsService.insert(sysSocialDetails);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 修改
	 *
	 * @param sysSocialDetails
	 * @return R
	 */
	@PostMapping("/update")
	public R update(@Valid @RequestBody SysSocialDetails sysSocialDetails) {
		sysSocialDetailsService.updateById(sysSocialDetails);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return R
	 */
	@PostMapping("/delete/{id}")
	public R delete(@PathVariable Integer id) {
		return new R<>(sysSocialDetailsService.deleteById(id));
	}

	/**
	 * 通过社交账号查询用户、角色信息
	 *
	 * @param inStr appid@code
	 * @return
	 */
	@GetMapping("/info/{inStr}")
	public R<UserInfo> social(@PathVariable String inStr) {
		Map<String, String> result = sysSocialDetailsService.findOpenId(inStr);
		return new R<>(sysUserService.findUserInfo(result.get("type"), result.get("openId")));
	}

	/**
	 * 绑定社交账号
	 *
	 * @param state 类型
	 * @param code  code
	 * @return
	 */
	@PostMapping("/bind")
	public R<Boolean> bind(String state, String code) {
		return new R<>(sysSocialDetailsService.bindSocial(state, code));
	}
}
