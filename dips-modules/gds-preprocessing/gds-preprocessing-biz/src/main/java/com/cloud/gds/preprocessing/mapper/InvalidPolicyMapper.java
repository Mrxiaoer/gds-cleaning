package com.cloud.gds.preprocessing.mapper;

import com.cloud.gds.preprocessing.entity.BasePolicy;
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
	 * 获取采集库中政策数据
	 *
	 * @return
	 */
	List<BasePolicy> gainScrapyPolicy();

	/**
	 * 根据ids更新爬取表中的数据状态
	 *
	 * @param ids
	 * @return
	 */
	boolean updatePolicyIsDeleted(@Param("ids") List<Long> ids);

	/**
	 * 根据ids更新真实表中的数据状态
	 *
	 * @param ids
	 * @return
	 */
//	boolean updateRealIsDeleted(@Param("ids") List<Long> ids);

}
