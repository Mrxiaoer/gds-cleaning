package com.cloud.gds.gmsanalyse.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyseFeature;
import com.cloud.gds.gmsanalyse.mapper.PolicyAnalyseFeatureMapper;
import com.cloud.gds.gmsanalyse.service.PolicyAnalyseFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 政策分析特征
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-04
 */
@Service
public class PolicyAnalyseFeatureServiceImpl extends ServiceImpl<PolicyAnalyseFeatureMapper, PolicyAnalyseFeature> implements PolicyAnalyseFeatureService {

	private final PolicyAnalyseFeatureMapper mapper;

	@Autowired
	public PolicyAnalyseFeatureServiceImpl(PolicyAnalyseFeatureMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public boolean batchInsert(List<PolicyAnalyseFeature> list) {
		return mapper.batchInsert(list);
	}
}
