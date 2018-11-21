package com.cloud.dips.admin.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.entity.SysRoleMenu;
import com.cloud.dips.admin.mapper.SysRoleMenuMapper;
import com.cloud.dips.admin.service.SysRoleMenuService;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Service
@AllArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
	private CacheManager cacheManager;

	/**
	 * @param role
	 * @param roleId  角色
	 * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return
	 */
	@Override
	@CacheEvict(value = "menu_details", key = "#role + '_menu'" )
	public Boolean insertRoleMenus(String role, Integer roleId, String menuIds) {
		SysRoleMenu condition = new SysRoleMenu();
		condition.setRoleId(roleId);
		this.delete(new EntityWrapper<>(condition));

		if (StrUtil.isBlank(menuIds)) {
			return Boolean.TRUE;
		}

		String[] menuIdList = menuIds.split("," );
		List<SysRoleMenu> roleMenuList = Arrays.stream(menuIdList)
			.map(menuId -> {
				SysRoleMenu roleMenu = new SysRoleMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(Integer.valueOf(menuId));
				return roleMenu;
			}).collect(Collectors.toList());

		//清空userinfo
		Objects.requireNonNull(cacheManager.getCache("user_details" )).clear();
		return this.insertBatch(roleMenuList);
	}
}
