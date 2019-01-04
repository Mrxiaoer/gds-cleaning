package com.cloud.dips.tag.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.tag.api.entity.GovTagTypeRelation;
import com.cloud.dips.tag.api.vo.GovTagTypeVO;

/**
 * <p>
 * 标签与标签分类关联表  Mapper 接口
 * </p>
 *
 * @author ZB
 */
public interface GovTagTypeRelationMapper extends BaseMapper<GovTagTypeRelation> {
	
	/**
	 * 获取标签分类vo集合
	 * @param tagId
	 * @return
	 */
	public List<GovTagTypeVO> selectTagTypes(@Param("tagId") String tagId);

}
