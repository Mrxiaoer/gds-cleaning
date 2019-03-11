package com.cloud.gds.cleaning.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.mapper.DataFieldMapper;
import com.cloud.gds.cleaning.service.CombineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

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

	@Autowired
	public CombineServiceImpl(DataFieldMapper dataFieldMapper) {
		this.dataFieldMapper = dataFieldMapper;
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
	public boolean nominateCleanPool(DataField dataField) {
		// dataField中仅仅包含ruleId、name
		dataField.setMethodId(DataCleanConstant.THREE);
		dataField.setCreateTime(LocalDateTime.now());
		assert SecurityUtils.getUser() != null;
		dataField.setCreateUser(SecurityUtils.getUser().getId());
		dataField.setDeptId(SecurityUtils.getUser().getDeptId());
		dataField.setDeptName(SecurityUtils.getUser().getDeptName());
		return SqlHelper.retBool(dataFieldMapper.insert(dataField));
	}
}
