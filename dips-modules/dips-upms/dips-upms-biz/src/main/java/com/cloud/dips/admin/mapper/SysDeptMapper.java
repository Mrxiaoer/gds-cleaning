package com.cloud.dips.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysDept;
import com.cloud.dips.admin.api.vo.DeptVO;

/**
 * <p>
 * 部门管理 Mapper 接口
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
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
	 * 通过ID查询部门信息
	 *
	 * @param id 部门ID
	 * @return 部门信息
	 */
	DeptVO selectDeptVoById(@Param("id") Integer id);
}
