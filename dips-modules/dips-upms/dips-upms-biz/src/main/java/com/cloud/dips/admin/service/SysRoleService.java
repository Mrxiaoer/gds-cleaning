package com.cloud.dips.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.dto.RoleDTO;
import com.cloud.dips.admin.api.entity.SysRole;
import com.cloud.dips.common.core.util.Query;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
public interface SysRoleService extends IService<SysRole> {

	/**
	 * 添加角色
	 *
	 * @param roleDto 角色信息
	 * @return 成功、失败
	 */
	Boolean insertRole(RoleDTO roleDto);

	/**
	 * 分页查角色列表
	 *
	 * @param objectQuery         查询条件
	 * @param objectEntityWrapper wapper
	 * @return page
	 */
	Page selectwithDeptPage(Query<Object> objectQuery, EntityWrapper<Object> objectEntityWrapper);

	/**
	 * 更新角色
	 *
	 * @param roleDto 含有部门信息
	 * @return 成功、失败
	 */
	Boolean updateRoleById(RoleDTO roleDto);

	/**
	 * 通过部门ID查询角色列表
	 *
	 * @param deptId 部门ID
	 * @return 角色列表
	 */
	List<SysRole> selectListByDeptId(Integer deptId);

	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	List<SysRole> findRolesByUserId(Integer userId);
}
