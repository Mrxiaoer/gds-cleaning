package com.cloud.dips.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysCityRelation;

/**
 * 
 *
 * @author DingBin
 * @date 2018-11-19 10:07:41
 */
@Mapper
public interface SysCityRelationMapper extends BaseMapper<SysCityRelation> {

	
//
//	/**
//	 * 更改部分关系表数据
//	 *
//	 * @param deptRelation
//	 */
//	void updateCityRealtion(SysCityRelation deptRelation);

	/**
	 * 删除城市关系表数据
	 *
	 * @param id 城市ID
	 */
	void deleteAllCityRealtion(Integer id);

	/**
	 * 删除部分关系表数据
	 * @param relation
	 */
	void deleteCityRealtion(SysCityRelation relation);

	/**
	 * 插入部分关系表数据
	 * @param relation
	 */
	void insertCityRealtion(SysCityRelation relation);

}
