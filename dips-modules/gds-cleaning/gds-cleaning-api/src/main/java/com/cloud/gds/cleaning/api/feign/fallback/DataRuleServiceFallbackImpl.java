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
		log.error("feign 分页查询失败", cause);
		return null;
	}

	@Override
	public DataRuleVo info(Long id) {
		log.error("feign 查看规则详情失败", cause);
		return null;
	}

	@Override
	public R selectAll() {
		log.error("feign 查看规则列表失败", cause);
		return null;
	}

	@Override
	public List<LabelVo> getKey(Long id) {
		log.error("feign 获取规则动态参数失败", cause);
		return null;
	}

	@Override
	public R save(DataRuleVo dataRuleVo) {
		log.error("feign 保存规则失败", cause);
		return null;
	}

	@Override
	public R update(DataRuleVo dataRuleVo) {
		log.error("feign 更新规则失败", cause);
		return null;
	}

	@Override
	public R delete(Long id) {
		log.error("feign 单一删除失败", cause);
		return null;
	}

	@Override
	public R deleteT(Set<Long> ids) {
		log.error("feign 批量删除失败", cause);
		return null;
	}
}
