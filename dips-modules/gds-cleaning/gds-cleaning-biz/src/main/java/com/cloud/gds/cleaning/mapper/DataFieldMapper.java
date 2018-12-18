package com.cloud.gds.cleaning.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.gds.cleaning.api.entity.DataField;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据数据库映射接口
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-07
 */
@Mapper
public interface DataFieldMapper extends BaseMapper<DataField> {

}
