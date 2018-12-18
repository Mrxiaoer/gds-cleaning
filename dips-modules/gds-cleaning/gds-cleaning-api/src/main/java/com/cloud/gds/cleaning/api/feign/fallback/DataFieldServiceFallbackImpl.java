package com.cloud.gds.cleaning.api.feign.fallback;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.feign.DataFieldService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author : lolilijve
 * @Email : 1042703214@qq.com
 * @Date : 2018-11-22
 */
@Slf4j
@Component
public class DataFieldServiceFallbackImpl implements DataFieldService {

	@Setter
	private Throwable cause;

	@Override
	public Page page(Map<String, Object> params) {
		log.error("feign 查询待清洗信息列表失败", cause);
		return null;
	}

	@Override
	public R info(Long id) {
		log.error("feign 查询待清洗信息列表失败:id={}", id.toString(), cause);
		return null;
	}

	@Override
	public R save(DataRule dataField) {
		log.error("feign 保存待清洗信息失败", cause);
		return null;
	}

	@Override
	public R update(DataRule dataField) {
		log.error("feign 更新待清洗信息失败", cause);
		return null;
	}

	@Override
	public R delete(Long id) {
		log.error("feign 删除待清洗信息失败", cause);
		return null;
	}
}
