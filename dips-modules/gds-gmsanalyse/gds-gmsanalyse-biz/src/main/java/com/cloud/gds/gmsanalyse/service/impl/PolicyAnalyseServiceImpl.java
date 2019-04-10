package com.cloud.gds.gmsanalyse.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.gds.gmsanalyse.dto.GovPolicyDto;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyse;
import com.cloud.gds.gmsanalyse.mapper.PolicyAnalyseMapper;
import com.cloud.gds.gmsanalyse.service.PolicyAnalyseService;
import org.springframework.stereotype.Service;

/**
 * 政策分析
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-03
 */
@Service
public class PolicyAnalyseServiceImpl extends ServiceImpl<PolicyAnalyseMapper, PolicyAnalyse> implements PolicyAnalyseService {

	@Override
	public PolicyAnalyse save(GovPolicyDto govPolicyDto) {
		PolicyAnalyse policyAnalyse = new PolicyAnalyse();
		policyAnalyse.setAnalyseName(govPolicyDto.getAnalyseName());
		policyAnalyse.setFeatureNum(govPolicyDto.getFeatureNum());
		policyAnalyse.setOriginalList(govPolicyDto.getOriginalList().toString());

		//  todo 2019-4-4 13:54:10
		policyAnalyse.setCreateUser(0L);
		insert(policyAnalyse);
		return policyAnalyse;
	}

}
