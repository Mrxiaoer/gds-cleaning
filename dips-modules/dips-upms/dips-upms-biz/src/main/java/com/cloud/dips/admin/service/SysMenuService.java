package com.cloud.dips.admin.service;


import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.entity.SysMenu;
import com.cloud.dips.admin.api.vo.MenuVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
public interface SysMenuService extends IService<SysMenu> {
	/**
	 * 通过角色编号查询URL 权限
	 *
	 * @param role 角色编号
	 * @return 菜单列表
	 */
	List<MenuVO> findMenuByRoleCode(String role);

	/**
	 * 级联删除菜单
	 *
	 * @param id 菜单ID
	 * @return 成功、失败
	 */
	Boolean deleteMenu(Integer id);

	/**
	 * 更新菜单信息
	 *
	 * @param sysMenu 菜单信息
	 * @return 成功、失败
	 */
	Boolean updateMenuById(SysMenu sysMenu);
}
