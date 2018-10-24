package com.cloud.dips.admin.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.dto.RelationTypeDTO;
import com.cloud.dips.admin.api.entity.SysRelationType;
import com.cloud.dips.admin.mapper.SysRelationTypeMapper;
import com.cloud.dips.admin.service.SysRelationTypeService;
import com.cloud.dips.common.core.util.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRelationTypeServiceImpl extends ServiceImpl<SysRelationTypeMapper, SysRelationType> implements SysRelationTypeService {

	@Autowired
	private SysRelationTypeMapper mapper;

	@Override
	public Boolean insertType(RelationTypeDTO typeDTO) {
		SysRelationType type = new SysRelationType();
		BeanUtils.copyProperties(typeDTO, type);
		return this.insert(type);
	}

	@Override
	public SysRelationType selectByNumber(String number) {
		return mapper.selectByNumber(number);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Page<SysRelationType> selectAllPage(Query query) {
		Object name = query.getCondition().get("name");
		Object number = query.getCondition().get("number");
		query.setRecords(mapper.selectAllPage(query, name, number));
		return query;
	}
}
