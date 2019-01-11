package com.cloud.gds.cleaning.api.feign.fallback;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.feign.ResultSetService;
import com.cloud.gds.cleaning.api.vo.CleanItem;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@Slf4j
@Component
public class ResultSetServiceFallbackImpl implements ResultSetService {

	@Setter
	private Throwable cause;

	@Override
	public R page(Map<String, Object> params) {
		return null;
	}

	@Override
	public R contrastBefore(Map<String, Object> params) {
		return null;
	}

	@Override
	public R contrastAfter(Map<String, Object> params) {
		return null;
	}

	@Override
	public List<CleanItem> cleaningItem(Long id) {
		return null;
	}

	@Override
	public R clear(Long fieldId) {
		return null;
	}

	@Override
	public R clearBuffer(Long fieldId) {
		return null;
	}
}
