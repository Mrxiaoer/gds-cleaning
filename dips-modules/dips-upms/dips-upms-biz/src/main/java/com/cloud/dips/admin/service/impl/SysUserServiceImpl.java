package com.cloud.dips.admin.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.dto.UserDTO;
import com.cloud.dips.admin.api.dto.UserInfo;
import com.cloud.dips.admin.api.entity.SysDept;
import com.cloud.dips.admin.api.entity.SysDeptRelation;
import com.cloud.dips.admin.api.entity.SysRole;
import com.cloud.dips.admin.api.entity.SysUser;
import com.cloud.dips.admin.api.entity.SysUserRole;
import com.cloud.dips.admin.api.vo.MenuVO;
import com.cloud.dips.admin.api.vo.UserVO;
import com.cloud.dips.admin.mapper.SysUserMapper;
import com.cloud.dips.admin.service.SysDeptRelationService;
import com.cloud.dips.admin.service.SysDeptService;
import com.cloud.dips.admin.service.SysMenuService;
import com.cloud.dips.admin.service.SysRoleService;
import com.cloud.dips.admin.service.SysUserRoleService;
import com.cloud.dips.admin.service.SysUserService;
import com.cloud.dips.common.core.constant.enums.EnumLoginType;
import com.cloud.dips.common.core.datascope.DataScope;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.security.util.SecurityUtils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private final SysMenuService sysMenuService;
	private final SysUserMapper sysUserMapper;
	private final SysRoleService sysRoleService;
	private final SysUserRoleService sysUserRoleService;
	private final SysDeptRelationService sysDeptRelationService;
	private final SysDeptService sysDeptService;

	/**
	 * 通过用户名查用户的全部信息
	 *
	 * @param username 用户名
	 * @return
	 */
	@Override
	@Cacheable(value = "user_details", key = "#username", unless = "#result == null" )
	public UserInfo findUserInfo(String type, String username) {
		SysUser condition = new SysUser();
		if (EnumLoginType.PWD.getType().equals(type)) {
			condition.setUsername(username);
		} else if (EnumLoginType.WECHAT.getType().equals(type)) {
			condition.setWeixinOpenid(username);
		} else {
			condition.setQqOpenid(username);
		}
		SysUser sysUser = this.selectOne(new EntityWrapper<>(condition));
		if (sysUser == null) {
			return null;
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setSysUser(sysUser);
		//设置角色列表
		List<SysRole> roleList = sysRoleService.findRolesByUserId(sysUser.getId());
		//设置角色列表
		List<String> roleCodes = roleList.stream().map(SysRole::getRoleCode).collect(Collectors.toList());
		String[] roles = ArrayUtil.toArray(roleCodes, String.class);
		userInfo.setRoles(roles);

		//设置权限列表（menu.permission）
		Set<String> permissions = new HashSet<>();
		Arrays.stream(roles).forEach(role -> {
			List<MenuVO> menuVos = sysMenuService.findMenuByRoleCode(role);
			List<String> permissionList = menuVos.stream()
				.filter(menuVo -> StringUtils.isNotEmpty(menuVo.getPermission()))
				.map(MenuVO::getPermission).collect(Collectors.toList());
			permissions.addAll(permissionList);
		});
		userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
		return userInfo;
	}

	@Override
	public Page selectWithRolePage(Query query) {
		DataScope dataScope = new DataScope();
		dataScope.setScopeName("deptId" );
		//dataScope.setIsOnly(true);是否只显示当前用户所在部门
		//dataScope.setDeptIds(getChildDepts());
		Object username = query.getCondition().get("username" );
		query.setRecords(sysUserMapper.selectUserVoPage(query, username, dataScope));
		return query;
	}


	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return 用户信息
	 */
	@Override
	public UserVO selectUserVoById(Integer id) {
		return sysUserMapper.selectUserVoById(id);
	}

	/**
	 * 通过用户名查找已经删除的用户
	 *
	 * @param username 用户名
	 * @return 用户对象
	 */
	@Override
	public SysUser selectDeletedUserByUsername(String username) {
		return sysUserMapper.selectDeletedUserByUsername(username);
	}

	/**
	 * 根据用户名删除用户（真实删除）
	 *
	 * @param username
	 * @return
	 */
	@Override
	public Boolean deleteSysUserByUsernameAndUserId(String username, Integer userId) {

		sysUserMapper.deleteSysUserByUsernameAndUserId(username, userId);
		SysUserRole condition = new SysUserRole();
		condition.setUserId(userId);
		sysUserRoleService.delete(new EntityWrapper<>(condition));
		return Boolean.TRUE;
	}

	/**
	 * 删除用户
	 *
	 * @param sysUser 用户
	 * @return Boolean
	 */
	@Override
	@CacheEvict(value = "user_details", key = "#sysUser.username" )
	public Boolean deleteUserById(SysUser sysUser) {
		sysUserRoleService.deleteByUserId(sysUser.getId());
		this.deleteById(sysUser.getId());
		return Boolean.TRUE;
	}

	@Override
	@CacheEvict(value = "user_details", key = "#username" )
	public R<Boolean> updateUserInfo(UserDTO userDto, String username) {
		UserVO userVO = sysUserMapper.selectUserVoByUsername(username);
		SysUser sysUser = new SysUser();
		if (StrUtil.isNotBlank(userDto.getPassword())
			&& StrUtil.isNotBlank(userDto.getNewpassword1())) {
			if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
				sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
			} else {
				log.warn("原密码错误，修改密码失败:{}", username);
				return new R<>(Boolean.FALSE, "原密码错误，修改失败" );
			}
		}
		sysUser.setPhone(userDto.getPhone());
		sysUser.setId(userVO.getId());
		sysUser.setAvatar(userDto.getAvatar());
		return new R<>(this.updateById(sysUser));
	}

	@Override
	@CacheEvict(value = "user_details", key = "#username" )
	public Boolean updateUser(UserDTO userDto, String username) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setModifiedTime(LocalDateTime.now());
		SysDept sysDept = new SysDept();
		sysDept = sysDeptService.selectById(userDto.getDeptId());
		sysUser.setDeptName(sysDept.getName());
		this.updateById(sysUser);

		SysUserRole condition = new SysUserRole();
		condition.setUserId(userDto.getId());
		sysUserRoleService.delete(new EntityWrapper<>(condition));
		userDto.getRole().forEach(id -> {
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(sysUser.getId());
			userRole.setRoleId(id);
			userRole.insert();
		});
		return Boolean.TRUE;
	}

	/**
	 * 获取当前用户的子部门信息
	 *
	 * @return 子部门列表
	 */
	private List<Integer> getChildDepts() {
		Integer deptId = SecurityUtils.getUser().getDeptId();

		//获取当前部门的子部门
		SysDeptRelation deptRelation = new SysDeptRelation();
		deptRelation.setAncestor(deptId);
		List<SysDeptRelation> deptRelationList = sysDeptRelationService.selectList(new EntityWrapper<>(deptRelation));
		List<Integer> deptIds = new ArrayList<>();
		deptRelationList.forEach(sysDeptRelation -> deptIds.add(sysDeptRelation.getDescendant()));
		return deptIds;
	}

}
