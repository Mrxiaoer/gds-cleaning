package com.cloud.gds.cleaning.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.SpecialStringUtil;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.DataFieldVo;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.mapper.DataFieldMapper;
import com.cloud.gds.cleaning.mapper.DataFieldValueMapper;
import com.cloud.gds.cleaning.mapper.DataRuleMapper;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.utils.CommonUtils;
import com.cloud.gds.cleaning.utils.DataRuleUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 用户数据实现层
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-07
 */
@Service
public class DataFieldServiceImpl extends ServiceImpl<DataFieldMapper, DataField> implements DataFieldService {

	private final DataRuleMapper dataRuleMapper;

	private final DataFieldValueMapper dataFieldValueMapper;

	private final DataFieldMapper dataFieldMapper;

	@Autowired
	public DataFieldServiceImpl(DataRuleMapper dataRuleMapper, DataFieldValueMapper dataFieldValueMapper, DataFieldMapper dataFieldMapper) {
		this.dataRuleMapper = dataRuleMapper;
		this.dataFieldValueMapper = dataFieldValueMapper;
		this.dataFieldMapper = dataFieldMapper;
	}

	@Override
	public Page<DataField> queryPage(Map<String, Object> params) {
		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<DataField> p = new Page<DataField>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<DataField> e = new EntityWrapper<DataField>();
		String name = params.getOrDefault("name", "").toString();
		if (StrUtil.isNotBlank(name)) {
			e.like("name", SpecialStringUtil.escapeExprSpecialWord(name));
		}
		// 查找来源方式
		String methodId = params.getOrDefault("methodId", "").toString();
		if (StrUtil.isNotBlank(methodId)) {
			e.eq("method_id", SpecialStringUtil.escapeExprSpecialWord(methodId));
		}
		e.eq("is_deleted", DataCleanConstant.FALSE);
		return this.selectPage(p, e);
	}

	@Override
	public Page<DataField> queryRecycleBinPage(Map<String, Object> params) {
		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<DataField> p = new Page<DataField>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<DataField> e = new EntityWrapper<DataField>();
		String name = params.getOrDefault("name", "").toString();
		if (StrUtil.isNotBlank(name)) {
			e.like("name", SpecialStringUtil.escapeExprSpecialWord(name));
		}
		// 查找来源方式
		String methodId = params.getOrDefault("methodId", "").toString();
		if (StrUtil.isNotBlank(methodId)) {
			e.eq("method_id", SpecialStringUtil.escapeExprSpecialWord(methodId));
		}
		return this.selectPage(p, e);
	}

	@Override
	public List<DataField> selectByRuleId(Long ruleId) {
		DataField dataField = new DataField();
		dataField.setRuleId(ruleId);
		dataField.setIsDeleted(DataCleanConstant.FALSE);
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
		dataFieldVo.setRuleName((dataField.getRuleId() == 0) ? null : (dataRuleMapper.selectById(dataField.getRuleId()).getName()));
		dataFieldVo.setRuleId(dataFieldVo.getRuleId() == 0 ? null : dataFieldVo.getRuleId());
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
		field.setIsDeleted(DataCleanConstant.TRUE);
		field.setModifiedTime(LocalDateTime.now());
		assert SecurityUtils.getUser() != null;
		field.setModifiedUser(SecurityUtils.getUser().getId());
		// TODO 主表删除之后子表是否已进行删除,暂时不进行处理
		return this.update(field);
	}

	@Override
	public Boolean deleteByIds(Set<Long> ids) {
		DataField dataField = new DataField();
		dataField.setIsDeleted(DataCleanConstant.TRUE);
		dataField.setModifiedTime(LocalDateTime.now());
		assert SecurityUtils.getUser() != null;
		dataField.setModifiedUser(SecurityUtils.getUser().getId());
		return this.update(dataField, new EntityWrapper<DataField>().in("id", ids));
	}

	@Override
	public Boolean checkRule(Long id, Long ruleId) {
		DataField dataField = this.selectById(id);
		// 数据池数据为空, 规则可换
		if ((dataFieldValueMapper.selectList(new EntityWrapper<DataFieldValue>().eq("field_id", id).eq("is_deleted", DataCleanConstant.FALSE))).size() > 0) {
			// 原先规则为空,规则可以换
			if (dataField.getRuleId() != null) {
				DataRuleVo oldVo = DataRuleUtils.po2Vo(dataRuleMapper.selectById(dataField.getRuleId()));
				DataRuleVo newVo = DataRuleUtils.po2Vo(dataRuleMapper.selectById(ruleId));
				SortedMap<String, String> old = oldVo.getDetail() != null ? DataRuleUtils.changeSortedMap(oldVo.getDetail()) : null;
				SortedMap<String, String> fresh = newVo.getDetail() != null ? DataRuleUtils.changeSortedMap(newVo.getDetail()) : null;
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
				if (dataField.getNeedReanalysis().equals(DataCleanConstant.FALSE)) {
					DataField q = new DataField();
					q.setId(dataField.getId());
					q.setNeedReanalysis(DataCleanConstant.TRUE);
					this.updateById(q);
				}
			}
		}
		return true;
	}

	@Override
	public String cleanPoolReduction(Long id) {
		// 判断规则是否存在
		// todo 规则已删除情况未进行判断 2019-2-23 13:59:21
		String str = null;
		boolean flag = false;
		DataField field = selectById(id);
		Long ruleId = field.getRuleId() != null ? field.getRuleId() : DataCleanConstant.FALSE;
		if (!ruleId.equals(DataCleanConstant.ZERO)) {
			DataRule dataRule = dataRuleMapper.selectById(ruleId);
			if (dataRule.getIsDeleted().equals(DataCleanConstant.TRUE)) {
				str = "规则名：" + dataRule.getName() + "已删除,请先还原规则后在还原数据池！";
			} else {
				flag = true;
			}
		} else {
			flag = true;
		}
		if (flag == true) {
			return String.valueOf(dataFieldMapper.reduction(id));
		} else {
			return str;
		}

	}

	@Override
	public boolean cleanPoolDelete(Long id) {
		return SqlHelper.retBool(dataFieldMapper.deleteById(id));
	}
}
