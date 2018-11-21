package com.cloud.dips.tag.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.cloud.dips.tag.api.dto.GovTagLevelDTO;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.entity.GovTagLevel;
import com.cloud.dips.tag.service.GovTagLevelService;
import com.cloud.dips.tag.service.GovTagService;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author ZB
 *
 */
@RestController
@RequestMapping("/tagLevel")
public class TagLevelController {
	@Autowired
	private GovTagLevelService service;
	@Autowired
	private GovTagService govTagService;
	
	@RequestMapping("/tagLevelList")
	@ApiOperation(value = "标签级别集合", notes = "标签级别集合",httpMethod="GET")
	public List<GovTagLevel> tagLevelList() {
		return service.selectList(new EntityWrapper<GovTagLevel>());
	}

	@RequestMapping("/tagLevelPage")
	@ApiOperation(value = "分页查询标签级别", notes = "标签级别集合",httpMethod="GET")
	public Page<GovTagLevel> tagLevelPage(@RequestParam Map<String, Object> params) {
		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<GovTagLevel> p=new Page<GovTagLevel>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<GovTagLevel> e=new EntityWrapper<GovTagLevel>();
		String name=params.getOrDefault("levelname", "").toString();
		if(StrUtil.isNotBlank(name)){
			name="%"+name+"%";
			e.where( "name like {0}", name);
		}
		return service.selectPage(p,e);
	}
	
	

	@SysLog("删除标签级别")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('gov_tagLevel_del')")
	@ApiOperation(value = "删除标签级别", notes = "删除标签级别",httpMethod="DELETE")
	public R<Boolean> tagLevelDel(@PathVariable Integer id) {
		GovTagLevel govTagLevel = service.selectById(id);
		if(govTagLevel==null){
			return new R<>(false);
		}else{
				EntityWrapper<GovTag> e=new EntityWrapper<GovTag>();
				e.where( "level_id = {0}", govTagLevel.getLevelId());
				govTagService.updateForSet("level_id=0", e);
				return new R<>(service.deleteById(govTagLevel.getLevelId()));
		}
	}
	
	@SysLog("添加标签级别")
	@PostMapping("/saveTagLevel")
	@PreAuthorize("@pms.hasPermission('gov_tagLevel_add')")
	@ApiOperation(value = "添加标签级别", notes = "添加标签级别", httpMethod = "POST")
	public R<Boolean> saveTagLevel(@RequestBody GovTagLevelDTO govTagLevelDto) {
			GovTagLevel govTagLevel = new GovTagLevel();
			BeanUtils.copyProperties(govTagLevelDto, govTagLevel);
			govTagLevel.setCreationDate(new Date());
			return new R<>(service.insert(govTagLevel));
	}
	
	@SysLog("更新标签级别")
	@PutMapping("/updateTagLevel")
	@PreAuthorize("@pms.hasPermission('gov_tagLevel_edit')")
	@ApiOperation(value = "更新标签级别", notes = "更新标签级别", httpMethod = "PUT")
	public R<Boolean> updateTagLevel(@RequestBody GovTagLevelDTO govTagLevelDto) {
		GovTagLevel govTagLevel = service.selectById(govTagLevelDto.getLevelId());
		BeanUtils.copyProperties(govTagLevelDto, govTagLevel);
		return new R<>(service.updateById(govTagLevel));
	}
	
}
