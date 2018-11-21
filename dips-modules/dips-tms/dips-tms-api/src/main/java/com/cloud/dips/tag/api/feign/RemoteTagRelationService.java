package com.cloud.dips.tag.api.feign;

import com.cloud.dips.common.core.constant.ServiceNameConstant;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.tag.api.feign.factory.RemoteTagRelationServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author ZB
 */
@FeignClient(value = ServiceNameConstant.TMS_SERVICE, fallbackFactory = RemoteTagRelationServiceFallbackFactory.class)
public interface RemoteTagRelationService {

	/**
	 * 标签关联保存
	 * @param params 参数集
	 * @return R
	 */
	@PostMapping("/tagRelation/saveTagRelation")
	R<Boolean> saveTagRelation(@RequestParam Map<String, Object> params);

	/**
	 * 标签关联删除
	 * @param params 参数集
	 * @return R
	 */
	@PostMapping("/tagRelation/deleteTagRelation")
	R<Boolean> deleteTagRelation(@RequestParam Map<String, Object> params);
}
