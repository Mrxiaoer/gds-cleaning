package com.cloud.dips.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.entity.SysMenu;
import com.cloud.dips.admin.api.vo.MenuVO;
import com.cloud.dips.admin.mapper.SysMenuMapper;
import com.cloud.dips.admin.service.SysMenuService;
import com.cloud.dips.common.core.constant.CommonConstant;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	@Cacheable(value = "menu_details", key = "#role  + '_menu'")
	public List<MenuVO> findMenuByRoleCode(String role) {
		return sysMenuMapper.findMenuByRoleCode(role);
	}

	@Override
	@CacheEvict(value = "menu_details", allEntries = true)
	public Boolean deleteMenu(Integer id) {
		// 删除当前节点
		SysMenu condition1 = new SysMenu();
		condition1.setId(id);
		condition1.setIsDeleted(CommonConstant.STATUS_DEL);
		this.updateById(condition1);

		// 删除父节点为当前节点的节点
		SysMenu conditon2 = new SysMenu();
		conditon2.setParentId(id);
		SysMenu sysMenu = new SysMenu();
		sysMenu.setIsDeleted(CommonConstant.STATUS_DEL);
		return this.update(sysMenu, new EntityWrapper<>(conditon2));
	}

	@Override
	@CacheEvict(value = "menu_details", allEntries = true)
	public Boolean updateMenuById(SysMenu sysMenu) {
		return this.updateById(sysMenu);
	}
}
