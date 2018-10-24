/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.admin.api.dto.RelationTypeDTO;
import com.cloud.dips.admin.api.entity.SysRelationType;
import com.cloud.dips.admin.service.SysRelationTypeService;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 * 部门关联类型管理 前端控制器
 *
 */
@RestController
@RequestMapping("/relation/type")
public class RelationTypeController {
	@Autowired
	private SysRelationTypeService service;

	/**
	 * 通过ID查询
	 *
	 * @param id ID
	 * @return SysRelationType
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "查询关联类型", notes = "根据ID查询关联类型: params{ID: id}", httpMethod = "GET")
	public SysRelationType get(@PathVariable Integer id) {
		return service.selectById(id);
	}

	/**
	 * 分页查询关联分类
	 *
	 * @param params 参数集
	 *
	 * @return 通知公告集合
	 */
	@RequestMapping("/page")
	@ApiOperation(value = "分页查询关联类型", notes = "关联类型分页集合", httpMethod = "GET")
	public Page<SysRelationType> page(@RequestParam Map<String, Object> params) {
		return service.selectAllPage(new Query<>(params));
	}


	/**
	 * 添加
	 *
	 * @param typeDTO 实体
	 * @return success/false
	 */
	@SysLog("添加关联类型")
	@PostMapping
	//@PreAuthorize("@pms.hasPermission('sys_dept_relevance_type_add')")
	@ApiOperation(value = "添加关联类型", notes = "添加关联类型", httpMethod = "POST")
	public R<Boolean> add(@RequestBody RelationTypeDTO typeDTO) {
		return new R<>(service.insertType(typeDTO));
	}

	/**
	 * 删除
	 *
	 * @param id ID
	 * @return success/false
	 */
	@SysLog("删除关联类型")
	@DeleteMapping("/{id}")
	//@PreAuthorize("@pms.hasPermission('sys_dept_relevance_type_del')")
	@ApiOperation(value = "删除关联类型", notes = "根据ID删除关联类型: params{类型ID: id}", httpMethod = "DELETE")
	public R<Boolean> delete(@PathVariable Integer id) {
		return new R<>(service.deleteById(id));
	}

	/**
	 * 更新
	 *
	 * @param typeDTO 实体
	 * @return success/false
	 */
	@SysLog("更新关联类型")
	@PutMapping
	//@PreAuthorize("@pms.hasPermission('sys_dept_relevance_type_edit')")
	@ApiOperation(value = "更新关联类型", notes = "更新关联类型", httpMethod = "PUT")
	public Boolean edit(@RequestBody RelationTypeDTO typeDTO) {
		return service.updateById(typeDTO);
	}
}
