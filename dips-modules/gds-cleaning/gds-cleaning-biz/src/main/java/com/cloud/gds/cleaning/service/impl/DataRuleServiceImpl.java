package com.cloud.gds.cleaning.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.SpecialStringUtil;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.BaseVo;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.api.vo.LabelVo;
import com.cloud.gds.cleaning.mapper.DataRuleMapper;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataRuleService;
import com.cloud.gds.cleaning.utils.DataRuleUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author : lolilijve
 * @Email : 1042703214@qq.com
 * @Date : 2018-11-22
 */
@Service("dataFieldService")
public class DataRuleServiceImpl extends ServiceImpl<DataRuleMapper, DataRule> implements DataRuleService {

	@Autowired
	DataFieldService dataFieldService;

	@Override
	public Page queryPage(Map<String, Object> params) {

		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<DataRule> p = new Page<DataRule>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<DataRule> e = new EntityWrapper<DataRule>();
		String name = params.getOrDefault("name", "").toString();
		if(StrUtil.isNotBlank(name)){
			e.like("name",  SpecialStringUtil.escapeExprSpecialWord(name));
		}
		e.eq("is_deleted", DataCleanConstant.NO);
		Page page = this.selectPage(p,e);
		if (page.getRecords() != null){
			List<DataRule> dataRules = page.getRecords();
			List<BaseVo> vos = new ArrayList<>();
			for (DataRule dataRule : dataRules) {
				BaseVo baseVo = new BaseVo();
				BeanUtils.copyProperties(dataRule, baseVo);
				vos.add(baseVo);
			}
			page.setRecords(vos);
		}
		return page;
	}

	@Override
	public DataRuleVo queryById(Long id) {
		return DataRuleUtils.po2Vo(this.selectById(id));
	}

	@Override
	public List<BaseVo> selectAll() {
		DataRule dataRule = new DataRule();
		dataRule.setIsDeleted(DataCleanConstant.NO);
		assert SecurityUtils.getUser() != null;
		dataRule.setDeptId(SecurityUtils.getUser().getDeptId());
		List<DataRule> dataRules = this.selectList(new EntityWrapper<>(dataRule));
		//返回id与name
		return DataRuleUtils.takeName(dataRules);
	}

	@Override
	public ArrayList<LabelVo> gainDynamicKey(Long id) {
		DataRuleVo dataRuleVo = DataRuleUtils.po2Vo(this.selectById(id));
		return DataRuleUtils.convey(dataRuleVo);
	}

	@Override
	public Boolean customUpdate(DataRuleVo dataRuleVo) {
		// 赋值相关信息
		DataRule dataRule = DataRuleUtils.vo2po(dataRuleVo);
		assert SecurityUtils.getUser() != null;
//		dataRule.setModifiedUser(SecurityUtils.getUser().getId());
		dataRule.setModifiedTime(LocalDateTime.now());

		// 如果规则的百分比更新,是否需要重新分析更新
		dataFieldService.updateNeedReanalysis(dataRuleVo.getDetail() == null ? 0 : dataRuleVo.getId());

		return this.updateById(dataRule);
	}

	@Override
	public Boolean deleteById(Long id) {
		DataRule dataRule = new DataRule();
		dataRule.setId(id);
		assert SecurityUtils.getUser() != null;
		dataRule.setModifiedUser(SecurityUtils.getUser().getId());
		dataRule.setModifiedTime(LocalDateTime.now());
		dataRule.setIsDeleted(DataCleanConstant.YES);
		return this.updateById(dataRule);
	}

	@Override
	public Boolean deleteByIds(Set<Long> ids) {

		DataRule dataRule = new DataRule();
		assert SecurityUtils.getUser() != null;
		dataRule.setModifiedUser(SecurityUtils.getUser().getId());
		dataRule.setModifiedTime(LocalDateTime.now());
		dataRule.setIsDeleted(DataCleanConstant.YES);
		this.update(dataRule, new EntityWrapper<DataRule>().in("id", ids));
		return true;
	}

	@Override
	public Boolean save(DataRuleVo dataRuleVo) {
		// 组装entity
		DataRule dataRule = new DataRule();
		dataRule.setName(dataRuleVo.getName());
		// 赋予用户信息
		dataRule.setCreateTime(LocalDateTime.now());
		dataRule.setModifiedTime(LocalDateTime.now());
		assert SecurityUtils.getUser() != null;
		dataRule.setCreateUser(SecurityUtils.getUser().getId());
		dataRule.setDeptId(SecurityUtils.getUser().getDeptId());
		return this.insert(dataRule);
	}

}
