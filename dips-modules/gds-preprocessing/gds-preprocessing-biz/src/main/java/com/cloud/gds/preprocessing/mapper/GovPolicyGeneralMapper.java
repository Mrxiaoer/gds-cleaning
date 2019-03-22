package com.cloud.gds.preprocessing.mapper;

import com.cloud.gds.preprocessing.entity.GovPolicyGeneral;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 真实表mapper
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-21
 */
@Mapper
public interface GovPolicyGeneralMapper {

	/**
	 * 批量插入数据
	 *
	 * @param list
	 * @return
	 */
	boolean insertBatch(@Param("list") List<GovPolicyGeneral> list);

}
