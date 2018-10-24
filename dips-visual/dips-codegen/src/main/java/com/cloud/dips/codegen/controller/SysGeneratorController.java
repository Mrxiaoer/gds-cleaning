/*
 *    Copyright (c) 2018-2025, BigPan All rights reserved.
 */

package com.cloud.dips.codegen.controller;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.codegen.entity.GenConfig;
import com.cloud.dips.codegen.service.SysGeneratorService;
import com.cloud.dips.common.core.util.Query;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author BigPan
 * @date 2018-07-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/generator")
public class SysGeneratorController {
	private final SysGeneratorService sysGeneratorService;

	/**
	 * 列表
	 *
	 * @param params 参数集
	 * @return 数据库表
	 */
	@GetMapping("/page")
	public Page list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		query.setRecords(sysGeneratorService.queryPage(query));
		return query;
	}

	/**
	 * 生成代码
	 */
	@PostMapping("/code")
	public void code(@RequestBody GenConfig genConfig, HttpServletResponse response) throws IOException {
		byte[] data = sysGeneratorService.generatorCode(genConfig);

		response.reset();
		response.setHeader("Content-Disposition", String.format("attachment; filename=%s.zip", genConfig.getTableName()));
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");

		IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
	}
}
