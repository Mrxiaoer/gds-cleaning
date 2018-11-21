package com.cloud.dips.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.dto.CityTree;
import com.cloud.dips.admin.api.entity.SysCity;

/**
 * 
 *
 * @author DingBin
 * @date 2018-11-19 10:07:31
 */
public interface SysCityService extends IService<SysCity> {

	
	List<CityTree> selectListTree(EntityWrapper<SysCity> entityWrapper);
	
	
	/**
	 * 添加信息城市
	 *
	 * @param sysCity
	 * @return
	 */
	Boolean insertCity(SysCity sysCity);

	/**
	 * 删除城市
	 *
	 * @param id 城市 ID
	 * @return 成功、失败
	 */
	Boolean deleteCityById(Integer id);

	/**
	 * 更新城市
	 *
	 * @param sysCity 城市信息
	 * @return 成功、失败
	 */
	Boolean updateCityById(SysCity sysCity);



}

