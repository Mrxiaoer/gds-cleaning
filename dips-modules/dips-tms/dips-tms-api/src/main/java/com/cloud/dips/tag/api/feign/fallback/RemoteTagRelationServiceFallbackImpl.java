package com.cloud.dips.tag.api.feign.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloud.dips.common.core.util.R;
import com.cloud.dips.tag.api.feign.RemoteTagRelationService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZB
 */
@Slf4j
@Component
public class RemoteTagRelationServiceFallbackImpl implements RemoteTagRelationService {
	@Setter
	private Throwable cause;

	@Override
	public R<Boolean> saveTagRelation(Map<String, Object> params) {
		log.error("feign 插入失败", cause);
		return null;
	}

	@Override
	public R<Boolean> deleteTagRelation(Integer relationId,String node) {
		log.error("feign 删除失败", cause);
		return null;
	}
}
