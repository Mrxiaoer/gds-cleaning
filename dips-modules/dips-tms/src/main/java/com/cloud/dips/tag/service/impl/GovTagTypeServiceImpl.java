package com.cloud.dips.tag.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.tag.entity.GovTagType;
import com.cloud.dips.tag.mapper.GovTagTypeMapper;
import com.cloud.dips.tag.service.GovTagTypeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GovTagTypeServiceImpl extends ServiceImpl<GovTagTypeMapper, GovTagType>
		implements GovTagTypeService {
}
