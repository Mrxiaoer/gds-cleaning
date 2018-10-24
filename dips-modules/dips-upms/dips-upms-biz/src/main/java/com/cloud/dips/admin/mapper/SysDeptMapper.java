/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.admin.api.entity.SysDept;
import com.cloud.dips.admin.api.vo.DeptVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门管理 Mapper 接口
 * </p>
 *
 * @author Wilson
 * @since 2018-01-20
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

	/**
	 * 关联dept——relation
	 *
	 * @param delFlag 删除标记
	 * @return 数据列表
	 */
	List<SysDept> selectDeptDtoList(String delFlag);

	/**
	 * 删除部门关系表数据
	 *
	 * @param id 部门ID
	 */
	void deleteDeptRelation(Integer id);

	/**
	 * 通过ID查询信息
	 *
	 * @param id
	 * @return notificationVo
	 */
	DeptVO selectDeptVoById(Integer id);

	/**
	 * 分页查询信息
	 *
	 * @param title 查询条件，标题，所属大区，负责人，是否财务，是否内网
	 * @return list
	 */
	List selectDeptVoPage(Query query, @Param("title") Object title, @Param("masterName") Object masterName, @Param("isFinancial") Object isFinancial, @Param("isIntranet") Object isIntranet);

}
