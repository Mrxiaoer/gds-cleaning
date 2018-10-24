package com.cloud.dips.tag.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.tag.entity.GovTagDescription;
import com.cloud.dips.tag.mapper.GovTagDescriptionMapper;
import com.cloud.dips.tag.service.GovTagDescriptionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GovTagDescriptionServiceImpl extends ServiceImpl<GovTagDescriptionMapper, GovTagDescription>
		implements GovTagDescriptionService {
	@Autowired
	private GovTagDescriptionMapper mapper;
	
	
	@Override
	public Boolean deleteByTagId(Integer tagId) {
		return mapper.deleteByTagId(tagId);
	}

	

}
