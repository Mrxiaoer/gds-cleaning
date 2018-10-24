package com.cloud.dips.tag.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.tag.dto.GovTagTypeDTO;
import com.cloud.dips.tag.entity.GovTag;
import com.cloud.dips.tag.entity.GovTagType;
import com.cloud.dips.tag.service.GovTagService;
import com.cloud.dips.tag.service.GovTagTypeService;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/tagType")
public class TagTypeController {
	@Autowired
	private GovTagTypeService service;
	@Autowired
	private GovTagService govTagService;
	
	
	@RequestMapping("/tagTypeList")
	@ApiOperation(value = "标签分类集合", notes = "标签分类集合",httpMethod="GET")
	public List<GovTagType> tagTypeList() {
		EntityWrapper<GovTagType> e=new EntityWrapper<GovTagType>();
		return service.selectList(e);
	}

	@RequestMapping("/tagTypePage")
	@ApiOperation(value = "分页查询标签分类", notes = "标签分类集合",httpMethod="GET")
	public Page<GovTagType> tagTypePage(@RequestParam Map<String, Object> params) {
		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<GovTagType> p=new Page<GovTagType>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "g_type_id").toString());
		p.setAsc(isAsc);
		EntityWrapper<GovTagType> e=new EntityWrapper<GovTagType>();
		String name=params.getOrDefault("typename", "").toString();
		if(StrUtil.isNotBlank(name)){
			name="%"+name+"%";
			e.where( "g_name like {0}", name);
		}
		return service.selectPage(p,e);
	}
	
	

	@SysLog("删除标签分类")
	@DeleteMapping("/{id}")
	//@PreAuthorize("@pms.hasPermission('gov_tagType_del')")
	@ApiOperation(value = "删除标签分类", notes = "删除标签分类",httpMethod="DELETE")
	public R<Boolean> tagTypeDel(@PathVariable Integer id) {
		GovTagType govTagType = service.selectById(id);
		if(govTagType==null){
			return new R<>(false);
		}else{
				EntityWrapper<GovTag> e=new EntityWrapper<GovTag>();
				e.where( "g_type_id = {0}", govTagType.getTypeId());
				govTagService.updateForSet("g_type_id=null", e);
				return new R<>(service.deleteById(govTagType.getTypeId()));
		}
	}
	
	@SysLog("添加标签分类")
	@PostMapping("/saveTagType")
	//@PreAuthorize("@pms.hasPermission('gov_tagType_add')")
	@ApiOperation(value = "添加标签分类", notes = "添加标签分类", httpMethod = "POST")
	public R<Boolean> saveTagType(@RequestBody GovTagTypeDTO govTagTypeDto) {
			GovTagType govTagType = new GovTagType();
			BeanUtils.copyProperties(govTagTypeDto, govTagType);
			govTagType.setCreationDate(new Date());
			return new R<>(service.insert(govTagType));
	}
	
	@SysLog("更新标签分类")
	@PutMapping("/updateTagType")
	//@PreAuthorize("@pms.hasPermission('gov_tagLevel_edit')")
	@ApiOperation(value = "更新标签分类", notes = "更新标签分类", httpMethod = "PUT")
	public R<Boolean> updateTagType(@RequestBody GovTagTypeDTO govTagTypeDto) {
		GovTagType govTagType = service.selectById(govTagTypeDto.getTypeId());
		BeanUtils.copyProperties(govTagTypeDto, govTagType);
		return new R<>(service.updateById(govTagType));
	}
	
}
