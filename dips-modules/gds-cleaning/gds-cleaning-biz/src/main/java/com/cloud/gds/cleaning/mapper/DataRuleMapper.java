package com.cloud.gds.cleaning.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.gds.cleaning.api.entity.DataRule;
import org.apache.ibatis.annotations.Mapper;

/**
 * 规则配置
 *
 * @author lolilijve
 * @date 2018-11-22 10:59:36
 */
@Mapper
public interface DataRuleMapper extends BaseMapper<DataRule> {

}
