package com.cloud.gds.cleaning.mapper;

import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.dto.DataPoolAnalysis;
import com.cloud.gds.cleaning.api.vo.CenterData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	@Test
	public void centerFiltration() {
		Long id = 11L;
		Float screenSize = 0.8f;
		List<DataPoolAnalysis> list = dataFieldValueMapper.centerFiltration(id, screenSize);
		System.out.println(list);
	}

	@Test
	public void reductionByIds() {
		Set<Long> ids = new HashSet<>();
		ids.add(1003467L);
		ids.add(1003466L);
		Integer userId = 3;
		System.out.println(dataFieldValueMapper.reductionByIds(ids, userId));
	}

	@Test
	public void oneKeyReduction() {
		Long fieldId = 100L;
		Integer userId = 1;
		System.out.println(dataFieldValueMapper.oneKeyReduction(fieldId, userId));
	}

}
