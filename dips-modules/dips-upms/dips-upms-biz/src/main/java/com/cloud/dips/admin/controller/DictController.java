/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.entity.SysDict;
import com.cloud.dips.admin.service.SysDictService;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.constant.SecurityConstants;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.security.util.SecurityUtils;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author Wilson
 * @since 2017-11-19
 */
@RestController
@RequestMapping("/dict")
public class DictController {
	@Autowired
	private SysDictService sysDictService;

	/**
	 * 通过ID查询字典信息
	 *
	 * @param id ID
	 * @return 字典信息
	 */
	@GetMapping("/{id}")
	public SysDict dict(@PathVariable Integer id) {
		return sysDictService.selectById(id);
	}

	/**
	 * 分页查询字典信息
	 *
	 * @param params 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/dictPage")
	public Page dictPage(@RequestParam Map<String, Object> params) {
		params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
		String label = "%";// "%":防止like值为空or() and() SQL语句报错
		String description = "%";
		String type = "%";
		if (StrUtil.isNotBlank((String) params.get("label"))) {
			label = (String) params.get("label");
			params.remove("label");
		}
		if (StrUtil.isNotBlank((String) params.get("description"))) {
			description = (String) params.get("description");
			params.remove("description");
		}
		if (StrUtil.isNotBlank((String) params.get("type"))) {
			type = (String) params.get("type");
			params.remove("type");
		}
		return sysDictService.selectPage(new Query<>(params),
			new EntityWrapper<SysDict>().and().like("g_type", type).and().like("g_label", label).and().like("g_description", description).orderBy("g_type,g_sort"));
	}

	/**
	 * 通过字典类型查找字典
	 *
	 * @param type 类型
	 * @return 同类型字典
	 */
	@GetMapping("/type/{type}")
	@Cacheable(value = "dict_details", key = "#type")
	public List<SysDict> findDictByType(@PathVariable String type) {
		SysDict condition = new SysDict();
		condition.setDelFlag(CommonConstant.STATUS_NORMAL);
		condition.setType(type);
		return sysDictService.selectList(new EntityWrapper<>(condition));
	}

	/**
	 * 添加字典
	 *
	 * @param sysDict 字典信息
	 * @return success、false
	 */
	@SysLog("添加字典")
	@PostMapping
	@CacheEvict(value = "dict_details", key = "#sysDict.type")
	@PreAuthorize("@pms.hasPermission('sys_dict_add')")
	public R<Boolean> dict(@RequestBody SysDict sysDict) {
		return new R<>(sysDictService.insert(sysDict));
	}

	/**
	 * 删除字典，并且清除字典缓存
	 *
	 * @param id   ID
	 * @param type 类型
	 * @return R
	 */
	@SysLog("删除字典")
	@DeleteMapping("/{id}/{type}")
	@CacheEvict(value = "dict_details", key = "#type")
	@PreAuthorize("@pms.hasPermission('sys_dict_del')")
	public R<Boolean> deleteDict(@PathVariable Integer id, @PathVariable String type) {
		return new R<>(sysDictService.deleteById(id));
	}

	/**
	 * 修改字典
	 *
	 * @param sysDict 字典信息
	 * @return success/false
	 */
	@PutMapping
	@SysLog("修改字典")
	@CacheEvict(value = "dict_details", key = "#sysDict.type")
	@PreAuthorize("@pms.hasPermission('sys_dict_edit')")
	public R<Boolean> editDict(@RequestBody SysDict sysDict) {
		return new R<>(sysDictService.updateById(sysDict));
	}

	/**
	 * 通过字典类型查找字典
	 *
	 * @param from 请求标志，该接口会被 前端调用
	 * @param type 类型
	 * @return 同类型字典
	 */
	@GetMapping(value = {"/remoteFindDictByType", "/remoteFindDictByType/{type}"})
	public List<SysDict> remoteFindDictByType(@PathVariable(required = false) String type,
											  @RequestHeader(required = false) String from) {

		// 查询用户不为空时判断是不是内部请求
		String username = SecurityUtils.getUser().getUsername();
		if (StrUtil.isNotBlank(username) && !StrUtil.equals(SecurityConstants.FROM_IN, from)) {
			return null;
		}
		SysDict condition = new SysDict();
		condition.setDelFlag(CommonConstant.STATUS_NORMAL);
		condition.setType(type);

		return sysDictService.selectList(new EntityWrapper<>(condition));
	}

}
