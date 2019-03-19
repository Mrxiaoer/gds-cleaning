package com.cloud.gds.cleaning.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.gds.cleaning.api.entity.AnalysisResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 算法结果分析
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-28
 */
@Mapper
public interface AnalysisResultMapper extends BaseMapper<AnalysisResult> {

	/**
	 * 批量导入数据
	 * @param valueList
	 * @return
	 */
	boolean insertAll(@Param("valueList") List<AnalysisResult> valueList);

	/**
	 * 根据id删除算法结果分析情况
	 * @param ids
	 * @return
	 */
	boolean relevanceDelete(@Param("ids") Set<Long> ids);

}
