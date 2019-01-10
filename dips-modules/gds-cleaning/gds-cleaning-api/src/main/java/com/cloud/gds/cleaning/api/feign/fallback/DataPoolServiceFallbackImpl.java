package com.cloud.gds.cleaning.api.feign.fallback;

import com.alibaba.fastjson.JSONObject;
import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.dto.InputJsonList;
import com.cloud.gds.cleaning.api.feign.DataPoolService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@Slf4j
@Component
public class DataPoolServiceFallbackImpl implements DataPoolService {

	@Setter
	private Throwable cause;

	@Override
	public R page(Map<String, Object> params) {
		log.error("feign 查询分页失败", cause);
		return null;
	}

	@Override
	public R info(Long id) {
		log.error("feign 查询失败", cause);
		return null;
	}

	@Override
	public R save(JSONObject params, Long id) {
		log.error("feign 保存失败", cause);
		return null;
	}

	@Override
	public R update(Long id, Map<String, Object> map) {
		log.error("feign 更新失败", cause);
		return null;
	}

	@Override
	public R delete(Long id) {
		log.error("feign 删除失败", cause);
		return null;
	}

	@Override
	public R deleteByIds(Set<Long> ids) {
		log.error("feign 删除失败", cause);
		return null;
	}

	@Override
	public R jsonapi(Long id, InputJsonList inputJsonList) {
		log.error("feign api导入失败", cause);
		return null;
	}
}
