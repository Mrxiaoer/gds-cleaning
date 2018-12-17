package com.cloud.dips.admin.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.dto.CityTree;
import com.cloud.dips.admin.api.entity.SysCity;
import com.cloud.dips.admin.api.entity.SysCityRelation;
import com.cloud.dips.admin.api.vo.TreeUtil;
import com.cloud.dips.admin.mapper.SysCityMapper;
import com.cloud.dips.admin.mapper.SysCityRelationMapper;
import com.cloud.dips.admin.service.SysCityService;
import com.cloud.dips.common.core.constant.CommonConstant;

import lombok.AllArgsConstructor;

/**
 * 
 *
 * @author DingBin
 * @date 2018-11-19 10:07:31
 */
@Service("govCityService")
@AllArgsConstructor
public class SysCityServiceImpl extends ServiceImpl<SysCityMapper, SysCity> implements SysCityService {

	private final SysCityRelationMapper sysCityRelationMapper;
	

	@Override
	public Boolean insertCity(SysCity city) {
		SysCity sysCity = new SysCity();
		BeanUtils.copyProperties(city, sysCity);
		this.insert(sysCity);
		this.insertCityRelation(sysCity);
		return Boolean.TRUE;
	}

	private void insertCityRelation(SysCity sysCity) {
		// 增加城市关系表
		SysCityRelation cityRelation = new SysCityRelation();
		if (sysCity.getParentId()!=0) {
			cityRelation.setDescendant(sysCity.getParentId());
			List<SysCityRelation> cityRelationList = sysCityRelationMapper.selectList(new EntityWrapper<>(cityRelation));
			for (SysCityRelation sysCityRelation : cityRelationList) {
				sysCityRelation.setDescendant(sysCity.getId());
				sysCityRelationMapper.insert(sysCityRelation);
			}
		}
		// 自己也要维护到关系表中
			SysCityRelation own = new SysCityRelation();
			own.setAncestor(sysCity.getId());
			own.setDescendant(sysCity.getId());
			sysCityRelationMapper.insert(own);
	}

	/**
	 * 删除城市
	 *
	 * @param id
	 *            城市 ID
	 * @return 成功、失败
	 */
	@Override
	public Boolean deleteCityById(Integer id) {
		SysCity sysCity = new SysCity();
		sysCity.setId(id);
		sysCity.setModifiedTime(LocalDateTime.now());
		sysCity.setIsDeleted(CommonConstant.STATUS_DEL);
		this.deleteById(sysCity);
		sysCityRelationMapper.deleteAllCityRealtion(id);
		return Boolean.TRUE;
	}

	@Override
	public Boolean updateCityById(SysCity sysCity) {
		// 更新城市状态
		this.updateById(sysCity);
		// 更新城市关系
		SysCityRelation relation = new SysCityRelation();
		if (sysCity.getParentId() != null) {
			relation.setAncestor(sysCity.getParentId());
		} else {
			relation.setAncestor(1);
		}
		relation.setDescendant(sysCity.getId());
		sysCityRelationMapper.deleteCityRealtion(relation);
		sysCityRelationMapper.insertCityRealtion(relation);
		return Boolean.TRUE;
	}

	@Override
	public List<CityTree> selectListTree(EntityWrapper<SysCity> entityWrapper) {
		entityWrapper.orderBy("order_num", false);
		return getCityTree(this.selectList(entityWrapper), 0);
	}

	/**
	 * 构建城市树
	 *
	 * @param citys
	 *            城市
	 * @param root
	 *            根节点
	 * @return
	 */
	private List<CityTree> getCityTree(List<SysCity> citys, int root) {
		List<CityTree> trees = new ArrayList<>();
		CityTree node;
		for (SysCity city : citys) {
			if (city.getParentId().equals(city.getId())) {
				continue;
			}
			node = new CityTree();
			node.setId(city.getId());
			node.setParentId(city.getParentId());
			node.setName(city.getName());
			trees.add(node);
		}
		return TreeUtil.bulid(trees, root);
	}
}
