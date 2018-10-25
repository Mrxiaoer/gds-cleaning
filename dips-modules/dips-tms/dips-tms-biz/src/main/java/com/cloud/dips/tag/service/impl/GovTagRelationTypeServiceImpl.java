package com.cloud.dips.tag.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.tag.api.entity.GovTagRelationType;
import com.cloud.dips.tag.mapper.GovTagRelationTypeMapper;
import com.cloud.dips.tag.service.GovTagRelationTypeService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZB
 */
@Slf4j
@Service
public class GovTagRelationTypeServiceImpl extends ServiceImpl<GovTagRelationTypeMapper, GovTagRelationType>
		implements GovTagRelationTypeService {

}
