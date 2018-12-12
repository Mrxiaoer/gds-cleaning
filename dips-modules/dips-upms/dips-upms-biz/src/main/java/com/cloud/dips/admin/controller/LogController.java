package com.cloud.dips.admin.controller;


import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.entity.SysLog;
import com.cloud.dips.admin.api.vo.PreLogVo;
import com.cloud.dips.admin.service.SysLogService;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;

import lombok.AllArgsConstructor;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@RestController
@RequestMapping("/log")
@AllArgsConstructor
public class LogController {
	private final SysLogService sysLogService;

	/**
	 * 分页查询日志信息
	 *
	 * @param params 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/logPage")
	public Page logPage(@RequestParam Map<String, Object> params) {
		return sysLogService.selectPage(new Query<>(params), new EntityWrapper<>());
	}

	/**
	 * 根据ID
	 *
	 * @param id ID
	 * @return success/false
	 */
	@PostMapping("/delete/{id}")
	@PreAuthorize("@pms.hasPermission('sys_log_del')")
	public R<Boolean> delete(@PathVariable Long id) {
		return new R<>(sysLogService.updateByLogId(id));
	}

	/**
	 * 插入日志
	 *
	 * @param sysLog 日志实体
	 * @return success/false
	 */
	@PostMapping("/create")
	public R<Boolean> save(@Valid @RequestBody SysLog sysLog) {
		sysLog.applyDefaultValue();
		return new R<>(sysLogService.insert(sysLog));
	}

	/**
	 * 插入前端异常日志
	 *
	 * @param preLogVoList 日志实体
	 * @return success/false
	 */
	@PostMapping("/logs")
	public R<Boolean> saveLogs(@RequestBody List<PreLogVo> preLogVoList) {
		return new R<>(sysLogService.insertLogs(preLogVoList));
	}
}
