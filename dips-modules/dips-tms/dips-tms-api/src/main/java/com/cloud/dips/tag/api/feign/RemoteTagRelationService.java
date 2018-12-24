package com.cloud.dips.tag.api.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloud.dips.common.core.constant.ServiceNameConstant;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.tag.api.feign.factory.RemoteTagRelationServiceFallbackFactory;
import com.cloud.dips.tag.api.vo.CommonVO;

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
	@PostMapping("/relation/save")
	R<Boolean> saveTagRelation(@RequestParam Map<String, Object> params);

	/**
	 * 标签关联删除
	 * @param relationId
	 * @param node
	 * @return
	 */
	@PostMapping("/relation/delete")
	R<Boolean> deleteTagRelation(@RequestParam Integer relationId,@RequestParam String node);
	/**
	 * 获取关联标签
	 * @param params 参数集：node、relationId、fob（f表示前台，b表示后台）
	 * @return
	 */
	@GetMapping("/get_tags")
	public R<Map<String, List<CommonVO>>> getTags(@RequestParam Map<String, Object> params);
}
