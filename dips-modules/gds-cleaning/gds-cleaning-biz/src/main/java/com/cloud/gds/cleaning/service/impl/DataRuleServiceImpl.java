package com.cloud.gds.cleaning.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.SpecialStringUtil;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.BaseVo;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.api.vo.DataSetVo;
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
@Service
public class DataRuleServiceImpl extends ServiceImpl<DataRuleMapper, DataRule> implements DataRuleService {

	private final DataFieldService dataFieldService;

	private final DataRuleMapper dataRuleMapper;

	@Autowired
	public DataRuleServiceImpl(DataFieldService dataFieldService, DataRuleMapper dataRuleMapper) {
		this.dataFieldService = dataFieldService;
		this.dataRuleMapper = dataRuleMapper;
	}

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
		if (StrUtil.isNotBlank(name)) {
			e.like("name", SpecialStringUtil.escapeExprSpecialWord(name));
		}
		e.eq("is_deleted", DataCleanConstant.FALSE);
		Page page = this.selectPage(p, e);
		if (page.getRecords() != null) {
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
	public Page queryRecycleBinPage(Map<String, Object> params) {
		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<DataRule> p = new Page<DataRule>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<DataRule> e = new EntityWrapper<DataRule>();
		String name = params.getOrDefault("name", "").toString();
		if (StrUtil.isNotBlank(name)) {
			e.like("name", SpecialStringUtil.escapeExprSpecialWord(name));
		}
		e.eq("is_deleted", DataCleanConstant.TRUE);
		Page page = this.selectPage(p, e);
		if (page.getRecords() != null) {
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
	public boolean rulePoolReduction(Long id) {
		DataRule dataRule = new DataRule();
		dataRule.setId(id);
		assert SecurityUtils.getUser() != null;
		dataRule.setModifiedUser(SecurityUtils.getUser().getId());
		dataRule.setModifiedTime(LocalDateTime.now());
		dataRule.setIsDeleted(DataCleanConstant.FALSE);
		return updateById(dataRule);
	}

	@Override
	public DataRuleVo queryById(Long id) {
		return DataRuleUtils.po2Vo(this.selectById(id));
	}

	@Override
	public List<BaseVo> selectAll() {
		DataRule dataRule = new DataRule();
		dataRule.setIsDeleted(DataCleanConstant.FALSE);
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
		dataRule.setModifiedUser(SecurityUtils.getUser().getId());
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
		dataRule.setIsDeleted(DataCleanConstant.TRUE);
		return this.updateById(dataRule);
	}

	@Override
	public Boolean deleteByIds(Set<Long> ids) {

		DataRule dataRule = new DataRule();
		assert SecurityUtils.getUser() != null;
		dataRule.setModifiedUser(SecurityUtils.getUser().getId());
		dataRule.setModifiedTime(LocalDateTime.now());
		dataRule.setIsDeleted(DataCleanConstant.TRUE);
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

	@Override
	public DataSetVo gainUpperPower(Long ruleId) {

		// 获取规则相应信息
		DataRuleVo dataRuleVo = this.queryById(ruleId);
		// 取规则参数集合转model
		List<DataSetVo> list = dataRuleVo.getDetail();
		// 先赋第一个model作为基础数
		DataSetVo resultSet = (list != null && list.size() > 0) ? list.get(0) : new DataSetVo();
		// 如何判断那些是同义,那些没有
		if (list != null && list.size() > 0) {
			for (DataSetVo dataSetVo : list) {
				// 判断基础数是否有同义,基础数据无同义
				if (resultSet.getIsSynonymous().equals(DataCleanConstant.FALSE)) {
					// 对比model存在同义
					if (dataSetVo.getIsSynonymous().equals(DataCleanConstant.TRUE)) {
						BeanUtils.copyProperties(dataSetVo, resultSet);
					} else {
						if (dataSetVo.getWeight() > resultSet.getWeight()) {
							BeanUtils.copyProperties(dataSetVo, resultSet);
						}
					}
				} else {
					// 如果对比前后数据都存在同义
					if (dataSetVo.getIsSynonymous().equals(DataCleanConstant.TRUE)) {
						if (dataSetVo.getWeight() > resultSet.getWeight()) {
							BeanUtils.copyProperties(dataSetVo, resultSet);
						}
					}
				}
			}
		}
		return resultSet;
	}

	@Override
	public boolean rulePoolDelete(Long id) {
		return SqlHelper.retBool(dataRuleMapper.deleteById(id));
	}

	@Override
	public boolean rulePoolDeletes(Set<Long> ids) {
		return SqlHelper.retBool(dataRuleMapper.recyclingBinClear(ids));
	}

}
