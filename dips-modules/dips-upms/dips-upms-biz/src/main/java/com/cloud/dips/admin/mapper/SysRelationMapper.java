package com.cloud.dips.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 关联Mapper接口
 */
public interface SysRelationMapper extends BaseMapper<SysRelation> {
	SysRelation findOne(@Param("node") String node, @Param("relationId") Integer relationId, @Param("correlationId") Integer correlationId, @Param("number") String number);

	List<SysRelation> selectByNodeAndIdAndType(@Param("node") String node, @Param("relationId") Integer relationId, @Param("number") String number);

	void deleteOne(@Param("node") String node, @Param("relationId") Integer relationId, @Param("correlationId") Integer correlationId, @Param("typeId") Integer typeId);
}
