package com.cloud.dips.tag.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.tag.api.entity.GovTagFunction;
import com.cloud.dips.tag.service.GovTagFunctionService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author ZB
 *
 */
@RestController
@RequestMapping("/function")
public class TagFunctionController {
	@Autowired
	private GovTagFunctionService service;
	
	@RequestMapping("/list")
	@ApiOperation(value = "标签功能集合", notes = "标签功能集合",httpMethod="GET")
	public List<GovTagFunction> list() {
		return service.selectList(new EntityWrapper<GovTagFunction>());
	}

	@RequestMapping("/map")
	@ApiOperation(value = "标签功能集合", notes = "标签功能集合",httpMethod="GET")
	public Map<String, Boolean> getMap() {
		EntityWrapper<GovTagFunction> e=new EntityWrapper<GovTagFunction>();
		List<GovTagFunction> list = service.selectList(e);
		Map<String,Boolean> map=new HashMap<String,Boolean>(0);
		for(GovTagFunction bean:list){
			if(bean.getEnable()==0){
				map.put(bean.getNumber(), Boolean.FALSE);
			}else{
				map.put(bean.getNumber(), Boolean.TRUE);
			}
		}
		return map;
	}
	
	@SysLog("删除标签功能")
	@PostMapping("/delete/{id}")
	@ApiOperation(value = "删除标签功能", notes = "删除标签功能",httpMethod="POST")
	public R<Boolean> tagFunctionDel(@PathVariable Integer id) {
		GovTagFunction bean = service.selectById(id);
		if(bean==null){
			return new R<>(false,"功能不存在！");
		}else{
			return new R<>(service.deleteById(bean.getId()));
		}
	}
	
	@SysLog("添加标签功能")
	@PostMapping("/create")
	@ApiOperation(value = "添加标签功能", notes = "添加标签功能", httpMethod = "POST")
	public R<Boolean> save(@Valid @RequestBody GovTagFunction govTagFunction) {
			GovTagFunction bean=new GovTagFunction();
			BeanUtils.copyProperties(govTagFunction, bean);
			return new R<>(service.insert(bean));
	}
	
	@SysLog("更新标签功能")
	@PostMapping("/update")
	@ApiOperation(value = "更新标签功能", notes = "更新标签功能", httpMethod = "POST")
	public R<Boolean> update(@Valid @RequestBody GovTagFunction govTagFunction) {
		GovTagFunction bean = service.selectById(govTagFunction.getId());
		if(bean==null){
			return new R<>(false,"功能不存在！");
		}else{
			BeanUtils.copyProperties(govTagFunction, bean);
			return new R<>(service.updateById(bean));	
		}
	}
	
	@RequestMapping("/update_enable/{id}")
	@ApiOperation(value = "更新标签功能是否启用", notes = "更新标签功能是否启用", httpMethod = "POST")
	public R<Boolean> updateEnable(@PathVariable Integer id) {
		GovTagFunction bean = service.selectById(id);
		if(bean==null){
			return new R<>(false,"功能不存在！");
		}else{
			Integer enable=1;
			if(bean.getEnable()==1){
				enable=0;
			}
			EntityWrapper<GovTagFunction> e=new EntityWrapper<GovTagFunction>();
			e.eq("id", bean.getId());
			return new R<>(service.updateForSet("enable = "+enable, e));	
		}
	}
	
}
