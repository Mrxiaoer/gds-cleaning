/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.dto.DeptDTO;
import com.cloud.dips.admin.api.dto.DeptTree;
import com.cloud.dips.admin.api.entity.SysDept;
import com.cloud.dips.admin.api.vo.DeptVO;
import com.cloud.dips.admin.service.SysDeptService;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author BigPan
 * @since 2018-01-20
 */
@RestController
@RequestMapping("/dept")
@AllArgsConstructor
public class DeptController {
	private final SysDeptService sysDeptService;

	/**
	 * 通过ID查询
	 *
	 * @param id ID
	 * @return SysDept
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "查询部门详细", notes = "根据ID查询部门详细: params{部门ID: id}", httpMethod = "GET")
	public DeptVO get(@PathVariable Integer id) {
		return sysDeptService.selectDeptVoById(id);
	}

	/**
	 * 分页查询部门
	 *
	 * @param params 参数集
	 *
	 * @return 通知公告集合
	 */
	@RequestMapping("/deptPage")
	@ApiOperation(value = "分页查询部门列表", notes = "部门列表", httpMethod = "GET")
	public Page<DeptVO> deptPage(@RequestParam Map<String, Object> params) {
		return sysDeptService.selectAllPage(new Query<>(params));
	}

	/**
	 * 返回树形菜单集合
	 *
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	@ApiOperation(value = "查询部门树形菜单", notes = "部门树形菜单", httpMethod = "GET")
	public List<DeptTree> getTree() {
		SysDept condition = new SysDept();
		condition.setStatus(CommonConstant.STATUS_NORMAL);
		return sysDeptService.selectListTree(new EntityWrapper<>(condition));
	}

	/**
	 * 添加
	 *
	 * @param deptDTO 实体
	 * @return success/false
	 */
	@SysLog("添加部门")
	@PostMapping
	//@PreAuthorize("@pms.hasPermission('sys_dept_add')")
	@ApiOperation(value = "添加部门", notes = "添加部门", httpMethod = "POST")
	public R<Boolean> add(@RequestBody DeptDTO deptDTO) {
		return new R<>(sysDeptService.insertDept(deptDTO));
	}

	/**
	 * 删除
	 *
	 * @param id ID
	 * @return success/false
	 */
	@SysLog("删除部门")
	@DeleteMapping("/{id}")
	//@PreAuthorize("@pms.hasPermission('sys_dept_del')")
	@ApiOperation(value = "删除部门", notes = "根据ID删除部门: params{类型ID: id}", httpMethod = "DELETE")
	public R<Boolean> delete(@PathVariable Integer id) {
		return new R<>(sysDeptService.deleteDeptById(id));
	}

	/**
	 * 编辑
	 *
	 * @param deptDTO 实体
	 * @return success/false
	 */
	@SysLog("更新部门")
	@PutMapping
	//@PreAuthorize("@pms.hasPermission('sys_dept_edit')")
	@ApiOperation(value = "更新部门", notes = "更新部门", httpMethod = "PUT")
	public Boolean edit(@RequestBody DeptDTO deptDTO) {
		deptDTO.setUpdateTime(new Date());
		return sysDeptService.updateDeptById(deptDTO);
	}

	/**
	 * 彻底删除
	 *
	 * @param deptDTO ID
	 * @return success/false
	 */
	@SysLog("彻底删除部门")
	@PostMapping("/thoroughlyDelete")
	//@PreAuthorize("@pms.hasPermission('sys_dept_del')")
	@ApiOperation(value = "彻底删除部门", notes = "根据ID彻底删除部门: params{类型ID: id}", httpMethod = "POST")
	public R<Boolean> thoroughlyDelete(@RequestBody DeptDTO deptDTO) {
		return new R<>(sysDeptService.thoroughlyDeleteDeptById(deptDTO.getDeptId()));
	}
}
