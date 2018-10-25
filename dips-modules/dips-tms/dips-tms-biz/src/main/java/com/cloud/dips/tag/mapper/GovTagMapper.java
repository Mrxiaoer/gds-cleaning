package com.cloud.dips.tag.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.vo.GovTagVO;

/**
 * <p>
 * 标签表  Mapper 接口
 * </p>
 *
 * @author ZB
 */
public interface GovTagMapper extends BaseMapper<GovTag> {

	/**
	 * 分页查询信息
	 *
	 * @param query 查询条件
	 * @return list
	 */
	List<GovTagVO> selectGovTagVoPage(Query<GovTag> query, @Param("tagname") Object tagname,@Param("typename") Object typename);
	
	/**
	 * 通过ID查询标签
	 *
	 * @param id
	 * @return GovTagVO
	 */
	GovTagVO selectGovTagVoById(Integer id);
	/**
	 * 根据标签名称查标签
	 * @param tagName
	 * @return
	 */
	Integer findByGovTagName(@Param("tagName") String tagName);
	

}
