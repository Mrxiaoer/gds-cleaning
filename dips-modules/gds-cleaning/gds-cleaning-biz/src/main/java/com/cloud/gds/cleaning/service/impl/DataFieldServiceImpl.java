package com.cloud.gds.cleaning.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.vo.DataFieldVo;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.mapper.DataFieldMapper;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.service.DataRuleService;
import com.cloud.gds.cleaning.utils.CommonUtils;
import com.cloud.gds.cleaning.utils.DataRuleUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户数据实现层
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-07
 */
@Service
public class DataFieldServiceImpl extends ServiceImpl<DataFieldMapper, DataField> implements DataFieldService {

	private final DataRuleService dataRuleService;
	private final DataFieldValueService dataFieldValueService;

	@Autowired
	public DataFieldServiceImpl(DataRuleService dataRuleService, DataFieldValueService dataFieldValueService) {
		this.dataRuleService = dataRuleService;
		this.dataFieldValueService = dataFieldValueService;
	}

	@Override
	public List<DataField> selectByRuleId(Long ruleId) {
		DataField dataField = new DataField();
		dataField.setRuleId(ruleId);
		dataField.setIsDeleted(DataCleanConstant.NO);
		return this.selectList(new EntityWrapper<>(dataField));
	}

	@Override
	public List<DataField> selectByRuleIds(Set<Long> ruleIds) {
		List<DataField> result = new ArrayList<>();
		for (Long id : ruleIds) {
			List<DataField> list = this.selectByRuleId(id);
			result.addAll(list);
		}
		return result;
	}

	@Override
	public DataFieldVo queryById(Long id) {
		DataFieldVo dataFieldVo = new DataFieldVo();
		DataField dataField = this.selectById(id);
		BeanUtils.copyProperties(dataField, dataFieldVo);
		dataFieldVo.setRuleName(
			(dataField.getRuleId() == 0) ? null : (dataRuleService.selectById(dataField.getRuleId()).getName()));
		return dataFieldVo;
	}

	@Override
	public Boolean save(DataField dataField) {
		dataField.setCreateTime(LocalDateTime.now());
		assert SecurityUtils.getUser() != null;
		dataField.setCreateUser(SecurityUtils.getUser().getId());
		dataField.setDeptId(SecurityUtils.getUser().getDeptId());
		dataField.setDeptName(SecurityUtils.getUser().getDeptName());
		return this.insert(dataField);
	}

	@Override
	public Boolean update(DataField dataField) {
		assert SecurityUtils.getUser() != null;
		dataField.setModifiedUser(SecurityUtils.getUser().getId());
		dataField.setModifiedTime(LocalDateTime.now());
		return this.updateById(dataField);
	}

	@Override
	public Boolean deleteById(Long id) {
		DataField field = new DataField();
		field.setId(id);
		field.setIsDeleted(DataCleanConstant.YES);
		field.setModifiedTime(LocalDateTime.now());
		assert SecurityUtils.getUser() != null;
		field.setModifiedUser(SecurityUtils.getUser().getId());
		// TODO 主表删除之后子表是否已进行删除,暂时不进行处理
		return this.update(field);
	}

	@Override
	public Boolean deleteByIds(Set<Long> ids) {
		DataField dataField = new DataField();
		dataField.setIsDeleted(DataCleanConstant.YES);
		dataField.setModifiedTime(LocalDateTime.now());
		assert SecurityUtils.getUser() != null;
		dataField.setModifiedUser(SecurityUtils.getUser().getId());
		return this.update(dataField, new EntityWrapper<DataField>().in("id", ids));
	}

	@Override
	public Boolean checkRule(Long id, Long ruleId) {
		DataField dataField = this.selectById(id);
		// 数据池数据为空, 规则可换
		if (dataFieldValueService.selectByfieldId(id).size() > 0) {
			// 原先规则为空,规则可以换
			if (dataField.getRuleId() != null) {
				DataRuleVo oldVo = DataRuleUtils.po2Vo(dataRuleService.selectById(dataField.getRuleId()));
				DataRuleVo newVo = DataRuleUtils.po2Vo(dataRuleService.selectById(ruleId));
				SortedMap<String, String> old =
					oldVo.getDetail() != null ? DataRuleUtils.changeSortedMap(oldVo.getDetail()) : null;
				SortedMap<String, String> fresh =
					newVo.getDetail() != null ? DataRuleUtils.changeSortedMap(newVo.getDetail()) : null;
				// 如果规则前后2个规则中参数为空,可更新规则
				if (old == null) {
					return true;
				}
				if (fresh == null) {
					return false;
				}
				return CommonUtils.checkSortedMap(old, fresh);
			}
		}

		return true;
	}

	@Override
	public Boolean updateNeedReanalysis(Long ruleId) {
		if (!ruleId.equals(DataCleanConstant.ZERO)) {
			List<DataField> list = this.selectByRuleId(ruleId);
			for (DataField dataField : list) {
				if (dataField.getNeedReanalysis().equals(DataCleanConstant.NO)) {
					DataField q = new DataField();
					q.setId(dataField.getId());
					q.setNeedReanalysis(DataCleanConstant.YES);
					this.updateById(q);
				}
			}
		}
		return true;
	}

}
