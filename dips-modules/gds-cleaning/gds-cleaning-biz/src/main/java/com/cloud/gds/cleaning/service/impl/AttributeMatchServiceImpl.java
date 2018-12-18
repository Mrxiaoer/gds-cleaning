package com.cloud.gds.cleaning.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.gds.cleaning.api.entity.AttributeMatch;
import com.cloud.gds.cleaning.mapper.AttributeMatchMapper;
import com.cloud.gds.cleaning.service.AttributeMatchService;
import org.springframework.stereotype.Service;

/**
 * @Author : lolilijve
 * @Email : 1042703214@qq.com
 * @Date : 2018-11-22
 */
@Service("attributeMatchService")
public class AttributeMatchServiceImpl extends ServiceImpl<AttributeMatchMapper, AttributeMatch> implements
	AttributeMatchService {

}
