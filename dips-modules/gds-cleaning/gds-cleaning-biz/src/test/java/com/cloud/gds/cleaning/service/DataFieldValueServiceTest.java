package com.cloud.gds.cleaning.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.dto.DataPoolAnalysis;
import com.cloud.gds.cleaning.api.entity.AnalysisResult;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.vo.*;
import com.cloud.gds.cleaning.mapper.DataFieldValueMapper;
import com.cloud.gds.cleaning.utils.CommonUtils;
import com.cloud.gds.cleaning.utils.DataPoolUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.text.MaskFormatter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataFieldValueServiceTest {

    @Autowired
    DataFieldValueService dataFieldValueService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void entity2Vo() {
        DataFieldValue entity = dataFieldValueService.selectById(2);
        DataPoolVo vo = DataPoolUtils.entity2Vo(entity);
        logger.info("vo", vo);
    }

    @Test
    public void entity2VoIsEmpty() {
        DataFieldValue entity = new DataFieldValue();
        entity.setFieldValue("");
        entity.setFieldId(2L);
        DataPoolVo vo = DataPoolUtils.entity2Vo(entity);
        logger.info("vo", vo);
    }

    @Test
    public void vo2Entity() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "小二");
        map.put("age", 9);
        map.put("length", 20);
        map.put("city", "杭州");

        JSONObject myjson = new JSONObject(map);

        DataPoolVo vo = new DataPoolVo();
        vo.setFieldValue(myjson);
        vo.setFieldId(2L);

        DataFieldValue entity = DataPoolUtils.vo2Entity(vo);
        dataFieldValueService.insert(entity);
        logger.info("entity_id", entity.getId());

    }

    @Test
    public void listEntity2Vo() {
        List<DataFieldValue> entitys = dataFieldValueService.selectList(new EntityWrapper<>());
        List<DataPoolVo> vos = DataPoolUtils.listEntity2Vo(entitys);
        logger.info("vos", vos);
    }

    @Test
    public void deleteByIds() {
        Set<Long> ids = new HashSet<>();
        ids.add(3L);
        ids.add(4L);
        dataFieldValueService.deleteByIds(ids);

    }

    @Test
    public void compareDifference() {
        List<DataFieldValueTree> compareResult = dataFieldValueService.compareDifference(2L);
        System.out.println(compareResult);
    }

    @Test
	public void selectPage(){
    	Map<String,Object> params = new HashMap<>();
    	params.put("page", 1);
    	params.put("limit", 10);
    	params.put("fieldId", 3);

		CommonUtils.PiPei pp = CommonUtils.createPP();
		List<String> list = new ArrayList<>();
		list.add("fieldId");
		pp.setEq(list);
//		list.clear();
//		list.add("");
//		pp.setLike(list);
//		Wrapper<DataFieldValue> entityWrapper = CommonUtils.pagePart(params,pp,new DataFieldValue());

//		Page page = dataFieldValueService.pagePo2Vo(dataFieldValueService.selectPage(new Query<>(CommonUtils.map2map(params)),entityWrapper));
	}

	@Test
	public void saveAll(){
    	List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>();
    	Map<String,Object> map = new HashMap<>();
    	map.put("city", "舟山");
    	map.put("name", "王小二");
    	map.put("age",24 );
    	maps.add(map);
    	maps.add(map);
    	dataFieldValueService.saveAll(2L,maps );
	}


	@Autowired
	AnalysisResultService analysisResultService;

	@Test
	public void listJson(){
    	String str = "[{\"id\":1,\"group\":[{\"id\":1,\"similarity\":1},{\"id\":2,\"similarity\":0.709008778084403}]},{\"id\":1525,\"group\":[{\"id\":1525,\"similarity\":1},{\"id\":1526,\"similarity\":0.7328392218049825},{\"id\":1601,\"similarity\":0.795443077662214},{\"id\":1611,\"similarity\":0.795443077662214},{\"id\":1725,\"similarity\":0.5546353317889164},{\"id\":2077,\"similarity\":0.691628594788375},{\"id\":2078,\"similarity\":0.6820620141175615},{\"id\":2079,\"similarity\":0.6858237655151436},{\"id\":2081,\"similarity\":0.5946353317889165},{\"id\":2657,\"similarity\":0.82}]},{\"id\":2667,\"group\":[{\"id\":2667,\"similarity\":1},{\"id\":2668,\"similarity\":0.9},{\"id\":2669,\"similarity\":0.7},{\"id\":2670,\"similarity\":0.7499999999999999}]}]";
		List<ResultJsonVo> list = JSON.parseArray(str, ResultJsonVo.class);
		System.out.println(list);
		Long fileId = 1L;
		List<AnalysisResult> analysisResults = new ArrayList<>();
		for (ResultJsonVo jsonVo : list) {
			for (GroupVo vo : jsonVo.getGroup()) {
				AnalysisResult result = new AnalysisResult();
				result.setFieldId(fileId);
				result.setBaseId(jsonVo.getId());
				result.setCompareId(vo.getId());
				result.setSimilarity(vo.getSimilarity());
				analysisResults.add(result);
			}
		}
		analysisResultService.insertBatch(analysisResults);
	}

	@Autowired
	DataFieldValueMapper dataFieldValueMapper;

	@Test
	public void selectDataPool(){
		Long id = 11L;
		List<DataPoolAnalysis> list = dataFieldValueMapper.selectDataPool(id);
		System.out.println(list);
	}

	@Test
	public void contrastBeforePage(){
		Map<String, Object> map = new HashMap<>();
		map.put("fieldId", 4L);
		dataFieldValueService.contrastBeforePage(map);
	}

	@Test
	public void gainCleanData(){

		Long fieldId = 1L;
		dataFieldValueService.gainCleanData(fieldId);
	}

	@Test
	public void centerToSatellite(){
		Long fieldId = 11L;
		List<DARVo> darVos = dataFieldValueService.centerToSatellite(fieldId);
		System.out.println(darVos);
	}

	@Test
	public void cleaningItem(){
		Long beCleanedId = 12L;
		List<CleanItem> list=dataFieldValueService.cleaningItem(beCleanedId);
		System.out.println(list);

	}
}
