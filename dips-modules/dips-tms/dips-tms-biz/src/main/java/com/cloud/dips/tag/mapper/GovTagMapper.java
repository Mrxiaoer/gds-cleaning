package com.cloud.dips.tag.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.vo.GovTagVO;
import com.cloud.dips.tag.api.vo.MapVO;

/**
 * <p>
 * 标签表  Mapper 接口
 * </p>
 *
 * @author ZB
 */
public interface GovTagMapper extends BaseMapper<GovTag> {

	/**
	 * 分页查询标签信息
	 * @param query 
	 * @param tagname 标签名称
	 * @param typeid 类型id
	 * @param levelid 级别id
	 * @param status 状态
	 * @param enable 启用
	 * @param fob 前后端
	 * @return List<GovTagVO>
	 */
	List<GovTagVO> selectGovTagVoPage(Query<GovTag> query, @Param("tagname") Object tagname,
			@Param("typeid") Object typeid,@Param("levelid") Object levelid,@Param("status") Object status,@Param("enable") Object enable,@Param("fob") Object fob);
	
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
	
	/**
	 * 根据标签分类统计标签
	 * @return
	 */
	List<MapVO> coutnByType();
	/**
	 * 根据日期统计标签
	 * @param date
	 * @return
	 */
	List<MapVO> coutnByDate(Date date);
	
	/**
	 * 获取所有标签集合
	 * @return
	 */
	List<GovTagVO> getAllTag();

}
