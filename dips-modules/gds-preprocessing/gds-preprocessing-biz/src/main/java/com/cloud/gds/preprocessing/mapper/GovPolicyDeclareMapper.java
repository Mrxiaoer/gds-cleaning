package com.cloud.gds.preprocessing.mapper;

import com.cloud.gds.preprocessing.entity.GovPolicyGeneral;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申报
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-05-09
 */
@Mapper
public interface GovPolicyDeclareMapper {
	/**
	 * 批量插入数据
	 *
	 * @param list
	 * @return
	 */
	boolean insertBatch(@Param("list") List<GovPolicyGeneral> list);

}
