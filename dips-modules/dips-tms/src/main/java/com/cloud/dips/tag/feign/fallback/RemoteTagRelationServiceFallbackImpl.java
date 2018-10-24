package com.cloud.dips.tag.feign.fallback;

import com.cloud.dips.common.core.util.R;
import com.cloud.dips.tag.feign.RemoteTagRelationService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j

@Component

public class RemoteTagRelationServiceFallbackImpl implements RemoteTagRelationService {
	@Setter
	private Throwable cause;
	
	@Override
	public R<Boolean> saveTagRelation(Map<String, Object> params) {
		log.error("feign 插入标签关联失败:{}",cause);
		return null;
	}

	@Override
	public R<Boolean> deleteTagRelation(Map<String, Object> params) {
		log.error("feign 删除标签关联失败:{}",cause);
		return null;
	}
}
