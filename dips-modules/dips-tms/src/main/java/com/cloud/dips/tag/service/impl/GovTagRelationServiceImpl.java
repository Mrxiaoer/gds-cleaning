package com.cloud.dips.tag.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.tag.entity.GovTagRelation;
import com.cloud.dips.tag.mapper.GovTagRelationMapper;
import com.cloud.dips.tag.service.GovTagRelationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GovTagRelationServiceImpl extends ServiceImpl<GovTagRelationMapper, GovTagRelation>
		implements GovTagRelationService {

}
