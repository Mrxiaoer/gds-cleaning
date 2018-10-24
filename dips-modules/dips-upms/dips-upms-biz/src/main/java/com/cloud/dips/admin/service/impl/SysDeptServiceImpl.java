/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.constant.AdminConstant;
import com.cloud.dips.admin.api.dto.DeptDTO;
import com.cloud.dips.admin.api.dto.DeptTree;
import com.cloud.dips.admin.api.entity.*;
import com.cloud.dips.admin.api.vo.DeptVO;
import com.cloud.dips.admin.api.vo.TreeUtil;
import com.cloud.dips.admin.mapper.*;
import com.cloud.dips.admin.service.SysDeptService;
import com.cloud.dips.common.core.constant.CommonConstant;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.tag.feign.RemoteTagRelationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author Wilson
 * @since 2018-01-20
 */
@Service
@AllArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

	private final SysDeptMapper sysDeptMapper;
	private final SysDeptRelationMapper sysDeptRelationMapper;
	private final SysRelationMapper sysRelationMapper;
	private final SysRelationTypeMapper sysRelationTypeMapper;
	private final SysUserMapper sysUserMapper;
	private final SysDeptClobMapper sysDeptClobMapper;
	private final RemoteTagRelationService remoteTagService;

	/**
	 * 添加信息部门
	 *
	 * @param deptDTO 部门
	 * @return
	 */
	@Override
	public Boolean insertDept(DeptDTO deptDTO) {

		SysDept sysDept = new SysDept();
		BeanUtils.copyProperties(deptDTO, sysDept);
		sysDept.setStatus(CommonConstant.STATUS_NORMAL);
		sysDept = this.save(sysDept);
		this.insertDeptRelation(sysDept);

		// 保存部门简介
		String introduceStr = deptDTO.getIntroduce();
		SysDeptClob introClob = findOrCreate(sysDept, AdminConstant.DEPT_INTRODUCE, introduceStr);
		if (introClob != null) {
			sysDeptClobMapper.insert(introClob);
		}

		// 保存组织架构
		String structureStr = deptDTO.getStructure();
		SysDeptClob strucClob = findOrCreate(sysDept, AdminConstant.DEPT_STRUCTURE, structureStr);
		if (strucClob != null) {
			sysDeptClobMapper.insert(strucClob);
		}

		// 保存核心优势
		String advantageStr = deptDTO.getAdvantage();
		SysDeptClob advanClob = findOrCreate(sysDept, AdminConstant.DEPT_ADVANTAGE, advantageStr);
		if (advanClob != null) {
			sysDeptClobMapper.insert(advanClob);
		}

		// 保存部门负责人
		if (deptDTO.getMasterId() != null) {
			Integer masterId = deptDTO.getMasterId();
			SysUser user = sysUserMapper.selectById(masterId);
			if (user != null) {
				SysRelation master = findOrCreate(sysDept, user, AdminConstant.DEPT_MASTER);
				if (master != null) {
					sysRelationMapper.insert(master);
				}
			}
		}

		// 保存部门成员
		if (deptDTO.getMembers() != null && deptDTO.getMembers().length > 0) {
			Integer[] memberIds = deptDTO.getMembers();
			for (Integer memberId : memberIds) {
				SysUser user = sysUserMapper.selectById(memberId);
				if (user != null) {
					SysRelation member = findOrCreate(sysDept, user, AdminConstant.DEPT_MEMBER);
					if (member != null) {
						sysRelationMapper.insert(member);
					}
				}
			}
		}

		// 保存部门助理
		if (deptDTO.getWriters() != null && deptDTO.getWriters().length > 0) {
			Integer[] writerIds = deptDTO.getWriters();
			for (Integer writerId : writerIds) {
				SysUser user = sysUserMapper.selectById(writerId);
				if (user != null) {
					SysRelation writer = findOrCreate(sysDept, user, AdminConstant.DEPT_WRITER);
					if (writer != null) {
						sysRelationMapper.insert(writer);
					}
				}
			}
		}

		// 保存部门资质
		if (deptDTO.getAptitudes() != null && deptDTO.getAptitudes().length > 0) {
			Integer[] aptitudes = deptDTO.getAptitudes();
			for (Integer aptitudeId : aptitudes) {
				SysRelation relation = findOrCreate(sysDept, aptitudeId, AdminConstant.DEPT_APTITUDE);
				if (relation != null) {
					sysRelationMapper.insert(relation);
				}
			}
		}

		// 保存卓越标签
		Map<String, Object> params1 = new HashMap<>();
		params1.put("gRelationId", sysDept.getDeptId());
		params1.put("gNode", AdminConstant.DEPT_NODE);
		params1.put("tagKeyWords", getTagKeyWords(deptDTO.getAbilityTags()));
		params1.put("number", "ability");
		remoteTagService.saveTagRelation(params1);

		// 保存专业标签
		Map<String, Object> params2 = new HashMap<>();
		params2.put("gRelationId", sysDept.getDeptId());
		params2.put("gNode", AdminConstant.DEPT_NODE);
		params2.put("tagKeyWords", getTagKeyWords(deptDTO.getProjectTags()));
		params2.put("number", "project");
		remoteTagService.saveTagRelation(params2);

		// 保存学习标签
		Map<String, Object> params3 = new HashMap<>();
		params3.put("gRelationId", sysDept.getDeptId());
		params3.put("gNode", AdminConstant.DEPT_NODE);
		params3.put("tagKeyWords", getTagKeyWords(deptDTO.getLearningTags()));
		params3.put("number", "learning");
		remoteTagService.saveTagRelation(params3);

		return Boolean.TRUE;
	}

	/**
	 * 维护部门关系
	 *
	 * @param sysDept 部门
	 */
	private void insertDeptRelation(SysDept sysDept) {
		//增加部门关系表
		SysDeptRelation deptRelation = new SysDeptRelation();
		deptRelation.setDescendant(sysDept.getParentId());
		List<SysDeptRelation> deptRelationList = sysDeptRelationMapper.selectList(new EntityWrapper<>(deptRelation));
		for (SysDeptRelation sysDeptRelation : deptRelationList) {
			sysDeptRelation.setDescendant(sysDept.getDeptId());
			sysDeptRelationMapper.insert(sysDeptRelation);
		}
		//自己也要维护到关系表中
		SysDeptRelation own = new SysDeptRelation();
		own.setDescendant(sysDept.getDeptId());
		own.setAncestor(sysDept.getDeptId());
		sysDeptRelationMapper.insert(own);
	}

	/**
	 * 删除部门
	 *
	 * @param id 部门 ID
	 * @return 成功、失败
	 */
	@Override
	public Boolean deleteDeptById(Integer id) {
		SysDept sysDept = new SysDept();
		sysDept.setDeptId(id);
		sysDept.setUpdateTime(new Date());
		sysDept.setStatus(CommonConstant.STATUS_DEL);
		this.deleteById(sysDept);
		// 不删除部门树中的记录
		//sysDeptMapper.deleteDeptRelation(id);
		return Boolean.TRUE;
	}

	/**
	 * 更新部门
	 *
	 * @param deptDTO 部门信息
	 * @return 成功、失败
	 */
	@Override
	public Boolean updateDeptById(DeptDTO deptDTO) {

		SysDept sysDept = new SysDept();
		BeanUtils.copyProperties(deptDTO, sysDept);
		//更新部门状态
		sysDept = this.update(deptDTO);
		//删除部门关系
		sysDeptMapper.deleteDeptRelation(sysDept.getDeptId());
		//新建部门关系
		this.insertDeptRelation(sysDept);

		// 更新部门简介
		String introduceStr = deptDTO.getIntroduce();
		sysDeptClobMapper.updateByIdAndKey(sysDept.getDeptId(), AdminConstant.DEPT_INTRODUCE, introduceStr);
		// 更新组织架构
		String structureStr = deptDTO.getStructure();
		sysDeptClobMapper.updateByIdAndKey(sysDept.getDeptId(), AdminConstant.DEPT_STRUCTURE, structureStr);
		// 保存核心优势
		String advantageStr = deptDTO.getAdvantage();
		sysDeptClobMapper.updateByIdAndKey(sysDept.getDeptId(), AdminConstant.DEPT_ADVANTAGE, advantageStr);

		// 清空所有的部门负责人
		List<SysRelation> masterList = sysRelationMapper.selectByNodeAndIdAndType(AdminConstant.DEPT_NODE, deptDTO.getDeptId(), AdminConstant.DEPT_MASTER);
		for (SysRelation sr : masterList) {
			sysRelationMapper.deleteOne(AdminConstant.DEPT_NODE, sr.getRelationId(), sr.getCorrelationId(), sr.getTypeId());
		}
		// 保存部门负责人
		if (deptDTO.getMasterId() != null) {
			Integer masterId = deptDTO.getMasterId();
			SysUser user = sysUserMapper.selectById(masterId);
			if (user != null) {
				SysRelation master = findOrCreate(deptDTO, user, AdminConstant.DEPT_MASTER);
				if (master != null) {
					sysRelationMapper.insert(master);
				}
			}
		}

		// 清空所有的部门成员
		List<SysRelation> memberList = sysRelationMapper.selectByNodeAndIdAndType(AdminConstant.DEPT_NODE, deptDTO.getDeptId(), AdminConstant.DEPT_MEMBER);
		for (SysRelation sr : memberList) {
			sysRelationMapper.deleteOne(AdminConstant.DEPT_NODE, sr.getRelationId(), sr.getCorrelationId(), sr.getTypeId());
		}
		// 保存部门成员
		if (deptDTO.getMembers() != null && deptDTO.getMembers().length > 0) {
			Integer[] memberIds = deptDTO.getMembers();
			for (Integer memberId : memberIds) {
				SysUser user = sysUserMapper.selectById(memberId);
				if (user != null) {
					SysRelation member = findOrCreate(deptDTO, user, AdminConstant.DEPT_MEMBER);
					if (member != null) {
						sysRelationMapper.insert(member);
					}
				}
			}
		}

		// 清空所有的部门助理
		List<SysRelation> writerList = sysRelationMapper.selectByNodeAndIdAndType(AdminConstant.DEPT_NODE, deptDTO.getDeptId(), AdminConstant.DEPT_WRITER);
		for (SysRelation sr : writerList) {
			sysRelationMapper.deleteOne(AdminConstant.DEPT_NODE, sr.getRelationId(), sr.getCorrelationId(), sr.getTypeId());
		}
		// 保存部门助理
		if (deptDTO.getWriters() != null && deptDTO.getWriters().length > 0) {
			Integer[] writerIds = deptDTO.getWriters();
			for (Integer writerId : writerIds) {
				SysUser user = sysUserMapper.selectById(writerId);
				if (user != null) {
					SysRelation writer = findOrCreate(deptDTO, user, AdminConstant.DEPT_WRITER);
					if (writer != null) {
						sysRelationMapper.insert(writer);
					}
				}
			}
		}

		// 清空所有的部门资质
		List<SysRelation> aptitudeList = sysRelationMapper.selectByNodeAndIdAndType(AdminConstant.DEPT_NODE, deptDTO.getDeptId(), AdminConstant.DEPT_APTITUDE);
		for (SysRelation sr : aptitudeList) {
			sysRelationMapper.deleteOne(AdminConstant.DEPT_NODE, sr.getRelationId(), sr.getCorrelationId(), sr.getTypeId());
		}
		// 保存部门资质
		if (deptDTO.getAptitudes() != null && deptDTO.getAptitudes().length > 0) {
			Integer[] aptitudes = deptDTO.getAptitudes();
			for (Integer aptitudeId : aptitudes) {
				SysRelation relation = findOrCreate(sysDept, aptitudeId, AdminConstant.DEPT_APTITUDE);
				if (relation != null) {
					sysRelationMapper.insert(relation);
				}
			}
		}

		// 保存卓越标签
		Map<String, Object> params1 = new HashMap<>();
		params1.put("gRelationId", sysDept.getDeptId());
		params1.put("gNode", AdminConstant.DEPT_NODE);
		params1.put("tagKeyWords", getTagKeyWords(deptDTO.getAbilityTags()));
		params1.put("number", "ability");
		remoteTagService.saveTagRelation(params1);

		// 保存专业标签
		Map<String, Object> params2 = new HashMap<>();
		params2.put("gRelationId", sysDept.getDeptId());
		params2.put("gNode", AdminConstant.DEPT_NODE);
		params2.put("tagKeyWords", getTagKeyWords(deptDTO.getProjectTags()));
		params2.put("number", "project");
		remoteTagService.saveTagRelation(params2);

		// 保存学习标签
		Map<String, Object> params3 = new HashMap<>();
		params3.put("gRelationId", sysDept.getDeptId());
		params3.put("gNode", AdminConstant.DEPT_NODE);
		params3.put("tagKeyWords", getTagKeyWords(deptDTO.getLearningTags()));
		params3.put("number", "learning");
		remoteTagService.saveTagRelation(params3);

		return Boolean.TRUE;
	}

	/**
	 * 查看部门信息
	 */
	@Override
	public DeptVO selectDeptVoById(Integer id) {
		return sysDeptMapper.selectDeptVoById(id);
	}

	/**
	 * 分页查询信息
	 *
	 * @param query 查询条件
	 * @return query
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page<DeptVO> selectAllPage(Query query) {
		Object title = query.getCondition().get("title");
		Object masterName = query.getCondition().get("masterName");
		Object isFinancial = query.getCondition().get("isFinancial");
		Object isIntranet = query.getCondition().get("isIntranet");
		query.setRecords(sysDeptMapper.selectDeptVoPage(query, title, masterName, isFinancial, isIntranet));
		return query;
	}

	@Override
	public SysDept save(SysDept sysDept) {
		this.insert(sysDept);
		return sysDept;
	}

	@Override
	public SysDept update(SysDept sysDept) {
		this.updateById(sysDept);
		return sysDept;
	}

	@Override
	public Boolean thoroughlyDeleteDeptById(Integer id) {
		SysDept sysDept = this.selectById(id);
		if (sysDept == null) {
			return false;
		}
		// 如果存在子部门不能删除
		SysDeptRelation deptRelation = new SysDeptRelation();
		deptRelation.setAncestor(id);
		List<SysDeptRelation> deptRelationList = sysDeptRelationMapper.selectList(new EntityWrapper<>(deptRelation));
		if (deptRelationList != null && deptRelationList.size() > 1) {
			return false;
		}

		// 删除大文本字段
		EntityWrapper<SysDeptClob> e1 = new EntityWrapper<>();
		e1.where("g_dept_id = {0}", id);
		sysDeptClobMapper.delete(e1);

		// 删除部门关联
		EntityWrapper<SysRelation> e2 = new EntityWrapper<>();
		e2.where("g_node = {0}", AdminConstant.DEPT_NODE).where("g_relation_id = {0}", id);
		sysRelationMapper.delete(e2);

		// 删除部门关联的标签
		Map<String, Object> params = new HashMap<>();
		params.put("gRelationId", id);
		params.put("gNode", AdminConstant.DEPT_NODE);
		remoteTagService.deleteTagRelation(params);

		// 删除部门树
		sysDeptMapper.deleteDeptRelation(id);

		// 删除部门
		sysDeptMapper.deleteById(id);
		return true;

	}

	/**
	 * 查询部门树
	 *
	 * @param sysDeptEntityWrapper
	 * @return 树
	 */
	@Override
	public List<DeptTree> selectListTree(EntityWrapper<SysDept> sysDeptEntityWrapper) {
		sysDeptEntityWrapper.orderBy("g_order_num", false);
		return getDeptTree(this.selectList(sysDeptEntityWrapper), 0);
	}


	/**
	 * 构建部门树
	 *
	 * @param depts 部门
	 * @param root  根节点
	 * @return
	 */
	private List<DeptTree> getDeptTree(List<SysDept> depts, int root) {
		List<DeptTree> trees = new ArrayList<>();
		DeptTree node;
		for (SysDept dept : depts) {
			if (dept.getParentId().equals(dept.getDeptId())) {
				continue;
			}
			node = new DeptTree();
			node.setId(dept.getDeptId());
			node.setParentId(dept.getParentId());
			node.setName(dept.getTitle());
			trees.add(node);
		}
		return TreeUtil.bulid(trees, root);
	}

	// 关联新增
	private SysRelation findOrCreate(SysDept dept, SysUser user, String number) {
		SysRelation bean = sysRelationMapper.findOne(AdminConstant.DEPT_NODE, dept.getDeptId(), user.getUserId(), number);
		if (bean == null) {
			SysRelationType type = sysRelationTypeMapper.selectByNumber(number);
			bean = new SysRelation(AdminConstant.DEPT_NODE, dept.getDeptId(), user.getUserId(), type.getId());
			return bean;
		}
		return null;
	}

	// 关联新增，直接使用id
	private SysRelation findOrCreate(SysDept dept, Integer id, String number) {
		SysRelation bean = sysRelationMapper.findOne(AdminConstant.DEPT_NODE, dept.getDeptId(), id, number);
		if (bean == null) {
			SysRelationType type = sysRelationTypeMapper.selectByNumber(number);
			bean = new SysRelation(AdminConstant.DEPT_NODE, dept.getDeptId(), id, type.getId());
			return bean;
		}
		return null;
	}

	// 大文本新增
	private SysDeptClob findOrCreate(SysDept dept, String key, String value) {
		SysDeptClob bean = sysDeptClobMapper.findOne(dept.getDeptId(), key);
		if (bean == null) {
			bean = new SysDeptClob(dept.getDeptId(), key, value);
			return bean;
		}
		return null;
	}

	// 标签拼字符串
	private String getTagKeyWords(List<String> tags) {
		StringBuilder tagKeyWords = new StringBuilder();
		if (tags != null) {
			Set<String> set = new HashSet<>(tags);
			String[] godtagNames = set.toArray(new String[0]);
			for (int i = 0; i < godtagNames.length; i++) {
				if (i != godtagNames.length - 1) {
					tagKeyWords.append(godtagNames[i]).append(",");
				} else {
					tagKeyWords.append(godtagNames[i]);
				}
			}
		}
		return tagKeyWords.toString();
	}
}
