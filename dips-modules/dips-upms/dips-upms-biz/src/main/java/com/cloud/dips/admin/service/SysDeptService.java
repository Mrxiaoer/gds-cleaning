package com.cloud.dips.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.dto.DeptTree;
import com.cloud.dips.admin.api.entity.SysDept;
import com.cloud.dips.admin.api.vo.DeptVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
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
	 * @param sysDept
	 * @return
	 */
	Boolean insertDept(SysDept sysDept);

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
	 * @param sysDept 部门信息
	 * @return 成功、失败
	 */
	Boolean updateDeptById(SysDept sysDept);

	/**
	 * 通过ID查询部门信息
	 *
	 * @param id 部门ID
	 * @return 部门信息
	 */
	DeptVO selectDeptVoById(Integer id);
	
	/**
	 * 通过名称查询部门ID
	 *
	 * @param id 部门ID
	 * @return 部门信息
	 */
	Integer findDeptIdByName(String name);
}
