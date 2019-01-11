package com.cloud.gds.cleaning.api.feign.fallback;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.feign.DataRuleService;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.api.vo.LabelVo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@Slf4j
@Component
public class DataRuleServiceFallbackImpl implements DataRuleService {

	@Setter
	private Throwable cause;

	@Override
	public R page(Map<String, Object> params) {
		return null;
	}

	@Override
	public R info(Long id) {
		return null;
	}

	@Override
	public R selectAll() {
		return null;
	}

	@Override
	public List<LabelVo> getKey(Long id) {
		return null;
	}

	@Override
	public R save(DataRuleVo dataRuleVo) {
		return null;
	}

	@Override
	public R update(DataRuleVo dataRuleVo) {
		return null;
	}

	@Override
	public R delete(Long id) {
		return null;
	}

	@Override
	public R deleteT(Set<Long> ids) {
		return null;
	}
}
