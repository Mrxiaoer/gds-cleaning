package com.cloud.dips.admin.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysMenu;
import com.cloud.dips.admin.api.vo.MenuVO;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	/**
	 * 通过角色编号查询菜单
	 *
	 * @param role 角色编号
	 * @return
	 */
	List<MenuVO> findMenuByRoleCode(String role);

	/**
	 * 通过角色ID查询权限
	 *
	 * @param roleIds Ids
	 * @return
	 */
	List<String> findPermissionsByRoleIds(String roleIds);
}
