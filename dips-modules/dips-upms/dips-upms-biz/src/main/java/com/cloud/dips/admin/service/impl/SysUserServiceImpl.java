/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.cloud.dips.admin.api.entity.SysRole;
import com.cloud.dips.admin.api.entity.SysUser;
import com.cloud.dips.admin.api.entity.SysUserClob;
import com.cloud.dips.admin.api.entity.SysUserRole;
import com.cloud.dips.admin.api.vo.MenuVO;
import com.cloud.dips.admin.api.vo.UserVO;
import com.cloud.dips.admin.mapper.SysUserClobMapper;
import com.cloud.dips.admin.mapper.SysUserMapper;
import com.cloud.dips.admin.service.SysMenuService;
import com.cloud.dips.admin.service.SysRoleService;
import com.cloud.dips.admin.service.SysUserRoleService;
import com.cloud.dips.admin.service.SysUserService;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.constant.enums.EnumLoginType;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.tag.feign.RemoteTagRelationService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wilson
 * @date 2017/10/31
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
	private final SysUserClobMapper sysUserClobMapper;
	private final RemoteTagRelationService remoteTagService;

	/**
	 * 通过用户名查用户的全部信息
	 *
	 * @param username 用户名
	 * @return
	 */

	@Override
	@Cacheable(value = "user_details", key = "#username", unless = "#result == null")
	public UserInfo findUserInfo(String type, String username) {

		SysUser condition = new SysUser();
		if (EnumLoginType.PWD.getType().equals(type)) {
			condition.setUsername(username);
		} else if (EnumLoginType.WECHAT.getType().equals(type)) {
			condition.setWxOpenid(username);
		} else {
			condition.setQqOpenid(username);
		}
		SysUser sysUser = this.selectOne(new EntityWrapper<>(condition));
		if (sysUser == null) {
			return null;
		}


		UserInfo userInfo = new UserInfo();
		userInfo.setSysUser(sysUser);

		UserVO userVo = new UserVO();
		userVo = sysUserMapper.selectUserVoByUsername(username);
		userInfo.setSysUserVO(userVo);

		//设置角色列表
		List<SysRole> roleList = sysRoleService.findRolesByUserId(sysUser.getUserId());
		List<String> roleCodes = new ArrayList<>();
		if (CollUtil.isNotEmpty(roleList)) {
			roleList.forEach(sysRole -> roleCodes.add(sysRole.getRoleCode()));
		}
		userInfo.setRoles(ArrayUtil.toArray(roleCodes, String.class));

		//设置权限列表（menu.permission）
		Set<MenuVO> menuVoSet = new HashSet<>();
		for (String role : roleCodes) {
			List<MenuVO> menuVos = sysMenuService.findMenuByRoleCode(role);
			menuVoSet.addAll(menuVos);
		}
		Set<String> permissions = new HashSet<>();
		for (MenuVO menuVo : menuVoSet) {
			if (StringUtils.isNotEmpty(menuVo.getPermission())) {
				String permission = menuVo.getPermission();
				permissions.add(permission);
			}
		}
		userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
		return userInfo;
	}


	@Override

	public Page selectWithRolePage(Query query) {

		Object username = query.getCondition().get("username");
		Object realname = query.getCondition().get("realname");
		Object status = query.getCondition().get("status");
		Object deptId = query.getCondition().get("deptId");

		query.setRecords(sysUserMapper.selectUserVoPage(query, username, realname, status, deptId));

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
	 * 删除用户
	 *
	 * @param sysUser 用户
	 * @return Boolean
	 */

	@Override

	public Boolean deleteUserById(SysUser sysUser) {

		//sysUserRoleService.deleteByUserId(sysUser.getUserId());

		this.deleteById(sysUser.getUserId());

		return Boolean.TRUE;

	}

	@Override

	@CacheEvict(value = "user_details", key = "#username")

	public R<Boolean> updateUserInfo(UserDTO userDto, String username) {

		UserVO userVO = sysUserMapper.selectUserVoByUsername(username);

		SysUser sysUser = new SysUser();

		Integer userId = userDto.getUserId();
		String work = userDto.getWorkText();
		String education = userDto.getEducation();

		if (sysUserClobMapper.findOne(userId, "workText") != null) {
			sysUserClobMapper.updateByIdAndKey(userId, "workText", work);
		} else {
			SysUserClob workClob = findOrCreate(userDto, "workText", work);
			if (workClob != null) {
				sysUserClobMapper.insert(workClob);
			}
		}

		if (sysUserClobMapper.findOne(userId, "education") != null) {
			sysUserClobMapper.updateByIdAndKey(userId, "education", education);
		} else {
			SysUserClob edcClob = findOrCreate(userDto, "education", education);
			if (edcClob != null) {
				sysUserClobMapper.insert(edcClob);
			}
		}


		if (StrUtil.isNotBlank(userDto.getPassword())

			&& StrUtil.isNotBlank(userDto.getNewpassword1())) {

			if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
				sysUser.setUserId(userId);

				sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));

			} else {

				log.warn("原密码错误，修改密码失败:{}", username);

				return new R<>(Boolean.FALSE, "原密码错误，修改失败");

			}

		}

		//start：BlackR
		if (StrUtil.isNotBlank(userDto.getSafePassword())
			&& StrUtil.isNotBlank(userDto.getNewpassword1())) {
			if (ENCODER.matches(userDto.getSafePassword(), userVO.getSafePassword())) {
				sysUser.setUserId(userId);
				sysUser.setSafePassword(ENCODER.encode(userDto.getNewpassword1()));
			} else {
				log.warn("原密码错误，修改密码失败:{}", username);
				return new R<>(Boolean.FALSE, "原密码错误，修改失败");
			}
		}
		//end：BlackR

		return new R<>(this.updateById(sysUser));

	}

	@Override

	@CacheEvict(value = "user_details", key = "#username")

	public Boolean updateUser(UserDTO userDto, String username) {

		SysUser sysUser = new SysUser();

		Integer userId = userDto.getUserId();
		String work = userDto.getWorkText();
		String education = userDto.getEducation();

		if (sysUserClobMapper.findOne(userId, "workText") != null) {
			sysUserClobMapper.updateByIdAndKey(userId, "workText", work);
		} else {
			SysUserClob workClob = findOrCreate(userDto, "workText", work);
			if (workClob != null) {
				sysUserClobMapper.insert(workClob);
			}
		}

		if (sysUserClobMapper.findOne(userId, "education") != null) {
			sysUserClobMapper.updateByIdAndKey(userId, "education", education);
		} else {
			SysUserClob edcClob = findOrCreate(userDto, "education", education);
			if (edcClob != null) {
				sysUserClobMapper.insert(edcClob);
			}
		}

		BeanUtils.copyProperties(userDto, sysUser);

		sysUser.setUpdateTime(new Date());
		//start：BlackR
		sysUser.setPassword(this.selectById(userDto).getPassword());
		sysUser.setSafePassword(this.selectById(userDto).getSafePassword());
		//end：BlackR
		this.updateById(sysUser);

		SysUserRole condition = new SysUserRole();

		condition.setUserId(userDto.getUserId());

		if (userDto.getRole() != null) {
			sysUserRoleService.delete(new EntityWrapper<>(condition));

			userDto.getRole().forEach(roleId -> {

				SysUserRole userRole = new SysUserRole();

				userRole.setUserId(sysUser.getUserId());

				userRole.setRoleId(roleId);

				userRole.insert();

			});
		}


		// 保存卓越标签
		Map<String, Object> params1 = new HashMap<>();
		params1.put("gRelationId", sysUser.getUserId());
		params1.put("gNode", "user");
		params1.put("tagKeyWords", getTagKeyWords(userDto.getAbilityTags()));
		params1.put("number", "ability");
		remoteTagService.saveTagRelation(params1);

		// 保存专业标签
		Map<String, Object> params2 = new HashMap<>();
		params2.put("gRelationId", sysUser.getUserId());
		params2.put("gNode", "user");
		params2.put("tagKeyWords", getTagKeyWords(userDto.getProjectTags()));
		params2.put("number", "project");
		remoteTagService.saveTagRelation(params2);

		// 保存学习标签
		Map<String, Object> params3 = new HashMap<>();
		params3.put("gRelationId", sysUser.getUserId());
		params3.put("gNode", "user");
		params3.put("tagKeyWords", getTagKeyWords(userDto.getLearningTags()));
		params3.put("number", "learning");
		remoteTagService.saveTagRelation(params3);

		return Boolean.TRUE;

	}

	// 大文本新增
	private SysUserClob findOrCreate(SysUser sysUser, String key, String value) {
		SysUserClob bean = sysUserClobMapper.findOne(sysUser.getUserId(), key);
		if (bean == null) {
			bean = new SysUserClob(sysUser.getUserId(), key, value);
			return bean;
		}
		return null;
	}

	// 标签拼字符串
	private String getTagKeyWords(List<String> tags) {
		StringBuilder tagKeyWords = new StringBuilder();
		if (tags != null) {
			Set<String> set = new HashSet<>(tags);
			String[] godtagNames = set.toArray(new String[0]);
			for (int i = 0; i < godtagNames.length; i++) {
				if (i != godtagNames.length - 1) {
					tagKeyWords.append(godtagNames[i]).append(",");
				} else {
					tagKeyWords.append(godtagNames[i]);
				}
			}
		}
		return tagKeyWords.toString();
	}

	@Override
	public SysUser save(UserDTO userDto) {
		String work = userDto.getWorkText();
		String education = userDto.getEducation();
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setDelFlag(CommonConstant.STATUS_NORMAL);
		sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
		sysUser.setSafePassword(ENCODER.encode(userDto.getSafePassword()));
		this.insert(sysUser);
		// 保存工作经历
		SysUserClob workClob = findOrCreate(sysUser, "workText", work);
		if (workClob != null) {
			sysUserClobMapper.insert(workClob);
		}
		// 保存学习经历
		SysUserClob edcClob = findOrCreate(sysUser, "education", education);
		if (edcClob != null) {
			sysUserClobMapper.insert(edcClob);
		}
		return sysUser;
	}


	@Override
	public SysUser selectUserById(Integer id) {
		SysUser sysUser = new SysUser();
		sysUser = sysUserMapper.selectUserById(id);
		return sysUser;
	}

	@Override
	public Page selectWithRolePageNoLock(Query query) {
		Object username = query.getCondition().get("username");
		Object realname = query.getCondition().get("realname");
		Object deptId = query.getCondition().get("deptId");

		query.setRecords(sysUserMapper.selectUserVoNoLockPage(query, username, realname, deptId));

		return query;
	}


	@Override
	public Page selectRelation(Query query) {
		// TODO Auto-generated method stub
		query.setRecords(sysUserMapper.selectUserRelation(query));

		return query;

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

}
