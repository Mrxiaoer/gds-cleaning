package com.cloud.gds.preprocessing.mapper;

import com.cloud.gds.preprocessing.GdsPreprocessingApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = GdsPreprocessingApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class InvalidPolicyMapperTest {

	@Autowired
	private InvalidPolicyMapper mapper;

	@Test
	public void gain() {

		List<Long> ids = new ArrayList<>();
//		ids.add(513607L);
		List<Long> list = mapper.gainIssueId(3, 250);
		System.out.println(list.size());

	}
}
