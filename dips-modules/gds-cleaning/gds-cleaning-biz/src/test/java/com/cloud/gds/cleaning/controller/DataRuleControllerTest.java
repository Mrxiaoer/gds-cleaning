package com.cloud.gds.cleaning.controller;

import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataRuleService;
import com.cloud.gds.cleaning.utils.DataRuleUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;


@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataRuleControllerTest {


	@Autowired
	DataRuleService dataRuleService;

	@Autowired
	DataFieldService dataFieldService;


	@Test
	public void update() {
		DataRuleVo dataRuleVo = new DataRuleVo();
		dataRuleVo.setId(4L);
		dataRuleVo.setName("小二测试20181221");
		DataRule dataRule = DataRuleUtils.vo2po(dataRuleVo);
//		dataRule.setModifiedUser(SecurityUtils.getUser().getId());
		dataRule.setModifiedTime(LocalDateTime.now());
		dataFieldService.updateMatrixFile(dataRuleVo.getDetail()== null ? 0 : dataRuleVo.getId());


	}
}
