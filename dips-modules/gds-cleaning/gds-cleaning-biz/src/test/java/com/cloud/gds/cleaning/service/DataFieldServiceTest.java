package com.cloud.gds.cleaning.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.api.vo.DataSetVo;
import com.cloud.gds.cleaning.utils.CommonUtils;
import com.cloud.gds.cleaning.utils.DataRuleUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataFieldServiceTest {

	@Autowired
	DataRuleService dataRuleService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void string2ListMap(){
		String string = "[{\"field_keys\":\"x\",\"weights\":\"20\",\"approximates\":\"0\"}," +
			"{\"field_keys\":\"y\",\"weights\":\"30\",\"approximates\":\"1\"}," +
			"{\"field_keys\":\"z\",\"weights\":\"50\",\"approximates\":\"0\"}]";
		List<DataSetVo> list = JSON.parseArray(string,DataSetVo.class);

		List<DataSetVo> listw = new ArrayList<>();
		for (Object object : list){
			Map<String,Object> ageMap = new HashMap<>();
			Map <String,Object> ret = (Map<String, Object>) object;
			/*for (Map.Entry<String, Object> entry : ret.entrySet()) {
            ageMap.put(entry.getKey());
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            listw.add(ageMap);  //添加到list集合  成为 list<map<String,Object>> 集合
        }*/
//			listw.add(ret);
			for (Map.Entry<String,Object> entry : ret.entrySet()) {
				System.out.print("key = " + entry.getKey());
			}
		}
		System.out.print(listw);
	}

	@Test
	public void po2Vo(){
		DataRule dataField = dataRuleService.selectById(4);
		DataRuleVo dataFieldVo = DataRuleUtils.po2Vo(dataField);
		System.out.print(dataFieldVo);
	}

	@Test
	public void selectPage(){
		Map<String,Object> params = new HashMap<>();

//		Iterator<Map.Entry<String, Object>> iter=params.entrySet().iterator();
//		if (iter.hasNext()) {
//			iter.next().;
//		}
		params.put("name","");
		EntityWrapper<DataRule> ew=new EntityWrapper<>();

		for(String paramKey:params.keySet()){
			String ParamValue = params.get(paramKey).toString();
			if(StrUtil.isNotBlank(ParamValue)){
				ew.like(paramKey, ParamValue);
			}
		}
		Page page = dataRuleService.selectPage(new Query<>(CommonUtils.map2map(params)),
			ew.eq("is_deleted", DataCleanConstant.NO));

		System.out.print(page);
	}

	@Test
	public void set(){
		Set<Long> ids = new HashSet<>();
		ids.add(1L);
		ids.add(2L);
		ids.add(3L);
		for (Long x : ids){
			System.out.print(x);
		}
	}


}
