package com.cloud.gds.cleaning.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.dto.DistinctDto;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.ComBineRuleVo;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.api.vo.DataSetVo;
import com.cloud.gds.cleaning.api.vo.LabelVo;
import com.cloud.gds.cleaning.mapper.DataFieldMapper;
import com.cloud.gds.cleaning.mapper.DataFieldValueMapper;
import com.cloud.gds.cleaning.service.CombineService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.service.DataRuleService;
import com.cloud.gds.cleaning.utils.DataPoolUtils;
import com.cloud.gds.cleaning.utils.DataRuleUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 合并清洗池实现类
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-07
 */
@Service
public class CombineServiceImpl implements CombineService {

	private final DataFieldMapper dataFieldMapper;

	private final DataFieldValueMapper dataFieldValueMapper;

	private final DataFieldValueService dataFieldValueService;

	private final DataRuleService dataRuleService;

	@Autowired
	public CombineServiceImpl(DataFieldValueService dataFieldValueService, DataRuleService dataRuleService, DataFieldMapper dataFieldMapper, DataFieldValueMapper dataFieldValueMapper) {
		this.dataFieldValueService = dataFieldValueService;
		this.dataRuleService = dataRuleService;
		this.dataFieldMapper = dataFieldMapper;
		this.dataFieldValueMapper = dataFieldValueMapper;
	}

	@Override
	public List<DataField> getIdenticalCleanPool(Long fieldId) {
		DataField field = dataFieldMapper.selectById(fieldId);
		assert SecurityUtils.getUser() != null;
		Integer deptId = SecurityUtils.getUser().getDeptId();
		if (!DataCleanConstant.ZERO.equals(field.getRuleId())) {
			List<DataField> list = dataFieldMapper.selectList(new EntityWrapper<DataField>().eq("rule_id", field.getRuleId()).eq("is_deleted", DataCleanConstant.FALSE).eq("dept_id", deptId));
			Iterator<DataField> it = list.iterator();
			while (it.hasNext()) {
				DataField dataField = it.next();
				if (dataField.getId().equals(field.getId())) {
					it.remove();
				}
			}
			return list;
		}
		return null;
	}

	@Override
	public List<DataField> getDistinctCleanPool(Long fieldId) {
		DataField field = dataFieldMapper.selectById(fieldId);
		// 查询同一个部门的清洗池相关信息
		assert SecurityUtils.getUser() != null;
		Integer deptId = SecurityUtils.getUser().getDeptId();
		List<DataField> list = dataFieldMapper.selectList(new EntityWrapper<DataField>().eq("is_deleted", DataCleanConstant.FALSE).eq("dept_id", deptId));

		Iterator<DataField> it = list.iterator();
		while (it.hasNext()) {
			DataField dataField = it.next();
			if (dataField.getId().equals(field.getId())) {
				it.remove();
			}
		}
		return list;
	}

	@Override
	public boolean nominateCleanPool(DataField dataField) {
		// dataField中仅仅包含ruleId、name
		// 来源方式为3相当于合并清洗池
		dataField.setMethodId(DataCleanConstant.THREE);
		dataField.setCreateTime(LocalDateTime.now());
		assert SecurityUtils.getUser() != null;
		dataField.setCreateUser(SecurityUtils.getUser().getId());
		dataField.setDeptId(SecurityUtils.getUser().getDeptId());
		dataField.setDeptName(SecurityUtils.getUser().getDeptName());
		return SqlHelper.retBool(dataFieldMapper.insert(dataField));
	}

	@Override
	public boolean regularizationData(DistinctDto distinctDto) {
		List<DataFieldValue> oldList = dataFieldValueMapper.selectList(new EntityWrapper<DataFieldValue>().eq("is_deleted", DataCleanConstant.FALSE).in("field_id", distinctDto.getOldPoolIds()));
		// 拼装新池数据
		List<DataFieldValue> newList = new ArrayList<>();
		for (DataFieldValue dataFieldValue : oldList) {
			DataFieldValue value = new DataFieldValue();
			value.setFieldValue(dataFieldValue.getFieldValue());
			value.setFieldId(distinctDto.getNewPoolId());
			assert SecurityUtils.getUser() != null;
			value.setCreateUser(SecurityUtils.getUser().getId());
			newList.add(value);
		}
		return dataFieldValueService.batchSave(newList, 200);
	}

	@Override
	public List<ComBineRuleVo> itemList(Set<Long> ids) {
		List<ComBineRuleVo> list = new ArrayList<>();
		for (Long id : ids) {
			List<ComBineRuleVo> item = item(id);
			if (item != null) {
				list.addAll(item);
			}
		}
		return list;
	}

	private List<ComBineRuleVo> item(Long id) {
		DataField dataField = dataFieldMapper.selectById(id);
		if (!DataCleanConstant.ZERO.equals(dataField.getRuleId())) {
			DataRuleVo dataRuleVo = DataRuleUtils.po2Vo(dataRuleService.selectById(dataField.getRuleId()));
			if (dataRuleVo.getDetail() != null) {
				List<ComBineRuleVo> list = new ArrayList<>();
				for (DataSetVo dataSetVo : dataRuleVo.getDetail()) {
					ComBineRuleVo comBineRuleVo = new ComBineRuleVo();
					BeanUtils.copyProperties(dataSetVo, comBineRuleVo);
					comBineRuleVo.setCleanPoolName(dataField.getName());
					list.add(comBineRuleVo);
				}
				return list;
			}
		}
		return null;
	}

	@Override
	public Long nominateRule(DataRuleVo dataRuleVo) {
		DataRule dataRule = new DataRule();
		dataRule.setParams(DataRuleUtils.vo2po(dataRuleVo).getParams());

		dataRule.setModifiedTime(LocalDateTime.now());
		dataRule.setModifiedTime(LocalDateTime.now());
		assert SecurityUtils.getUser() != null;
		dataRule.setCreateUser(SecurityUtils.getUser().getId());
		dataRule.setDeptId(SecurityUtils.getUser().getDeptId());
		if (dataRuleService.insert(dataRule)) {
			return dataRule.getId();
		}
		return DataCleanConstant.ZERO;
	}

	@Override
	public boolean distinctData(DistinctDto distinctDto) {
		// 组装新池数据
		List<DataFieldValue> oldList = dataFieldValueMapper.selectList(new EntityWrapper<DataFieldValue>().eq("is_deleted", DataCleanConstant.FALSE).in("field_id", distinctDto.getOldPoolIds()));
		List<DataFieldValue> newList = new ArrayList<>();
		for (DataFieldValue dataFieldValue : oldList) {
			DataFieldValue value = new DataFieldValue();

			// 组装新规则数据
			value.setFieldValue(assembleRule(dataFieldValue, distinctDto.getDefaultRuleName()));

			value.setFieldId(distinctDto.getNewPoolId());
			assert SecurityUtils.getUser() != null;
			value.setCreateUser(SecurityUtils.getUser().getId());
			newList.add(value);
		}
		return dataFieldValueService.batchSave(newList, 200);
	}

	private String assembleRule(DataFieldValue dataFieldValue, List<LabelVo> list) {
		Map<String, Object> map = DataPoolUtils.entity2Vo(dataFieldValue).getFieldValue();
		Map<String, Object> map1 = new HashMap<>();
		for (LabelVo labelVo : list) {
			map1.put(labelVo.getProp(), map.containsKey(labelVo.getProp()) ? map.get(labelVo.getProp()) : labelVo.getLabel());
		}
		return JSON.toJSONString(map1);
	}


}
