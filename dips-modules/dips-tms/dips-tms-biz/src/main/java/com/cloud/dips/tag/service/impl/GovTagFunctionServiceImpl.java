package com.cloud.dips.tag.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.tag.api.entity.GovTagFunction;
import com.cloud.dips.tag.mapper.GovTagFunctionMapper;
import com.cloud.dips.tag.service.GovTagFunctionService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZB
 */
@Slf4j
@Service
public class GovTagFunctionServiceImpl extends ServiceImpl<GovTagFunctionMapper, GovTagFunction>
		implements GovTagFunctionService {

	@Override
	public GovTagFunction getByNumber(String number) {
		EntityWrapper<GovTagFunction> e=new EntityWrapper<GovTagFunction>();
		e.eq("number", number);
		return this.selectOne(e);
	}
	
}
