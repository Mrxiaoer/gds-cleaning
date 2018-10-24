/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.dto.DeptDTO;
import com.cloud.dips.admin.api.dto.DeptTree;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.admin.api.entity.SysDept;
import com.cloud.dips.admin.api.vo.DeptVO;

import java.util.List;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author Wilson
 * @since 2018-01-20
 */
public interface SysDeptService extends IService<SysDept> {

	/**
	 * 查询部门树菜单
	 *
	 * @param sysDeptEntityWrapper
	 * @return 树
	 */
	List<DeptTree> selectListTree(EntityWrapper<SysDept> sysDeptEntityWrapper);

	/**
	 * 添加信息部门
	 *
	 * @param deptDTO
	 * @return
	 */
	Boolean insertDept(DeptDTO deptDTO);

	/**
	 * 删除部门
	 *
	 * @param id 部门 ID
	 * @return 成功、失败
	 */
	Boolean deleteDeptById(Integer id);

	/**
	 * 更新部门
	 *
	 * @param deptDTO 部门信息
	 * @return 成功、失败
	 */
	Boolean updateDeptById(DeptDTO deptDTO);

	/**
	 * 根据id查询部门
	 *
	 * @param id 部门 ID
	 * @return
	 */
	DeptVO selectDeptVoById(Integer id);

	/**
	 * 分页查询信息
	 *
	 * @param query 查询条件
	 *
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Page<DeptVO> selectAllPage(Query query);

	/**
	 * 新增，返回sysDept
	 * */
	SysDept save(SysDept sysDept);

	/**
	 * 更新，返回sysDept
	 * */
	SysDept update(SysDept sysDept);

	Boolean thoroughlyDeleteDeptById(Integer id);

}
