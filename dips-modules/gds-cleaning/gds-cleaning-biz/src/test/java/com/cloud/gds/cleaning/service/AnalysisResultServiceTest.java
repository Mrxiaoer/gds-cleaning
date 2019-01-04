package com.cloud.gds.cleaning.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.entity.AnalysisResult;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GdsCleaningApplication.class)
public class AnalysisResultServiceTest {

	@Autowired
	AnalysisResultService service;
	@Autowired
	DataFieldValueService dataFieldValueService;

	@Test
	public void delete() {
		Long fieldId = 2L;
		Boolean flag = service.delete(new EntityWrapper<AnalysisResult>().eq("field_id", fieldId));
		System.out.println(flag);
	}

	@Test
	public void deleteAllById() {
		Long id = 77L;
		boolean flag = service.deleteAllById(id);
		System.out.println(flag);
	}

	@Test
	public void deleteAllByIds() {
		Set<Long> ids = new HashSet<>();
		ids.add(66L);
		ids.add(77L);
		boolean flag = service.deleteAllByIds(ids);
		System.out.println(flag);
	}

	@Test
	public void cleanData() {
		String string = "[{\"baseId\":1,\"cleanId\":3},{\"baseId\":1,\"cleanId\":4},{\"baseId\":2,\"cleanId\":3}]";
		JSONArray jsonArray = JSONArray.parseArray(string);
//		List<Map<String,Object>> list = JSONObject.parseArray(jsonArray, );
		System.out.println(jsonArray);
		List<DataFieldValue> list = new ArrayList<>();
		for (Object object : jsonArray) {
			System.out.println(object);
			Map<String, Object> map = (Map<String, Object>) JSONObject.parse(object.toString());
			System.out.println(map);
			DataFieldValue dataFieldValue = new DataFieldValue();
			dataFieldValue.setId(((Integer) map.get("cleanId")).longValue());
			dataFieldValue.setBeCleanedId(((Integer) map.get("baseId")).longValue());

			list.add(dataFieldValue);
		}
		dataFieldValueService.updateBatchById(list);
	}



	public void automaticCleaning() {


	}

}
