package com.cloud.gds.preprocessing.feign;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.preprocessing.constant.PreprocessingConstant;
import com.cloud.gds.preprocessing.feign.factory.InvalidPolicyServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-29
 */
@FeignClient(value = PreprocessingConstant.MODULE_NAME, path = "/invalid", fallbackFactory = InvalidPolicyServiceFallbackFactory.class)
public interface InvalidPolicyService {

	/**
	 * 清除爬取表中无效title text的数据
	 *
	 * @param titleLength 主题长度
	 * @param textLength  正文长度
	 * @return
	 */
	@PostMapping("/clean_title")
	R invalidTitle(@RequestParam Integer titleLength, @RequestParam Integer textLength);

	/**
	 * 清除采集表中相同名称的数据
	 *
	 * @return
	 */
	@GetMapping("/clean_equally_title")
	R cleanInvalidInScape();

	/**
	 * 爬取数据与正式库中数据进行清洗,清洗到采集表中重复的数据
	 *
	 * @return
	 */
	@GetMapping("/clean_two_table")
	R cleanRepeatScrapy();


	/**
	 * 国策数据迁移
	 *
	 * @param examineUserId 用户自定义的审核人id
	 * @return
	 */
	@GetMapping("/transfer")
	R dataMigrationSurface(@RequestParam Long examineUserId);
}
