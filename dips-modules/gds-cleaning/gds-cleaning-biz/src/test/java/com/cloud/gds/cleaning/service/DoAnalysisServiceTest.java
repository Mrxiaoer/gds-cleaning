package com.cloud.gds.cleaning.service;

import com.cloud.gds.cleaning.GdsCleaningApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DoAnalysisServiceTest {

	@Autowired
	DoAnalysisService doAnalysisService;

	@Test
	public void handOutAnalysis() {
		doAnalysisService.handOutAnalysis(1L,0.8F,10);
	}

	@Test
	public void getAllNeedAnalysisDataFile() {
	}

}
