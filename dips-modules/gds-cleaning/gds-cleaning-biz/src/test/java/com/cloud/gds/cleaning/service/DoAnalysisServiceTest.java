package com.cloud.gds.cleaning.service;

import com.cloud.gds.cleaning.GdsCleaningApplication;
import hammerlab.iterator.map;
import java.util.HashMap;
import java.util.Map;
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

	@Test
	public void exactlySame(){
		Map<Long,String> map = new HashMap<>();
		map.put(1L,"a");
		map.put(2L,"b");
		map.put(3L,"a");
		map.put(4L,"c");
		map.put(5L,"ab");
		map.put(6L,"d");
		map.put(7L,"ab");
		System.out.println(doAnalysisService.exactlySame(map));
	}

	@Test
	public void getExactlySame(){
		System.out.println(doAnalysisService.getExactlySameDataIds(96L));
	}

}
