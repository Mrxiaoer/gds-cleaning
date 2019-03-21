package com.cloud.gds.preprocessing.mapper;

import com.cloud.gds.preprocessing.GdsPreprocessingApplication;
import com.cloud.gds.preprocessing.entity.ScrapyGovPolicyGeneral;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest(classes = GdsPreprocessingApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ScrapyGovPolicyGeneralMapperTest {

	@Autowired
	private ScrapyGovPolicyGeneralMapper mapper;

	@Test
	public void gainScrapyPolicy(){
		List<ScrapyGovPolicyGeneral> list = mapper.gainScrapyPolicy();
		System.out.println(list.size());
		System.out.println("查询成功咯~");
	}

}
