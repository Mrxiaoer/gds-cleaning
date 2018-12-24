package com.cloud.dips.tag.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.tag.api.entity.GovTagRelation;
import com.cloud.dips.tag.api.vo.GovTagRelationVO;

/**
 * <p>
 * 标签关联表  Mapper 接口
 * </p>
 *
 * @author ZB
 */
public interface GovTagRelationMapper extends BaseMapper<GovTagRelation> {
	
	/**
	 * 获取标签集合
	 * @param relationId
	 * @param node
	 * @param fob
	 * @return
	 */
	public List<GovTagRelationVO> getTags(@Param("relationId") Integer relationId,@Param("node") String node,@Param("fob") String fob);
}
