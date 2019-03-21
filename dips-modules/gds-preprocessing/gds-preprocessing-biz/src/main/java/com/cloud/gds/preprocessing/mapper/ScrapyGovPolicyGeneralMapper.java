package com.cloud.gds.preprocessing.mapper;

import com.cloud.gds.preprocessing.entity.ScrapyGovPolicyGeneral;
import org.apache.ibatis.annotations.Mapper;

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

}
