package com.cloud.dips.tag.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.tag.api.entity.GovTagType;
import com.cloud.dips.tag.api.vo.GovTagTypeVO;

/**
 * <p>
 * 标签分类表  Mapper 接口
 * </p>
 *
 * @author ZB
 */
public interface GovTagTypeMapper extends BaseMapper<GovTagType> {
	/**
	 * 查询一级分类
	 * @return
	 */
	List<GovTagTypeVO> selectParents();
	/**
	 * 分页查询分类
	 * @param query
	 * @param name 分类名称
	 * @return
	 */
	public List<GovTagTypeVO> selectTypeVoPage(Query<GovTagTypeVO> query,@Param("name") Object name);
}
