package com.cloud.dips.admin.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.admin.api.entity.SysDict;
import com.cloud.dips.admin.api.entity.SysDictValue;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.dips.admin.service.SysDictService;
import com.cloud.dips.admin.service.SysDictValueService;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;

import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 字典值表 前端控制器
 * </p>
 *
 * @author ZB
 */
@RestController
@RequestMapping("/dictValue")
public class DictValueController {
	@Autowired
	private SysDictService service;
	
	@Autowired
	private SysDictValueService valueService;
	
	
	@GetMapping("/{id}")
	@ApiOperation(value = "通过ID查询字典值信息", notes = "通过ID查询字典值信息",httpMethod="GET")
	public SysDictValue dictVale(@PathVariable Integer id) {
		return valueService.selectById(id);
	}

	@GetMapping("/dictValuePage")
	@ApiOperation(value = "分页查询字典值", notes = "分页查询字典值",httpMethod="GET")
	public Page<SysDictValue> dictValuePage(@RequestParam Map<String, Object> params) {
		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<SysDictValue> p=new Page<SysDictValue>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "sort").toString());
		p.setAsc(isAsc);
		EntityWrapper<SysDictValue> e=new EntityWrapper<SysDictValue>();
		return valueService.selectPage(p,e);
	}

	@RequestMapping("/dictValueParents/{dId}")
	@ApiOperation(value = "字典值一级分类集合", notes = "字典值一级分类集合: params{ID:字典id}",httpMethod="GET")
	public List<DictValueVO> dictValueParents(@PathVariable("dId") Integer id) {
			return valueService.selectDictValueVo(id);
	}

	@SysLog("删除字典值")
	@PostMapping("/delete/{id}")
	@ApiOperation(value = "删除字典值", notes = "根据ID删除字典值: params{字典值ID: id}",httpMethod="POST")
	public R<Boolean> dictValueDel(@PathVariable Integer id) {
		SysDictValue sysDictValue = valueService.selectById(id);
		if(sysDictValue==null){
			return new R<>(false);
		}else{
			if(sysDictValue.getParentId()==0){
				EntityWrapper<SysDictValue> e=new EntityWrapper<SysDictValue>();
				e.eq("parent_id", sysDictValue.getId());
				valueService.delete(e);
			}
			return new R<>(valueService.deleteById(sysDictValue.getId()));
		}
	}
	
	@SysLog("添加字典值")
	@PostMapping("/create")
	@ApiOperation(value = "添加字典值", notes = "添加字典值", httpMethod = "POST")
	public R<Boolean> addDictValue(@Valid @RequestBody SysDictValue sysDictValue) {
		SysDict sysDict=service.selectById(sysDictValue.getDictId());
		if(sysDict==null){
			return new R<>(Boolean.FALSE);
		}else{
			if(sysDictValue.getParentId()!=null){
				SysDictValue dy=valueService.selectById(sysDictValue.getParentId());
				if(dy==null || dy.getParentId()!=0){
					sysDictValue.setParentId(0);
				}
			}
			SysDictValue bean=new SysDictValue();
			BeanUtils.copyProperties(sysDictValue, bean);
			return new R<>(valueService.insert(bean));
		}	
	}
	
	@SysLog("更新字典值")
	@PostMapping("/update")
	@ApiOperation(value = "更新字典值", notes = "更新字典值", httpMethod = "POST")
	public R<Boolean> updateDictValue(@Valid @RequestBody SysDictValue sysDictValue) {
		SysDictValue bean=valueService.selectById(sysDictValue.getId());
		if(bean==null){
			return new R<>(Boolean.FALSE);
		}else{
			BeanUtils.copyProperties(sysDictValue, bean);
			bean.setUpdateTime(new Date());
			return new R<>(valueService.updateById(bean));
		}
	}	

}
