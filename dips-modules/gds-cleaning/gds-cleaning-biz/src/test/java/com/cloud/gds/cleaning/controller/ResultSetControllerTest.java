package com.cloud.gds.cleaning.controller;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.utils.CommonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ResultSetControllerTest {

	@Autowired
	DataFieldValueService dataFieldValueService;

	@Test
	public void page(){
		Map<String,Object> params = new HashMap<>();
		CommonUtils.PiPei pp = CommonUtils.createPP();
		List<String> eqList = new ArrayList<>();
//		eqList.add("fieldId");
//		eqList.add("remark");
//		pp.setEq(eqList);
//		List<String> likelist = new ArrayList<>();
//		likelist.add("");
//		pp.setLike(likelist);
		Wrapper<DataFieldValue> wrapper = CommonUtils.pagePart(params,pp,new DataFieldValue());
		Page page = dataFieldValueService.pagePo2Vo(dataFieldValueService.selectPage(new Query<>(CommonUtils.map2map(params)),wrapper.eq("be_cleaned_id", DataCleanConstant.NO)));

		System.out.println(page);

	}

}
