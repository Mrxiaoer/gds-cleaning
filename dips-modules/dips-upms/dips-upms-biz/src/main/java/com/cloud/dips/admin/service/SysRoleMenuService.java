package com.cloud.dips.admin.service;


import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.entity.SysRoleMenu;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

	/**
	 * 更新角色菜单
	 *
	 * @param role
	 * @param roleId  角色
	 * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return
	 */
	Boolean insertRoleMenus(String role, Integer roleId, String menuIds);
}
