package com.cloud.dips.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysDeptRelation;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
public interface SysDeptRelationMapper extends BaseMapper<SysDeptRelation> {
	/**
	 * 删除部门关系表数据
	 *
	 * @param id 部门ID
	 */
	void deleteAllDeptRealtion(Integer id);

//	/**
//	 * 更改部分关系表数据
//	 *
//	 * @param deptRelation
//	 */
//	void updateDeptRealtion(SysDeptRelation deptRelation);

	/**
	 * 删除部分关系表数据
	 * @param relation
	 */
	void deleteDeptRealtion(SysDeptRelation relation);

	/**
	 * 添加部分关系表数据
	 * @param relation
	 */
	void insertDeptRealtion(SysDeptRelation relation);
}
