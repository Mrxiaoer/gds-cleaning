package com.cloud.gds.gmsanalyse.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.SpecialStringUtil;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.gmsanalyse.constant.AnalyseConstant;
import com.cloud.gds.gmsanalyse.dto.GovPolicyDto;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyse;
import com.cloud.gds.gmsanalyse.mapper.PolicyAnalyseMapper;
import com.cloud.gds.gmsanalyse.service.PolicyAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 政策分析
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-03
 */
@Service
public class PolicyAnalyseServiceImpl extends ServiceImpl<PolicyAnalyseMapper, PolicyAnalyse> implements PolicyAnalyseService {

	private final PolicyAnalyseMapper policyAnalyseMapper;

	@Autowired
	public PolicyAnalyseServiceImpl(PolicyAnalyseMapper policyAnalyseMapper) {
		this.policyAnalyseMapper = policyAnalyseMapper;
	}

	@Override
	public Page queryPage(Map<String, Object> params) {
		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<PolicyAnalyse> p = new Page<PolicyAnalyse>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<PolicyAnalyse> e = new EntityWrapper<PolicyAnalyse>();
		String analyseName = params.getOrDefault("analyseName", "").toString();
		if (StrUtil.isNotBlank(analyseName)) {
			e.like("analyse_name", SpecialStringUtil.escapeExprSpecialWord(analyseName));
		}
		e.eq("is_deleted", AnalyseConstant.FALSE);
		// 用户只能查询自己部门的规则
		assert SecurityUtils.getUser() != null;
		e.eq("create_user", SecurityUtils.getUser().getId());
		return selectPage(p, e);
	}

	@Override
	public PolicyAnalyse save(GovPolicyDto govPolicyDto) {
		PolicyAnalyse policyAnalyse = new PolicyAnalyse();
		policyAnalyse.setAnalyseName(govPolicyDto.getAnalyseName());
		policyAnalyse.setFeatureNum(govPolicyDto.getFeatureNum());
		// 政策分析刚添加的时候政策默认处于正在分析的状态中,数据库默认值是0
//		assert SecurityUtils.getUser() != null;
//		policyAnalyse.setCreateUser(SecurityUtils.getUser().getId());
		insert(policyAnalyse);
		return policyAnalyse;
	}

	@Override
	public boolean individuationUpdate(PolicyAnalyse policyAnalyse) {
		return SqlHelper.retBool(policyAnalyseMapper.updateById(policyAnalyse));
	}

	@Override
	public boolean queryDelete(Long id) {
		return policyAnalyseMapper.queryDelete(id);
	}

}
