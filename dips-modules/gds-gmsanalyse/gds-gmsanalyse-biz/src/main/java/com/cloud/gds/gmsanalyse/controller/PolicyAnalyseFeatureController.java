package com.cloud.gds.gmsanalyse.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.gmsanalyse.service.PolicyAnalyseFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 特征controller
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-16
 */
@RestController
@RequestMapping("/feature")
public class PolicyAnalyseFeatureController {

	private final PolicyAnalyseFeatureService policyAnalyseFeatureService;


	@Autowired
	public PolicyAnalyseFeatureController(PolicyAnalyseFeatureService policyAnalyseFeatureService) {
		this.policyAnalyseFeatureService = policyAnalyseFeatureService;
	}

	@GetMapping("/info")
	public R info(@RequestParam Long analyseId){
		return new R<>(policyAnalyseFeatureService.info(analyseId));
	}
}
