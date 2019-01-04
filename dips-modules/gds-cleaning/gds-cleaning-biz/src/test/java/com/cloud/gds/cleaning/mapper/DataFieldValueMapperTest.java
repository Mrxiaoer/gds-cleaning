package com.cloud.gds.cleaning.mapper;

import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.vo.CenterData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataFieldValueMapperTest {


	@Autowired
	DataFieldValueMapper dataFieldValueMapper;

	@Test
	public void gainCleanData() {
		Long field = 1L;
		List<CenterData> centerData = dataFieldValueMapper.gainCleanData(field);
		System.out.println(centerData);
	}
}
