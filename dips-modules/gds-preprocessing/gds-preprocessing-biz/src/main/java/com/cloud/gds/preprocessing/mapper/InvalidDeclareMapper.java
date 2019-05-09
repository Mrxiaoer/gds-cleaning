package com.cloud.gds.preprocessing.mapper;

import com.cloud.gds.preprocessing.entity.BasePolicy;
import com.cloud.gds.preprocessing.entity.ScrapyGovPolicyGeneral;
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
public interface InvalidDeclareMapper {

	/**
	 * 获取国策采集长度问题数据
	 *
	 * @param titleLength
	 * @param textLength
	 * @return
	 */
	List<Long> gainIssueId(@Param("titleLength") Integer titleLength, @Param("textLength") Integer textLength);

	/**
	 * 根据ids更新爬取表中的数据状态
	 *
	 * @param ids
	 * @return
	 */
	boolean updateScrapyIsDeleted(@Param("ids") List<Long> ids);

	/**
	 * 查询采集库中is_deleted = 0 重复的数据
	 *
	 * @return
	 */
	List<BasePolicy> gainIdenticalPolicy();

	/**
	 * 查询申报数据
	 *
	 * @return
	 */
	List<ScrapyGovPolicyGeneral> invalidInformationPolicy();

	/**
	 * 根据ids更新爬虫表的状态值
	 *
	 * @param state 状态值
	 * @param ids   需要更新的ids
	 * @return
	 */
	boolean updateByIdsAndIsDeleted(@Param("state") Long state, @Param("ids") List<Long> ids);
}
