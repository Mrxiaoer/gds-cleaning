package com.cloud.dips.admin.controller;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.cloud.dips.admin.api.dto.MenuTree;
import com.cloud.dips.admin.api.entity.SysMenu;
import com.cloud.dips.admin.api.vo.MenuVO;
import com.cloud.dips.admin.api.vo.TreeUtil;
import com.cloud.dips.admin.service.SysMenuService;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.security.util.SecurityUtils;

/**
 * @author RCG
 * @date 2018/11/18
 */
@RestController
@RequestMapping("/menu" )
public class MenuController {
	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 通过角色名称查询用户菜单
	 *
	 * @param role 角色名称
	 * @return 菜单列表
	 */
	@GetMapping("/findMenuByRole/{role}" )
	public List<MenuVO> findMenuByRole(@PathVariable String role) {
		return sysMenuService.findMenuByRoleCode(role);
	}

	/**
	 * 返回当前用户的树形菜单集合
	 *
	 * @return 当前用户的树形菜单
	 */
	@GetMapping(value = "/userMenu" )
	public List<MenuTree> userMenu() {
		// 获取符合条件得菜单
		Set<MenuVO> all = new HashSet<>();
		SecurityUtils.getRoles().forEach(roleCode -> all.addAll(sysMenuService.findMenuByRoleCode(roleCode)));

		List<MenuTree> menuTreeList = all.stream().filter(vo -> CommonConstant.MENU
			.equals(vo.getType()))
			.map(MenuTree::new)
			.sorted(Comparator.comparingInt(MenuTree::getSort))
			.collect(Collectors.toList());
		return TreeUtil.bulid(menuTreeList, -1);
	}

	/**
	 * 返回树形菜单集合
	 *
	 * @return 树形菜单
	 */
	@GetMapping(value = "/allTree" )
	public List<MenuTree> getTree() {
		SysMenu condition = new SysMenu();
		condition.setIsDeleted(CommonConstant.STATUS_NORMAL);
		return TreeUtil.bulidTree(sysMenuService.selectList(new EntityWrapper<>(condition)), -1);
	}

	/**
	 * 返回角色的菜单集合
	 *
	 * @param roleName 角色名称
	 * @return 属性集合
	 */
	@GetMapping("/roleTree/{roleName}" )
	public List<Integer> roleTree(@PathVariable String roleName) {
		return sysMenuService.findMenuByRoleCode(roleName)
			.stream().map(MenuVO::getId)
			.collect(Collectors.toList());
	}

	/**
	 * 通过ID查询菜单的详细信息
	 *
	 * @param id 菜单ID
	 * @return 菜单详细信息
	 */
	@GetMapping("/{id}" )
	public SysMenu menu(@PathVariable Integer id) {
		return sysMenuService.selectById(id);
	}

	/**
	 * 新增菜单
	 *
	 * @param sysMenu 菜单信息
	 * @return success/false
	 */
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_menu_add')" )
	public R<Boolean> menu(@Valid @RequestBody SysMenu sysMenu) {
		return new R<>(sysMenuService.insert(sysMenu));
	}

	/**
	 * 删除菜单
	 *
	 * @param id 菜单ID
	 * @return success/false
	 * TODO  级联删除下级节点
	 */
	@DeleteMapping("/{id}" )
	@PreAuthorize("@pms.hasPermission('sys_menu_del')" )
	public R<Boolean> menuDel(@PathVariable Integer id) {
		return new R<>(sysMenuService.deleteMenu(id));
	}

	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_menu_edit')" )
	public R<Boolean> menuUpdate(@Valid @RequestBody SysMenu sysMenu) {
		return new R<>(sysMenuService.updateMenuById(sysMenu));
	}

}
