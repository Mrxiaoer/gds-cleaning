/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.controller;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.dto.UserDTO;
import com.cloud.dips.admin.api.dto.UserInfo;
import com.cloud.dips.admin.api.entity.SysUser;
import com.cloud.dips.admin.api.entity.SysUserDetail;
import com.cloud.dips.admin.api.entity.SysUserRole;
import com.cloud.dips.admin.api.vo.UserVO;
import com.cloud.dips.admin.mapper.SysUserDetailMapper;
import com.cloud.dips.admin.service.SysUserDetailService;
import com.cloud.dips.admin.service.SysUserService;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.constant.SecurityConstants;
import com.cloud.dips.common.core.constant.enums.EnumLoginType;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.security.util.SecurityUtils;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author RCG
 * @date 2017/10/28
 */
@RestController
@RequestMapping("/user")
public class UserController {
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	@Autowired
	private SysUserService userService;
	@Autowired
	private SysUserDetailService sysUserDetailService;
	@Autowired
	private SysUserDetailMapper sysUserDetailMapper;

	/**
	 * 获取当前用户信息（角色、权限）
	 * 并且异步初始化用户部门信息
	 *
	 * @param from 请求标志，该接口会被 auth、 前端调用
	 * @param username 用户名
	 * @return 用户名
	 */
	@GetMapping(value = {"/info", "/info/{username}"})
	public R<UserInfo> user(@PathVariable(required = false) String username,
							@RequestHeader(required = false) String from) {
		// 查询用户不为空时判断是不是内部请求
		if (StrUtil.isNotBlank(username) && !StrUtil.equals(SecurityConstants.FROM_IN, from)) {
			return new R<>(null, "error");
		}
		//为空时查询当前用户
		if (StrUtil.isBlank(username)) {
			username = SecurityUtils.getUser().getUsername();
		}
		return new R<>(userService.findUserInfo(EnumLoginType.PWD.getType(), username));
	}

	/**
	 * 通过ID查询当前用户信息
	 *
	 * @param id ID
	 * @return 用户信息
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "查询用户详情", notes = "根据ID查询用户详情: params{用户ID: userId}",httpMethod="GET")
	public UserVO user(@PathVariable Integer id) {
		return userService.selectUserVoById(id);
	}

	/**
	 * 删除用户信息
	 *
	 * @param id ID
	 * @return R
	 */
	@SysLog("删除用户信息")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_user_del')")
	@ApiOperation(value = "删除用户", notes = "根据ID删除用户")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "path",example = "1000")
	public R<Boolean> userDel(@PathVariable Integer id) {
		SysUser sysUser = userService.selectById(id);
		return new R<>(userService.deleteUserById(sysUser));
	}
	
	/**
	 * 锁定用户
	 *
	 * @param id ID
	 * @return R
	 */
	@SysLog("锁定或解锁用户")
	@PutMapping("/lock/{id}")
	@ApiOperation(value = "锁定或解锁用户", notes = "根据ID锁定或解锁用户", httpMethod = "PUT")
	public R<Boolean> userLock(@PathVariable Integer id) {
		SysUser sysUser = userService.selectUserById(id);
		EntityWrapper<SysUser> e=new EntityWrapper<SysUser>();
		e.where( "g_user_id = {0}", sysUser.getUserId());		

		if(1 == sysUser.getStatus()){
			userService.updateForSet("g_status=0", e);
		}else{
			userService.updateForSet("g_status=1", e);
		}
		return new R<>(true);
	}

	/**
	 * 添加用户
	 *
	 * @param userDto 用户信息
	 * @return success/false
	 */
	@SysLog("添加用户")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_user_add')")
	@ApiOperation(value = "添加用户", notes = "添加用户", httpMethod = "POST")
	public R<Boolean> user(@RequestBody UserDTO userDto) {
		SysUser deletedUser = userService.selectDeletedUserByUsername(userDto.getUsername());
		if (deletedUser != null) {
			userService.deleteSysUserByUsernameAndUserId(userDto.getUsername(), deletedUser.getUserId());
		}
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setDelFlag(CommonConstant.STATUS_NORMAL);
		sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
		userService.insert(sysUser);
		
		SysUserRole userRole = new SysUserRole();
		userRole.setUserId(sysUser.getUserId());
		userRole.setRoleId(1);
		userRole.insert();
		
		/*userDto.getRole().forEach(roleId -> {
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(sysUser.getUserId());
			userRole.setRoleId(roleId);
			userRole.insert();
		});*/
		return new R<>(Boolean.TRUE);
	}
/*	*//**
	 * 更新用户信息
	 *
	 * @param userDto 用户信息
	 * @return R
	 *//*
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_user_edit')")
	public R<Boolean> userUpdate(@RequestBody UserDTO userDto) {
		SysUser user = userService.selectById(userDto.getUserId());
		
		SysUserDetail sysUserDetail = sysUserDetailMapper.findOne(userDto.getUserId());
		if(sysUserDetail==null){
			sysUserDetail = new SysUserDetail();
		}
		BeanUtils.copyProperties(userDto, sysUserDetail);
		sysUserDetailService.insertOrUpdate(sysUserDetail);
		
		return new R<>(userService.updateUser(userDto, user.getUsername()));
	}*/

	/**
	 * 分页查询用户
	 *
	 * @param params 参数集
	 * @return 未锁定用户集合
	 */
	@RequestMapping
	@ApiOperation(value = "用户查询", notes = "用户集合",httpMethod="GET")
	public Page userPageNoLock(@RequestParam Map<String, Object> params) {
		return userService.selectWithRolePageNoLock(new Query(params));
	}
	
	/**
	 * 分页查询用户
	 *
	 * @param params 参数集
	 * @return 用户集合
	 */
	@GetMapping("/admin")
	@ApiOperation(value = "用户查询", notes = "用户集合",httpMethod="GET")
	public Page userPage(@RequestParam Map<String, Object> params) {
		return userService.selectWithRolePage(new Query(params));
	}

	/**
	 * 修改个人信息
	 *
	 * @param userDto userDto
	 * @return success/false
	 */
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_user_edit')")
	@ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "PUT")
	public R<Boolean> editInfo(@RequestBody UserDTO userDto) {
				
		SysUserDetail sysUserDetail = sysUserDetailMapper.findOne(userDto.getUserId());
		if(sysUserDetail==null){
			sysUserDetail = new SysUserDetail();
			sysUserDetail.setUserId(userDto.getUserId());
		}

		BeanUtils.copyProperties(userDto, sysUserDetail);
		
		String avater=userDto.getAvatar();
		
		if(StrUtil.isNotBlank(avater)){
			sysUserDetail.setWithAvatar(true);
		}else{
			sysUserDetail.setWithAvatar(false);
		}

		sysUserDetailService.insertOrUpdate(sysUserDetail);
		
		SysUser user = userService.selectById(userDto.getUserId());
		
		userService.updateUser(userDto, user.getUsername());
		
		return userService.updateUserInfo(userDto, SecurityUtils.getUser().getUsername());
	}
	
	
	/**
	 * 分页查询用户  关联时使用
	 *
	 * @param params 参数集
	 * @return 用户集合
	 */
	@GetMapping("/selectRelation")
	@ApiOperation(value = "用户查询（关联）", notes = "用户集合",httpMethod="GET")
	public Page userRelation(@RequestParam Map<String, Object> params) {
		return userService.selectRelation(new Query(params));
	}
	
}