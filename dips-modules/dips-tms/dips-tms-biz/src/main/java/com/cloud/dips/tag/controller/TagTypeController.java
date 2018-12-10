package com.cloud.dips.tag.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.tag.api.dto.GovTagTypeDTO;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.entity.GovTagType;
import com.cloud.dips.tag.api.vo.GovTagTypeVO;
import com.cloud.dips.tag.service.GovTagService;
import com.cloud.dips.tag.service.GovTagTypeService;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author ZB
 *
 */
@RestController
@RequestMapping("/tag_type")
public class TagTypeController {
	@Autowired
	private GovTagTypeService service;
	@Autowired
	private GovTagService govTagService;
	
	
	@RequestMapping("/list")
	@ApiOperation(value = "标签分类集合", notes = "标签分类集合",httpMethod="GET")
	public List<GovTagType> tagTypeList() {
		EntityWrapper<GovTagType> e=new EntityWrapper<GovTagType>();
		return service.selectList(e);
	}
	
	@RequestMapping("/parents")
	@ApiOperation(value = "标签父分类集合", notes = "标签父分类集合",httpMethod="GET")
	public List<GovTagTypeVO> tagTypeParents() {
		return service.selectParents();
	}

	@RequestMapping("/page")
	@ApiOperation(value = "分页查询标签分类", notes = "标签分类集合",httpMethod="GET")
	public Page<GovTagTypeVO> tagTypePage(@RequestParam Map<String, Object> params) {
		String orderByField = "orderByField";
		if(StrUtil.isBlank(params.getOrDefault(orderByField, "").toString())){
			params.put(orderByField, "id");
		}
		return service.selectTypeVoPage(new Query<GovTagTypeVO>(params));
	}
	
	

	@SysLog("删除标签分类")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('gov_tagType_del')")
	@ApiOperation(value = "删除标签分类", notes = "删除标签分类",httpMethod="DELETE")
	public R<Boolean> tagTypeDel(@PathVariable Integer id) {
		GovTagType govTagType = service.selectById(id);
		if(govTagType==null){
			return new R<>(false);
		}else{
				EntityWrapper<GovTag> e=new EntityWrapper<GovTag>();
				e.where( "type_id = {0}", govTagType.getTypeId());
				govTagService.updateForSet("type_id=0", e);
				EntityWrapper<GovTagType> e2=new EntityWrapper<GovTagType>();
				e2.where( "parent_id = {0}", govTagType.getTypeId());
				service.updateForSet("parent_id="+govTagType.getParentId(), e2);
				return new R<>(service.deleteById(govTagType.getTypeId()));
		}
	}
	
	@SysLog("添加标签分类")
	@PostMapping("/create")
	@PreAuthorize("@pms.hasPermission('gov_tagType_add')")
	@ApiOperation(value = "添加标签分类", notes = "添加标签分类", httpMethod = "POST")
	public R<Boolean> saveTagType(@RequestBody GovTagTypeDTO govTagTypeDto) {
			GovTagType govTagType = new GovTagType();
			BeanUtils.copyProperties(govTagTypeDto, govTagType);
			return new R<>(service.insert(govTagType));
	}
	
	@SysLog("更新标签分类")
	@PostMapping("/update")
	@PreAuthorize("@pms.hasPermission('gov_tagType_edit')")
	@ApiOperation(value = "更新标签分类", notes = "更新标签分类", httpMethod = "POST")
	public R<Boolean> updateTagType(@RequestBody GovTagTypeDTO govTagTypeDto) {
		GovTagType govTagType = service.selectById(govTagTypeDto.getTypeId());
		BeanUtils.copyProperties(govTagTypeDto, govTagType);
		return new R<>(service.updateAllColumnById(govTagType));
	}
	
}
