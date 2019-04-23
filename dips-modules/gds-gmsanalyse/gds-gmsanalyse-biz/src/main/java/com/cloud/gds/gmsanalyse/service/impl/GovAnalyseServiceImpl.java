package com.cloud.gds.gmsanalyse.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.gds.gms.api.fegin.RemoteGovPolicyGeneralService;
import com.cloud.gds.gmsanalyse.constant.AnalyseConstant;
import com.cloud.gds.gmsanalyse.bo.DeconstructionListBo;
import com.cloud.gds.gmsanalyse.dto.GovPolicyDto;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyse;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyseFeature;
import com.cloud.gds.gmsanalyse.service.*;
import com.cloud.gds.gmsanalyse.vo.Analyse;
import com.cloud.gds.gmsanalyse.vo.Child;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private final RemoteGovPolicyGeneralService remoteGovPolicyGeneralService;
	private final PolicyDeconstructionService policyDeconstructionService;

	@Autowired
	public GovAnalyseServiceImpl(PolicyAnalyseService policyAnalyseService, PolicyAnalyseFeatureService policyAnalyseFeatureService, AnalyseAlgorithmService analyseAlgorithmService, RemoteGovPolicyGeneralService remoteGovPolicyGeneralService, PolicyDeconstructionService policyDeconstructionService) {
		this.policyAnalyseService = policyAnalyseService;
		this.policyAnalyseFeatureService = policyAnalyseFeatureService;
		this.analyseAlgorithmService = analyseAlgorithmService;
		this.remoteGovPolicyGeneralService = remoteGovPolicyGeneralService;
		this.policyDeconstructionService = policyDeconstructionService;
	}

	@Override
	public void govAnalyse(GovPolicyDto govPolicyDto) throws IOException, ClassNotFoundException {
		// 保存分析信息
		PolicyAnalyse policyAnalyse = policyAnalyseService.save(govPolicyDto);
		// 调用gms模块获取查询条件的政策数据
		List<Long> ids = remoteGovPolicyGeneralService.gainList(govPolicyDto.getParams());
		// 获取需分析的东西 2个数组+1个map
		DeconstructionListBo bo = policyDeconstructionService.gainMaterials(ids);
		// TODO 2019-4-22 10:18:34 政策分析
//		String result = analyseAlgorithmService.govAnalysis(ids);
		String result = null;
		// 分析状态进行处理
		PolicyAnalyse analyse = new PolicyAnalyse();
		analyse.setId(policyAnalyse.getId());
		if (result != null) {
			// 分析成功
			updatePolicyAndFeature(policyAnalyse.getId(), result);
			analyse.setAnalyseState(AnalyseConstant.TRUE);
		} else {
			// 分析失败
			analyse.setAnalyseState(AnalyseConstant.TWO);
		}
		// 更新分析状态
		policyAnalyseService.updateById(analyse);
	}

	private boolean updatePolicyAndFeature(Long analyseId, String result) {
		Map<String, Object> map = spellData(analyseId, result);
		// 先更特征表
		List<PolicyAnalyseFeature> list = JSON.parseArray(String.valueOf(map.get("list")), PolicyAnalyseFeature.class);
		// 再更新分析表
		PolicyAnalyse updatePolicy = new PolicyAnalyse();
		updatePolicy.setId(analyseId);
		updatePolicy.setAnalyseState(AnalyseConstant.TRUE);
		updatePolicy.setAnalyseSummary(String.valueOf(map.get("summary")));
		return policyAnalyseFeatureService.batchInsert(list) && policyAnalyseService.updateById(updatePolicy);
	}

	private Map<String, Object> spellData(Long id, String result) {
		Map<String, Object> map = new HashMap<>();
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
		map.put("summary", analyse.getSummary());
		map.put("list", list);
		return map;
	}
}
