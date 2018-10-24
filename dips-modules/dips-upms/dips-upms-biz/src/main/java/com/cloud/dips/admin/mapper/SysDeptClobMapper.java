package com.cloud.dips.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysDeptClob;
import org.apache.ibatis.annotations.Param;

/**
 * 部门大字段Mapper 接口
 */
public interface SysDeptClobMapper extends BaseMapper<SysDeptClob> {
	SysDeptClob findOne(@Param("deptId") Integer deptId, @Param("key") String key);

	void updateByIdAndKey(@Param("deptId") Integer deptId, @Param("key") String key, @Param("value") String value);
}
