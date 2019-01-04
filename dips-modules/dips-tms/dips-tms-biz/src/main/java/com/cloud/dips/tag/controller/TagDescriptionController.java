package com.cloud.dips.tag.controller;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.cloud.dips.tag.api.dto.GovTagDescriptionDTO;
import com.cloud.dips.tag.api.entity.GovTagDescription;
import com.cloud.dips.tag.api.vo.GovTagDescriptionVO;
import com.cloud.dips.tag.service.GovTagDescriptionService;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author ZB
 *
 */
@RestController
@RequestMapping("/description")
public class TagDescriptionController {
	@Autowired
	private GovTagDescriptionService service;

	
	@SysLog("添加标签描述")
	@PostMapping("/create")
	@ApiOperation(value = "添加标签描述", notes = "添加标签描述", httpMethod = "POST")
	public R<Boolean> saveTag(@RequestBody GovTagDescriptionDTO govTagDescriptionDto) {
			GovTagDescription govTagDescription = new GovTagDescription();
			BeanUtils.copyProperties(govTagDescriptionDto, govTagDescription);
			// 获取当前用户 
			DipsUser user = SecurityUtils.getUser();
			govTagDescription.setCreatorId(user.getId());
			govTagDescription.applyDefaultValue();
			return new R<Boolean>(service.insert(govTagDescription));
	}

	@RequestMapping("/page/{tagId}")
	@ApiOperation(value = "分页查询标签描述", notes = "分页查询标签描述",httpMethod="GET")
	public Page<GovTagDescriptionVO> page(@PathVariable(value="tagId") Integer tagId,@RequestParam Map<String, Object> params) {
		String orderByField = "orderByField";
		if(StrUtil.isBlank(params.getOrDefault(orderByField, "").toString())){
			params.put(orderByField, "id");
		}
		params.put("tagId", tagId);
		return service.selectAllPage(new Query<GovTagDescriptionVO>(params));
	}
	
	@SysLog("删除标签描述")
	@RequestMapping("/delete/{id}")
	@ApiOperation(value = "删除标签描述", notes = "删除标签描述",httpMethod="POST")
	public R<Boolean> tagDescriptionDel(@PathVariable Integer id) {
		GovTagDescription govTagDescription = service.selectById(id);
		if(govTagDescription==null){
			return new R<Boolean>(false,"描述不存在！");
		}else{
			return new R<Boolean>(service.deleteById(govTagDescription.getDescriptionId()));
		}
	}
	
	@SysLog("更新标签描述")
	@RequestMapping("/update")
	@ApiOperation(value = "更新标签描述", notes = "更新标签描述", httpMethod = "POST")
	public R<Boolean> updateTagDescription(@RequestBody GovTagDescription govTagDescription) {
		GovTagDescription bean = service.selectById(govTagDescription.getDescriptionId());
		if(bean==null){
			return new R<Boolean>(false,"描述不存在！");
		}else{
			BeanUtils.copyProperties(govTagDescription, bean);
			return new R<Boolean>(service.updateById(bean));
		}
	}
	
}
