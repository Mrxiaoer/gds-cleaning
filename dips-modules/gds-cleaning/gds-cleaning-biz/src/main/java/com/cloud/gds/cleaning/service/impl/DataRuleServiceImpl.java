package com.cloud.gds.cleaning.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.DataRulePageVo;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.mapper.DataRuleMapper;
import com.cloud.gds.cleaning.service.DataRuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author : lolilijve
 * @Email : 1042703214@qq.com
 * @Date : 2018-11-22
 */
@Service("dataFieldService")
public class DataRuleServiceImpl extends ServiceImpl<DataRuleMapper, DataRule> implements DataRuleService {


	@Override
	public List<DataRule> selectAll() {
		DataRule dataRule = new DataRule();
		dataRule.setIsDeleted(DataCleanConstant.NO);
		dataRule.setDeptId(1);
		dataRule.setDeptId(SecurityUtils.getUser().getDeptId());
		List<DataRule> dataRules = this.selectList(new EntityWrapper<>(dataRule));
		return dataRules;
	}

	@Override
	public Boolean deleteById(Long id) {
		DataRule dataRule = new DataRule();
		dataRule.setId(id);
		dataRule.setModifiedUser(SecurityUtils.getUser().getId());
		dataRule.setModifiedTime(LocalDateTime.now());
		dataRule.setIsDeleted(DataCleanConstant.YES);
		return this.updateById(dataRule);
	}

	@Override
	public Boolean deleteByIds(Set<Long> ids) {
		for (Long id : ids){
			this.deleteById(id);
		}
		return true;
	}

	@Override
	public Boolean save(DataRuleVo dataRuleVo) {
		// 组装entity
		DataRule dataRule = new DataRule();
		dataRule.setName(dataRuleVo.getName());
		// 赋予用户信息
		dataRule.setCreateTime(LocalDateTime.now());
		dataRule.setCreateUser(SecurityUtils.getUser().getId());
		dataRule.setDeptId(SecurityUtils.getUser().getDeptId());
		return this.insert(dataRule);
	}

}
