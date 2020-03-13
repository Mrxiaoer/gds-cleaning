package com.cloud.gds.preprocessing.mapper;

import com.cloud.gds.preprocessing.entity.BasePolicy;
import com.cloud.gds.preprocessing.entity.ScrapyGovPolicyGeneral;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-19
 */
@Mapper
public interface InvalidPolicyMapper {

	/**
	 * 获取国策采集长度问题数据
	 *
	 * @param titleLength
	 * @param textLength
	 * @return
	 */
	List<Long> gainIssueId(@Param("titleLength") Integer titleLength, @Param("textLength") Integer textLength);

	/**
	 * 查询采集库中is_deleted = 0 重复的数据
	 *
	 * @return
	 */
	List<BasePolicy> gainIdenticalPolicy();

	/**
	 * 获取真实库中的政策数据
	 *
	 * @return
	 */
	List<BasePolicy> gainRealPolicy();

	/**
	 * 获取真实库中的政策数据 非2158
	 *
	 * @return
	 */
	List<BasePolicy> gainRealPolicyNull2158();

	/**
	 * 获取真实库中的政策数据 非2158
	 *
	 * @return
	 */
	List<BasePolicy> gainRealPolicyIs2158();


	/**
	 * 获取采集库中政策数据
	 *
	 * @return
	 */
	List<BasePolicy> gainScrapyPolicy();

	/**
	 * 重复清洗正式库中重复数据
	 *
	 * @return
	 */
	List<BasePolicy> cleanRealPolicy();

	/**
	 * 根据ids更新爬取表中的数据状态
	 *
	 * @param ids
	 * @return
	 */
	boolean updateScrapyIsDeleted(@Param("ids") List<Long> ids);

	/**
	 * 根据ids更新real中的数据状态
	 *
	 * @param ids
	 * @return
	 */
	boolean updateRealIsDeleted(@Param("ids") List<Long> ids);

	/**
	 * 获取爬虫政策表中最新爬虫数据1w
	 *
	 * @return
	 */
	List<ScrapyGovPolicyGeneral> ScrapyPolicyGeneral();

	/**
	 * 根据ids更新爬虫表的状态值
	 *
	 * @param state 状态值
	 * @param ids   需要更新的ids
	 * @return
	 */
	boolean updateByIdsAndIsDeleted(@Param("state") Long state, @Param("ids") List<Long> ids);

}
