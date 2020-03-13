package com.cloud.gds.preprocessing.service;

import com.cloud.gds.preprocessing.GdsPreprocessingApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019/5/17
 */
@SpringBootTest(classes = GdsPreprocessingApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class InvalidPolicyServiceTest {
	@Autowired
	private InvalidPolicyService invalidPolicyService;

	@Test
	public void Test(){
		invalidPolicyService.cleanRealPolic();
	}

}
