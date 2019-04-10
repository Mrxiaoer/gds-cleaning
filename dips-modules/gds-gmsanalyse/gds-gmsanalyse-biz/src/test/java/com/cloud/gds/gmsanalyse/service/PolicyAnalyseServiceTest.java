package com.cloud.gds.gmsanalyse.service;


import com.cloud.gds.gmsanalyse.GdsGmsAnalyseApplication;
import com.cloud.gds.gmsanalyse.dto.GovPolicyDto;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = GdsGmsAnalyseApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PolicyAnalyseServiceTest {


	@Autowired
	private PolicyAnalyseService policyAnalyseService;

	@Test
	public void save() {
		List<Long> ids = new ArrayList<>();
		ids.add(2L);
		ids.add(5L);
		ids.add(200L);
		GovPolicyDto govPolicyDto = new GovPolicyDto();
		govPolicyDto.setAnalyseName("政策名称");
		govPolicyDto.setFeatureNum(10);
		govPolicyDto.setOriginalList(ids);

		policyAnalyseService.save(govPolicyDto);

		System.out.println(111);

	}

}
