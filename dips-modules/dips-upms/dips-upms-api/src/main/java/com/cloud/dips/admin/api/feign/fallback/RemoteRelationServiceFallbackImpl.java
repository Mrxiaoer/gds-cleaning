package com.cloud.dips.admin.api.feign.fallback;

import com.cloud.dips.admin.api.feign.RemoteRelationService;
import com.cloud.dips.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author C.Z.H
 */

@Slf4j
@Component
public class RemoteRelationServiceFallbackImpl implements RemoteRelationService {

	@Override
	public R<Boolean> saveRelation(Map<String, Object> params) {
		log.error("feign 插入失败");
		return null;
	}

	@Override
	public R<Boolean> deleteRelation(Map<String, Object> params) {
		log.error("feign 删除失败");
		return null;
	}
}
