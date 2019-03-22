package com.cloud.gds.preprocessing.mapper;

import com.cloud.gds.preprocessing.entity.ScrapyGovPolicyGeneral;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采集通用政策模型mapper
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-21
 */
@Mapper
public interface ScrapyGovPolicyGeneralMapper {

	/**
	 * 获取爬虫政策表中最新爬虫数据
	 *
	 * @return
	 */
	List<ScrapyGovPolicyGeneral> gainScrapyPolicy();

	/**
	 * 根据ids更新爬虫表的状态值
	 *
	 * @param state 状态值
	 * @param ids   需要更新的ids
	 * @return
	 */
	boolean updateByIdsAndIsDeleted(@Param("state") Long state, @Param("ids") List<Long> ids);
}
