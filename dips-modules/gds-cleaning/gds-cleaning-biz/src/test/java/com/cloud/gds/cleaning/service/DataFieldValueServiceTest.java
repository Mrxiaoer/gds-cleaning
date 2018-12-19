package com.cloud.gds.cleaning.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.vo.DataFieldValueTree;
import com.cloud.gds.cleaning.api.vo.DataPoolVo;
import com.cloud.gds.cleaning.utils.CommonUtils;
import com.cloud.gds.cleaning.utils.DataPoolUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.text.MaskFormatter;
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
    public void pagePo2Vo() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        //		params.put("limit", 8);
        //		params.put("field_id", 2);
        System.out.print(params.get("fieldId"));
        Page<DataFieldValue> page = dataFieldValueService
                .selectPage(new Query<>(CommonUtils.map2map(params)), new EntityWrapper<DataFieldValue>());
        // po -> vo
        Page page2 = dataFieldValueService.pagePo2Vo(page);

        System.out.println(page2);
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
		Wrapper<DataFieldValue> entityWrapper = CommonUtils.pagePart(params,pp,new DataFieldValue());

		Page page = dataFieldValueService.pagePo2Vo(dataFieldValueService.selectPage(new Query<>(CommonUtils.map2map(params)),entityWrapper));
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

}
