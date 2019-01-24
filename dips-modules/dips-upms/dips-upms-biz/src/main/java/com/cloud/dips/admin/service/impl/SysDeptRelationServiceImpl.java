package com.cloud.dips.admin.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.entity.SysDept;
import com.cloud.dips.admin.api.entity.SysDeptRelation;
import com.cloud.dips.admin.mapper.SysDeptRelationMapper;
import com.cloud.dips.admin.service.SysDeptRelationService;

import lombok.AllArgsConstructor;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Service
@AllArgsConstructor
public class SysDeptRelationServiceImpl extends ServiceImpl<SysDeptRelationMapper, SysDeptRelation> implements SysDeptRelationService {
	private final SysDeptRelationMapper sysDeptRelationMapper;

	/**
	 * 维护部门关系
	 *
	 * @param sysDept 部门
	 */
	@Override
	public void insertDeptRelation(SysDept sysDept) {
		//增加部门关系表
		SysDeptRelation condition = new SysDeptRelation();
		if (sysDept.getParentId()!=0) {
			condition.setDescendant(sysDept.getParentId());
			List<SysDeptRelation> relationList = sysDeptRelationMapper.selectList(new EntityWrapper<>(condition))
				.stream().map(relation -> {
					relation.setDescendant(sysDept.getId());
					return relation;
				}).collect(Collectors.toList());
			this.insertBatch(relationList);
		}
		//自己也要维护到关系表中
		SysDeptRelation own = new SysDeptRelation();
		own.setDescendant(sysDept.getId());
		own.setAncestor(sysDept.getId());
		sysDeptRelationMapper.insert(own);
	}

	/**
	 * 通过ID删除部门关系
	 *
	 * @param id
	 */
	@Override
	public void deleteAllDeptRealtion(Integer id) {
		baseMapper.deleteAllDeptRealtion(id);
	}

	/**
	 * 更新部门关系
	 *
	 * @param relation
	 */
	@Override
	public void updateDeptRealtion(SysDeptRelation relation) {
		
		baseMapper.deleteDeptRealtion(relation);
		baseMapper.insertDeptRealtion(relation);
	}

}
