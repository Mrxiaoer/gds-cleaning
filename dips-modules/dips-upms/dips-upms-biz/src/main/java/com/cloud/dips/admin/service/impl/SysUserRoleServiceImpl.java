package com.cloud.dips.admin.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.entity.SysUserRole;
import com.cloud.dips.admin.mapper.SysUserRoleMapper;
import com.cloud.dips.admin.service.SysUserRoleService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

	/**
	 * 根据用户Id删除该用户的角色关系
	 *
	 * @param userId 用户ID
	 * @return boolean
	 * @author 寻欢·李
	 * @date 2017年12月7日 16:31:38
	 */
	@Override
	public Boolean deleteByUserId(Integer userId) {
		return baseMapper.deleteByUserId(userId);
	}
}
