package com.cloud.gds.cleaning.mapper;

import com.cloud.gds.cleaning.GdsCleaningApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AnalysisResultMapperTest {

	@Autowired
	AnalysisResultMapper mapper;

	@Test
	public void relevanceDelete() {
		Set<Long> ids = new HashSet<>();
		ids.add(1L);
		ids.add(2L);
		System.out.println(mapper.relevanceDelete(ids));
	}

	@Test
	public void recyclingBinClear(){

		Set<Long> ids = new HashSet<>();
		ids.add(90599L);
		ids.add(90602L);
		ids.add(90604L);
		System.out.println(mapper.relevanceDelete(ids));

	}
}
