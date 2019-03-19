package com.cloud.gds.cleaning.api.feign.fallback;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.feign.RecycleBinService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * 回收站
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-11
 */
@Slf4j
@Component
public class RecycleBinServiceFallbackImpl implements RecycleBinService {

	@Setter
	private Throwable cause;

	@Override
	public R cleanPoolPage(Map<String, Object> params) {
		log.error("feign 分页数据失败", cause);
		return null;
	}

	@Override
	public R cleanPoolReduction(Long id) {
		log.error("feign 还原清洗池中一条数据失败", cause);
		return null;
	}

	@Override
	public R cleanPoolDelete(Long id) {
		log.error("feign 删除清洗池中的一条数据失败", cause);
		return null;
	}

	@Override
	public R dataPoolPage(Map<String, Object> params) {
		log.error("feign 回收站数据池分页失败", cause);
		return null;
	}

	@Override
	public R dataPoolReduction(Long id) {
		log.error("feign 还原数据池中一条数据失败", cause);
		return null;
	}

	@Override
	public R dataPoolReductions(Set<Long> ids) {
		log.error("feign 批量还原数据池中的数据失败", cause);
		return null;
	}

	@Override
	public R oneKeyReduction(Long id) {
		log.error("feign 一键还原数据池信息失败", cause);
		return null;
	}

	@Override
	public R dataPoolDelete(Long id) {
		log.error("feign 删除数据池一条数据失败", cause);
		return null;
	}

	@Override
	public R dataPoolDeletes(Set<Long> ids) {
		log.error("feign 批量删除数据池中数据失败", cause);
		return null;
	}

	@Override
	public R rulePoolPage(Map<String, Object> params) {
		log.error("feign 已删除规则池分页失败", cause);
		return null;
	}

	@Override
	public R rulePoolReduction(Long id) {
		log.error("feign 还原规则池一条数据失败", cause);
		return null;
	}

	@Override
	public R rulePoolDelete(Long id) {
		log.error("feign 删除规则池一条数据失败", cause);
		return null;
	}

	@Override
	public R rulePoolDeletes(Set<Long> ids) {
		log.error("feign 批量删除规则池中数据失败", cause);
		return null;
	}
}
