package com.cloud.gds.preprocessing.mapper;

import com.cloud.gds.preprocessing.entity.BasePolicy;
import com.cloud.gds.preprocessing.entity.GovPolicyGeneral;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资讯
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-05-09
 */
@Mapper
public interface GovPolicyInformationMapper {

	/**
	 * 获取真实表中的id、title
	 * @return
	 */
	List<BasePolicy> realPolicyBase();

	/**
	 * 批量插入数据
	 *
	 * @param list
	 * @return
	 */
	boolean insertBatch(@Param("list") List<GovPolicyGeneral> list);
}
