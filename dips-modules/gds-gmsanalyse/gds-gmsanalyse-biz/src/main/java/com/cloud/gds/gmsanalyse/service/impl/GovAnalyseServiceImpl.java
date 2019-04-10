package com.cloud.gds.gmsanalyse.service.impl;

import com.cloud.gds.gmsanalyse.dto.GovPolicyDto;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyse;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyseFeature;
import com.cloud.gds.gmsanalyse.service.AnalyseAlgorithmService;
import com.cloud.gds.gmsanalyse.service.GovAnalyseService;
import com.cloud.gds.gmsanalyse.service.PolicyAnalyseFeatureService;
import com.cloud.gds.gmsanalyse.service.PolicyAnalyseService;
import com.cloud.gds.gmsanalyse.vo.Analyse;
import com.cloud.gds.gmsanalyse.vo.Child;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-04
 */
@Service
public class GovAnalyseServiceImpl implements GovAnalyseService {

	private final PolicyAnalyseService policyAnalyseService;
	private final PolicyAnalyseFeatureService policyAnalyseFeatureService;
	private final AnalyseAlgorithmService analyseAlgorithmService;

	@Autowired
	public GovAnalyseServiceImpl(PolicyAnalyseService policyAnalyseService, PolicyAnalyseFeatureService policyAnalyseFeatureService, AnalyseAlgorithmService analyseAlgorithmService) {
		this.policyAnalyseService = policyAnalyseService;
		this.policyAnalyseFeatureService = policyAnalyseFeatureService;
		this.analyseAlgorithmService = analyseAlgorithmService;
	}

	@Override
	public boolean govAnalyse(GovPolicyDto govPolicyDto) {
		// 保存分析信息
		PolicyAnalyse policyAnalyse = policyAnalyseService.save(govPolicyDto);
		// 政策分析
		String result = analyseAlgorithmService.govAnalysis(govPolicyDto.getOriginalList());

		// 分析状态进行处理 TODO 2019-4-9 16:33:46
		if (result != null) {
			List<PolicyAnalyseFeature> list = spellData(policyAnalyse.getId(), result);
		}


		return false;
	}

	private List<PolicyAnalyseFeature> spellData(Long id, String result) {
		List<PolicyAnalyseFeature> list = new ArrayList<>();

		Gson gson = new Gson();
		Analyse analyse = gson.fromJson(result, Analyse.class);

		for (Child child : analyse.getList()) {
			PolicyAnalyseFeature feature = new PolicyAnalyseFeature();
			feature.setFeatureNum(child.getSum());
			feature.setFeatureName(child.getName());
			feature.setAnalyseId(id);
			list.add(feature);
		}
		return list;
	}
}
