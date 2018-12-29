package com.cloud.gds.cleaning.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.entity.AnalysisResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GdsCleaningApplication.class)
public class AnalysisResultServiceTest {

	@Autowired
	AnalysisResultService service;

	@Test
	public void delete(){
		Long fieldId = 2L;
		Boolean flag = service.delete(new EntityWrapper<AnalysisResult>().eq("field_id", fieldId));
		System.out.println(flag);
	}

}
