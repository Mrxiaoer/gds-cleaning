package com.cloud.gds.gmsanalyse.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyseFeature;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 政策分析特征mapper
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-04
 */
@Mapper
public interface PolicyAnalyseFeatureMapper extends BaseMapper<PolicyAnalyseFeature> {

	/**
	 * 批量插入
	 *
	 * @param list
	 * @return
	 */
	boolean batchInsert(@Param("list") List<PolicyAnalyseFeature> list);

	/**
	 * 根据政策表id查询其特征
	 *
	 * @param analyseId
	 * @return
	 */
	List<PolicyAnalyseFeature> info(@Param("analyseId") Long analyseId);

}
