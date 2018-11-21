package com.cloud.dips.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.cloud.dips.admin.api.dto.RoleDTO;
import com.cloud.dips.admin.api.entity.SysRole;
import com.cloud.dips.admin.service.SysRoleMenuService;
import com.cloud.dips.admin.service.SysRoleService;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;

/**
 * @author RCG
 * @date 2018/11/19
 */
@RestController
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	/**
	 * 通过ID查询角色信息
	 *
	 * @param id ID
	 * @return 角色信息
	 */
	@GetMapping("/{id}")
	public SysRole role(@PathVariable Integer id) {
		return sysRoleService.selectById(id);
	}

	/**
	 * 添加角色
	 *
	 * @param roleDto 角色信息
	 * @return success、false
	 */
	@SysLog("添加角色")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_role_add')")
	public R<Boolean> role(@RequestBody RoleDTO roleDto) {
		return new R<>(sysRoleService.insertRole(roleDto));
	}

	/**
	 * 修改角色
	 *
	 * @param roleDto 角色信息
	 * @return success/false
	 */
	@SysLog("修改角色")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_role_edit')")
	public R<Boolean> roleUpdate(@RequestBody RoleDTO roleDto) {
		return new R<>(sysRoleService.updateRoleById(roleDto));
	}

	/**
	 * 删除角色
	 *
	 * @param id 
	 * @return success/false
	 */
	@SysLog("删除角色")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_role_del')")
	public R<Boolean> roleDel(@PathVariable Integer id) {
		SysRole sysRole = sysRoleService.selectById(id);
		sysRole.setIsDeleted(CommonConstant.STATUS_DEL);
		return new R<>(sysRoleService.updateById(sysRole));
	}

	/**
	 * 获取角色列表
	 *
	 * @param deptId 部门ID
	 * @return 角色列表
	 */
	@GetMapping("/roleList/{deptId}")
	public List<SysRole> roleList(@PathVariable Integer deptId) {
		return sysRoleService.selectListByDeptId(deptId);

	}

	/**
	 * 分页查询角色信息
	 *
	 * @param params 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/rolePage")
	public Page rolePage(@RequestParam Map<String, Object> params) {
		return sysRoleService.selectwithDeptPage(new Query<>(params), new EntityWrapper<>());
	}

	/**
	 * 更新角色菜单
	 *
	 * @param roleId  角色ID
	 * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return success、false
	 */
	@SysLog("更新角色菜单")
	@PutMapping("/roleMenuUpd")
	@PreAuthorize("@pms.hasPermission('sys_role_perm')")
	public R<Boolean> roleMenuUpd(Integer roleId, @RequestParam(value = "menuIds", required = false) String menuIds) {
		SysRole sysRole = sysRoleService.selectById(roleId);
		return new R<>(sysRoleMenuService.insertRoleMenus(sysRole.getRoleCode(), roleId, menuIds));
	}
}
