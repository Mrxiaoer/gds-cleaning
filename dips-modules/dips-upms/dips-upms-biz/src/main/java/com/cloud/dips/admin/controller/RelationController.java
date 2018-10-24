package com.cloud.dips.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.dips.admin.api.entity.SysRelation;
import com.cloud.dips.admin.api.entity.SysRelationType;
import com.cloud.dips.admin.service.SysRelationService;
import com.cloud.dips.admin.service.SysRelationTypeService;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/relation")
public class RelationController {

	@Autowired
	private SysRelationService service;
	@Autowired
	private SysRelationTypeService typeService;

	@SysLog("添加关联")
	@PostMapping("/save")
	@ApiOperation(value = "添加关联", notes = "添加关联", httpMethod = "POST")
	public R<Boolean> saveRelation(@RequestParam Map<String, Object> params) {

		if (StrUtil.isBlank(params.getOrDefault("relationId", "").toString())
			|| StrUtil.isBlank(params.getOrDefault("node", "").toString())) {
			return new R<>(false);
		} else {
			String[] correlationIds = params.get("correlationIds").toString().split(",");
			Integer relationId = Integer.parseInt(params.get("relationId").toString());
			String node = params.get("node").toString();
			String number = params.get("number").toString();
			SysRelationType sysRelationType = typeService.selectByNumber(number);

			if (sysRelationType != null) {
				EntityWrapper<SysRelation> e2 = new EntityWrapper<>();
				e2.where("g_relation_id = {0}", relationId).where("g_node = {0}", node).where("g_type_id = {0}",
					sysRelationType.getId());
				service.delete(e2);
				for (String correlationId : correlationIds) {
					SysRelation bean = new SysRelation();
					bean.setNode(node);
					bean.setTypeId(sysRelationType.getId());
					bean.setRelationId(relationId);
					if (correlationId != null) {
						bean.setCorrelationId(Integer.parseInt(correlationId));
					}
					EntityWrapper<SysRelation> e4 = new EntityWrapper<>();
					e4.where("g_relation_id = {0}", relationId).where("g_node = {0}", node)
						.where("g_type_id = {0}", sysRelationType.getId())
						.where("g_correlation_id = {0}", correlationId);
					if (service.selectOne(e4) == null) {
						service.insert(bean);
					}
				}
			} else {
				return new R<>(false);
			}
			return new R<>(true);
		}
	}

	@SysLog("删除关联")
	@PostMapping("/delete")
	@ApiOperation(value = "删除关联", notes = "删除关联", httpMethod = "POST")
	public R<Boolean> deleteRelation(@RequestParam Map<String, Object> params) {

		if (StrUtil.isBlank(params.getOrDefault("relationId", "").toString())
			|| StrUtil.isBlank(params.getOrDefault("node", "").toString())) {
			return new R<>(false);
		} else {
			Integer relationId = Integer.parseInt(params.get("relationId").toString());
			String node = params.get("node").toString();
			EntityWrapper<SysRelation> e2 = new EntityWrapper<>();
			e2.where("g_relation_id = {0}", relationId).where("g_node = {0}", node);
			service.delete(e2);
			return new R<>(true);
		}
	}
}
