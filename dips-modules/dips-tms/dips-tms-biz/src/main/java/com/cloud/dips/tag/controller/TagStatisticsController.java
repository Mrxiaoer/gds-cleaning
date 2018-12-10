package com.cloud.dips.tag.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.entity.GovTagRelation;
import com.cloud.dips.tag.api.vo.MapVO;
import com.cloud.dips.tag.service.GovTagRelationService;
import com.cloud.dips.tag.service.GovTagService;

import io.swagger.annotations.ApiOperation;

/**
 * 标签统计
 * @author ZB
 *
 */
@RestController
@RequestMapping("/statistics")
public class TagStatisticsController {
	@Autowired
	private GovTagService service;
	
	@Autowired
	private GovTagRelationService relatioService;
	
	/**
	 * 标签统计
	 * @return 总数
	 */
	@GetMapping("/count")
	@ApiOperation(value = "统计标签总数", notes = "统计标签总数",httpMethod="GET")
	public Map<Object, Object> tagCount() {
		Map<Object,Object> map=new HashMap<Object,Object>(0);
		EntityWrapper<GovTagRelation> re=new EntityWrapper<GovTagRelation>();
		re.groupBy("node");
		re.setSqlSelect("count(*)");
		List<Object> lr=relatioService.selectObjs(re);
		map.put("app", lr.size());
		EntityWrapper<GovTag> e=new EntityWrapper<GovTag>();
		map.put("sum", service.selectCount(e));
		Date now=new Date();
		Date week=new DateTime(now.getTime()).minusDays(7).withMillisOfDay(0).toDate();
		e.ge("create_time", week);
		map.put("newweek", service.selectCount(e));
		return map;
	}
	
	@GetMapping("/weeknew")
	@ApiOperation(value = "近期新增标签", notes = "近期新增标签",httpMethod="GET")
	public List<Map<String, Object>> tagList(@RequestParam(value="day",required=false) Integer day) {
		EntityWrapper<GovTag> e=new EntityWrapper<GovTag>();
		if(day==null){
			day=7;
		}
		Date now=new Date();
		Date week=new DateTime(now.getTime()).minusDays(day).withMillisOfDay(0).toDate();
		e.ge("create_time", week);
		e.setSqlSelect("id,name");
		e.last("limit 20");
		return service.selectMaps(e);
	}
	
	@GetMapping("/distributed")
	@ApiOperation(value = "标签按分类统计", notes = "标签按分类统计",httpMethod="GET")
	public List<Map<String, Object>> coutnByType() {
		List<MapVO> l=service.coutnByType();
		List<Map<String, Object>> lm=new ArrayList<Map<String, Object>>();
		for(MapVO os : l){
			if(os.getKey()!=null){
				Map<String, Object> map=new HashMap<String, Object>(0);
				map.put("item", os.getKey());
				map.put("count", os.getValue());
				lm.add(map);
			}
		}
		return lm;
	}
	
	@GetMapping("/daynew")
	@ApiOperation(value = "标签按日期统计", notes = "标签按日期统计",httpMethod="GET")
	public List<Map<String, Object>> coutnByDate(@RequestParam(value="day",required=false) Integer day) {
		if(day==null){
			day=7;
		}
		Date now=new Date();
		Date week=new DateTime(now.getTime()).minusDays(day).withMillisOfDay(0).toDate();
		List<MapVO> l=service.coutnByDate(week);
		List<Map<String, Object>> lm=new ArrayList<Map<String, Object>>();
		for(MapVO os : l){
			Map<String, Object> map=new HashMap<String, Object>(0);
			map.put("date", os.getKey());
			map.put("value", os.getValue());
			lm.add(map);	
		}
		return lm;
	}

	@GetMapping("/common")
	@ApiOperation(value = "常用标签", notes = "常用标签",httpMethod="GET")
	public List<Map<String, Object>> common(@RequestParam(value="limit",required=false) Integer limit) {
		EntityWrapper<GovTag> e=new EntityWrapper<GovTag>();
		if(limit==null){
			limit=10;
		}
		Collection<String> columns=new ArrayList<String>();
		columns.add("refers");
		e.orderDesc(columns);
		e.setSqlSelect("id,name");
		e.last("limit "+limit);
		return service.selectMaps(e);
	}
	
	@GetMapping("/relation/{id}")
	@ApiOperation(value = "标签应用详情", notes = "标签应用详情: params{标签ID: id}",httpMethod="GET")
	public List<Map<String, Object>> tagRefersShow(@PathVariable Integer id) {
		GovTag tag=service.selectById(id);
		if(tag==null){
			return null;
		}else{
			EntityWrapper<GovTagRelation> re=new EntityWrapper<GovTagRelation>();
			re.groupBy("node");
			re.eq("tag_id", tag.getTagId());
			re.setSqlSelect("node as name,count(*) as count");
			return relatioService.selectMaps(re);	
		}
	}
	
	
}