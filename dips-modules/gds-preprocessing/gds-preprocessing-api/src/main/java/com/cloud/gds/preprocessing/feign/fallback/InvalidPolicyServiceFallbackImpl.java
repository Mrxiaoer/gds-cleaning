package com.cloud.gds.preprocessing.feign.fallback;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.preprocessing.feign.InvalidPolicyService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 政策预清洗
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-29
 */
@Slf4j
@Component
public class InvalidPolicyServiceFallbackImpl implements InvalidPolicyService {

	@Setter
	private Throwable cause;

	@Override
	public R invalidTitle(Integer titleLength, Integer textLength) {
		log.error("feign 清除爬取表中无效title text的数据失败", cause);
		return null;
	}

	@Override
	public R cleanInvalidInScape() {
		log.error("feign 清除采集表中相同名称的数据失败", cause);
		return null;
	}

	@Override
	public R cleanRepeatScrapy() {
		log.error("feign 爬取数据与正式库中数据进行清洗,清洗到采集表中重复的数据失败", cause);
		return null;
	}

	@Override
	public R dataMigrationSurface(Long examineUserId) {
		log.error("feign 国策数据迁移失败", cause);
		return null;
	}
}
