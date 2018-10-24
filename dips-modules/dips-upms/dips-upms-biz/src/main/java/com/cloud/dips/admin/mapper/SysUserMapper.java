/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysUser;
import com.cloud.dips.admin.api.vo.UserVO;
import com.cloud.dips.common.core.util.Query;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Wilson
 * @since 2017-10-29
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
	/**
	 * 通过用户名查询用户信息（含有角色信息）
	 *
	 * @param username 用户名
	 * @return userVo
	 */
	UserVO selectUserVoByUsername(String username);
	
	/**
	 * 通过真实姓名查询用户信息（含有角色信息）
	 *
	 * @param username 用户名
	 * @return userVo
	 */
	UserVO selectUserVoByRealname(String realname);

	/**
	 * 分页查询用户信息（含角色）
	 *
	 * @param query    查询条件
	 * @param username 用户名
	 * @return list
	 */
	List selectUserVoPage(Query query, @Param("username") Object username, @Param("realname") Object realname, @Param("status") Object status, @Param("deptId") Object deptId);
	
	/**
	 * 分页查询用户信息（含角色）
	 *
	 * @param query    查询条件
	 * @param username 用户名
	 * @return list
	 */
	List selectUserVoNoLockPage(Query query, @Param("username") Object username, @Param("realname") Object realname, @Param("deptId") Object deptId);

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return userVo
	 */
	UserVO selectUserVoById(Integer id);
	
	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return userVo
	 */
	SysUser selectUserById(Integer id);

	/**
	 * 查找关联 的用户
	 * @param query
	 * @return
	 */
	List selectUserRelation(Query query);
	
	/**
	 * 通过用户名查找已经删除的用户
	 * @param username 用户名
	 * @return 用户对象
	 */
	SysUser selectDeletedUserByUsername(@Param("username")String username);

	/**
	 * 根据用户名删除用户（真实删除）
	 * @param username
	 * @return
	 */
	Boolean deleteSysUserByUsernameAndUserId(@Param("username")String username,@Param("userId")Integer userId);

}
