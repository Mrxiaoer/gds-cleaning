package com.cloud.gds.preprocessing.service;

import com.cloud.gds.preprocessing.GdsPreprocessingApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = GdsPreprocessingApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataDisposeServiceTest {

	@Autowired
	private DataDisposeService service;

	@Test
	public void dataTransfer() {
		boolean b = service.dataMigrationSurface(10000L);
		System.out.println("dataTransfer over");
	}
}
