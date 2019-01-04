package com.cloud.gds.cleaning.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.api.vo.DataSetVo;
import com.cloud.gds.cleaning.utils.DataRuleUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataRuleServiceTest {

	@Autowired
	DataRuleService dataRuleService;

	@Test
	public void deleteById() {
		System.out.println(dataRuleService.deleteById(7L));
	}

	@Test
	public void deleteByIds() {
		Set<Long> ids = new HashSet<>();
		ids.add(1L);
		ids.add(2L);
		ids.add(3L);
		dataRuleService.deleteByIds(ids);

	}

	@Test
	public void save() {
		DataRuleVo dataRuleVo = new DataRuleVo();
		dataRuleVo.setName("小二规则1214");
		dataRuleService.save(dataRuleVo);
	}


	@Test
	public void check(){
		DataRule one = dataRuleService.selectById(4);
		DataRule two = dataRuleService.selectById(5);
		DataRuleVo oneVo = DataRuleUtils.po2Vo(one);
		DataRuleVo twoVo = DataRuleUtils.po2Vo(two);
		List<DataSetVo> listone = oneVo.getDetail();
		List<DataSetVo> listtwo = twoVo.getDetail();
		SortedMap<String,String> sorteOne = changeSortedMap(listone);
		SortedMap<String,String> sorteTwo = changeSortedMap(listtwo);
		Boolean flag = checkSortedMap(sorteTwo,sorteOne);
		System.out.println(flag);

	}

	public static SortedMap<String,String> changeSortedMap(List<DataSetVo> dataSetVos){
		SortedMap<String,String> sortedMap = new TreeMap<>();
		for (DataSetVo vo : dataSetVos){
			if (vo.getLabel() != "" || vo.getLabel().trim()!=""){
				sortedMap.put(vo.getLabel(), vo.getLabel());
			}
		}
		return sortedMap;
	}

	public static Boolean checkSortedMap(SortedMap<String,String> one,SortedMap<String,String> two){
		Set<Map.Entry<String, String>> es = one.entrySet();
		Iterator<Map.Entry<String,String>> it = es.iterator();
		while (it.hasNext()){
			Map.Entry<String,String> entry =  (Map.Entry<String,String>)it.next();
			String k = (String)entry.getKey();
			if (two.containsKey(k)){
				two.remove(k);
			}else {
				return false;
			}
		}
		if (two.size() > 0){
			return false;
		}
		return true;
	}

	@Test
	public void queryPage(){
		Map<String,Object>  map = new HashMap<>();
		map.put("page", 1);
		map.put("limit", 10);
		map.put("isAsc", true);
//		map.put("name", "小二");
		Page page = dataRuleService.queryPage(map);
		System.out.println(page);

	}

	@Test
	public void gainUpperPower(){

		Long ruleId = 8L;
		DataSetVo dataSetVo = dataRuleService.gainUpperPower(ruleId);
		System.out.println(dataSetVo);

	}
}
