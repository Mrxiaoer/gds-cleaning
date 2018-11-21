package com.cloud.dips.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.dto.UserDTO;
import com.cloud.dips.admin.api.dto.UserInfo;
import com.cloud.dips.admin.api.entity.SysUser;
import com.cloud.dips.admin.api.vo.UserVO;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
public interface SysUserService extends IService<SysUser> {
	/**
	 * 查询用户信息
	 *
	 * @param type     类型
	 * @param username 用户名
	 * @return userInfo
	 */
	UserInfo findUserInfo(String type, String username);

	/**
	 * 分页查询用户信息（含有角色信息）
	 *
	 * @param query 查询条件
	 * @return
	 */
	Page selectWithRolePage(Query query);


	/**
	 * 删除用户
	 *
	 * @param sysUser 用户
	 * @return boolean
	 */
	Boolean deleteUserById(SysUser sysUser);

	/**
	 * 更新当前用户基本信息
	 *
	 * @param userDto  用户信息
	 * @param username 用户名
	 * @return Boolean
	 */
	R<Boolean> updateUserInfo(UserDTO userDto, String username);

	/**
	 * 更新指定用户信息
	 *
	 * @param userDto  用户信息
	 * @param username 用户信息
	 * @return
	 */
	Boolean updateUser(UserDTO userDto, String username);

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return 用户信息
	 */
	UserVO selectUserVoById(Integer id);

	/**
	 * 通过用户名查找已经删除的用户
	 *
	 * @param username 用户名
	 * @return
	 */
	SysUser selectDeletedUserByUsername(String username);

	/**
	 * 根据用户名删除用户（真实删除）
	 *
	 * @param username username
	 * @param userId   userId
	 * @return
	 */
	Boolean deleteSysUserByUsernameAndUserId(String username, Integer userId);


}
