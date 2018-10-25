package com.cloud.dips.tag.mapper;


import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.tag.api.entity.GovTagDescription;

/**
 * <p>
 * 标签描述表  Mapper 接口
 * </p>
 *
 * @author ZB
 */
public interface GovTagDescriptionMapper extends BaseMapper<GovTagDescription> {
	
	Boolean deleteByTagId(@Param("tagId") Integer tagId);
}
