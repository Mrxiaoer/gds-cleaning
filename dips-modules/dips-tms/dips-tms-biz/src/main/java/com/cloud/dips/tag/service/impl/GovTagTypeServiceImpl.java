package com.cloud.dips.tag.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.SpecialStringUtil;
import com.cloud.dips.tag.api.entity.GovTagType;
import com.cloud.dips.tag.api.vo.GovTagTypeVO;
import com.cloud.dips.tag.mapper.GovTagTypeMapper;
import com.cloud.dips.tag.service.GovTagTypeService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZB
 */
@Slf4j
@Service
public class GovTagTypeServiceImpl extends ServiceImpl<GovTagTypeMapper, GovTagType>
		implements GovTagTypeService {
	
	@Override
	public List<GovTagTypeVO> selectParents() {
		return baseMapper.selectParents();
	}

	@Override
	public Page<GovTagTypeVO> selectTypeVoPage(Query<GovTagTypeVO> query) {
		Object name = query.getCondition().get("name");
		String typename="";
		if(name!=null){
			typename=SpecialStringUtil.escapeExprSpecialWord(name.toString());
		}
		query.setRecords(baseMapper.selectTypeVoPage(query, typename));
		return query;
	}
}
