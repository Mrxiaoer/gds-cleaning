package com.cloud.dips.admin.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.dips.admin.api.dto.DeptTree;
import com.cloud.dips.admin.api.entity.SysDept;
import com.cloud.dips.admin.api.vo.DeptVO;
import com.cloud.dips.admin.service.SysDeptService;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.R;

import lombok.AllArgsConstructor;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
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
	public DeptVO get(@PathVariable Integer id) {
		return sysDeptService.selectDeptVoById(id);
	}


	/**
	 * 返回树形菜单集合
	 *
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	public List<DeptTree> getTree() {
		SysDept condition = new SysDept();
		condition.setIsDeleted(CommonConstant.STATUS_NORMAL);
		return sysDeptService.selectListTree(new EntityWrapper<>(condition));
	}

	/**
	 * 添加
	 *
	 * @param sysDept 实体
	 * @return success/false
	 */
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_dept_add')")
	public R<Boolean> add(@Valid @RequestBody SysDept sysDept) {
		return new R<>(sysDeptService.insertDept(sysDept));
	}

	/**
	 * 删除
	 *
	 * @param id ID
	 * @return success/false
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_dept_del')")
	public R<Boolean> delete(@PathVariable Integer id) {
		return new R<>(sysDeptService.deleteDeptById(id));
	}

	/**
	 * 编辑
	 *
	 * @param sysDept 实体
	 * @return success/false
	 */
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_dept_edit')")
	public Boolean edit(@Valid @RequestBody SysDept sysDept) {
		sysDept.setModifiedTime(LocalDateTime.now());
		return sysDeptService.updateDeptById(sysDept);
	}
	
	/**
	 * 通过部门名称查询
	 *
	 * @param id ID
	 * @return deptId
	 */
	@GetMapping("/find/{name}")
	public Integer findDeptIdByName(@PathVariable("name") String name) {
		return sysDeptService.findDeptIdByName(name);
	}
}
