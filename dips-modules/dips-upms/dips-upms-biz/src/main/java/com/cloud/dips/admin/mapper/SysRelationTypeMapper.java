package com.cloud.dips.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysRelationType;
import com.cloud.dips.common.core.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 关联类型Mapper接口
 */
public interface SysRelationTypeMapper extends BaseMapper<SysRelationType> {
	SysRelationType selectByNumber(String number);

	List<SysRelationType> selectAllPage(Query query, @Param("name") Object name, @Param("number") Object number);
}
