package com.cloud.gds.preprocessing.mapper;

import com.cloud.gds.preprocessing.entity.BasePolicy;
import com.cloud.gds.preprocessing.entity.ScrapyGovPolicyGeneral;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采集解读政策模型mapper
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-21
 */
@Mapper
public interface ScrapyGovPolicyExplainMapper {

	/**
	 * 获取爬虫政策表中申报最新爬虫数据1w
	 *
	 * @return
	 */
	List<ScrapyGovPolicyGeneral> gainExplainScrapyPolicy();

	/**
	 * 获取采集表中的id、title
	 * @return
	 */
	List<BasePolicy> scrapyExplainBase();

	/**
	 * 根据ids更新爬虫表的状态值
	 *
	 * @param state 状态值
	 * @param ids   需要更新的ids
	 * @return
	 */
	boolean updateByIdsAndIsDeleted(@Param("state") Long state, @Param("ids") List<Long> ids);
}
