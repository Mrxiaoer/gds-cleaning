package com.cloud.gds.gmsanalyse.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 政策分析数据层接口
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-03
 */
@Mapper
public interface PolicyAnalyseMapper extends BaseMapper<PolicyAnalyse> {

	/**
	 * 根据id删除政策分析情况
	 *
	 * @param id
	 * @return
	 */
	boolean queryDelete(@Param("id") Long id);

}
