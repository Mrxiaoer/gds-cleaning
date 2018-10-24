package com.cloud.dips.tag.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.common.security.service.DipsUser;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.tag.dto.GovTagDTO;
import com.cloud.dips.tag.entity.GovTag;
import com.cloud.dips.tag.service.GovTagDescriptionService;
import com.cloud.dips.tag.service.GovTagRelationService;
import com.cloud.dips.tag.service.GovTagService;
import com.cloud.dips.tag.vo.GovTagVO;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/tag")
public class TagController {
	@Autowired
	private GovTagService service;
	
	@Autowired
	private GovTagDescriptionService govTagDescriptionService;
	
	@Autowired
	private GovTagRelationService govTagRelationService;
	
	/**
	 * 
	 * 通过ID查询标签
	 *
	 * @param id
	 * 
	 * @return 标签
	 * 
	 */
	@GetMapping("/{id}")
	//@PreAuthorize("@pms.hasPermission('gov_tag_view')")
	@ApiOperation(value = "查询标签详情", notes = "根据ID查询标签详情: params{通知ID: tagId}",httpMethod="GET")
	public GovTagVO tag(@PathVariable Integer id) {
		return service.selectGovTagVoById(id);
	}
	
	/**
	 * 分页查询标签
	 *
	 * @param params 参数集
	 * 
	 * @return 标签集合
	 */
	@RequestMapping("/tagPage")
	@ApiOperation(value = "分页查询标签", notes = "标签集合",httpMethod="GET")
	public Page<GovTagVO> tagPage(@RequestParam Map<String, Object> params) {
		if(StrUtil.isBlank(params.getOrDefault("orderByField", "").toString())){
			params.put("orderByField", "g_tag_id");
		}
		return service.selectAllPage(new Query<>(params));
	}

	/**
	 * 删除标签
	 *
	 * @param id
	 * 
	 * @return R
	 */
	@SysLog("删除标签")
	@DeleteMapping("/{id}")
	//@PreAuthorize("@pms.hasPermission('gov_tag_del')")
	@ApiOperation(value = "删除标签", notes = "根据ID删除标签: params{标签ID: tagId}",httpMethod="DELETE")
	public R<Boolean> tagDel(@PathVariable Integer id) {
		GovTag govTag = service.selectById(id);
		if(govTag==null){
			return new R<>(false);
		}else{
				govTagDescriptionService.deleteByTagId(govTag.getTagId());
				govTagRelationService.deleteById(govTag.getTagId());
				return new R<>(service.deleteGovTagById(govTag));
		}
	}
	
	@SysLog("添加标签")
	@PostMapping("/saveTag")
	//@PreAuthorize("@pms.hasPermission('gov_tag_add')")
	@ApiOperation(value = "添加标签", notes = "添加标签", httpMethod = "POST")
	public R<Boolean> saveTag(@Valid @RequestBody GovTagDTO govTagDto) {
		Integer i=service.findByGovTagName(govTagDto.getName());
		if(!(i>0)){
			GovTag govTag = new GovTag();
			BeanUtils.copyProperties(govTagDto, govTag);
			// 获取当前用户 
			DipsUser user = SecurityUtils.getUser();
			govTag.setCreatorId(user.getId());
			govTag.applyDefaultValue();
			service.save(govTag);
		}
		return new R<>(Boolean.TRUE);
	}
	
/**
 * 增加标签应用次数
 * @param id标签ID
 * @return
 */
	@PutMapping("/refer/{id}")
	@ApiOperation(value = "增加标签应用次数", notes = "增加标签应用次数", httpMethod = "PUT")
	public R<Boolean> refer(@PathVariable Integer id) {
		GovTag govTag = service.selectById(id);
		govTag.setRefers(govTag.getRefers()+1);
		return new R<>(service.updateById(govTag));
	}
	/**
	 * 减少标签应用次数
	 * @param id标签ID
	 * @return
	 */
	@PutMapping("/derefer/{id}")
	@ApiOperation(value = "减少标签应用次数", notes = "减少标签应用次数", httpMethod = "PUT")
	public R<Boolean> derefer(@PathVariable Integer id) {
		GovTag govTag = service.selectById(id);
		if(govTag.getRefers()<1){
			govTag.setRefers(0);
		}else{
			govTag.setRefers(govTag.getRefers()-1);
		}
		return new R<>(service.updateById(govTag));
	}
	
	
	/**
	 * 更新标签浏览量
	 * @param id标签ID
	 * @return
	 */
	@PutMapping("/tagViews/{id}")
	@ApiOperation(value = "更新标签浏览量", notes = "更新标签浏览量", httpMethod = "PUT")
	public R<Boolean> tagViews(@PathVariable Integer id) {
		GovTag govTag = service.selectById(id);
		govTag.setViews(govTag.getViews()+1);
		return new R<>(service.updateById(govTag));
	}
	

	@SysLog("更新标签")
	@PutMapping("/updateTag")
	//@PreAuthorize("@pms.hasPermission('gov_tag_edit')")
	@ApiOperation(value = "更新标签", notes = "更新标签", httpMethod = "PUT")
	public R<Boolean> updateTag(@RequestBody GovTagDTO govTagDto) {
		GovTag govTag = service.selectById(govTagDto.getTagId());
		BeanUtils.copyProperties(govTagDto, govTag);
		return new R<>(service.updateById(govTag));
	}
}