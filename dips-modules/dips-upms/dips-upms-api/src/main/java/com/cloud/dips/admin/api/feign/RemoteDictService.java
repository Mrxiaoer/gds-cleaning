package com.cloud.dips.admin.api.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cloud.dips.admin.api.entity.SysDict;
import com.cloud.dips.admin.api.feign.fallback.RemoteDictServiceFallbackImpl;
import com.cloud.dips.common.core.constant.ServiceNameConstant;

/**
 * @author RCG
 * @date 2018/11/19
 */
@FeignClient(value = ServiceNameConstant.UMPS_SERVICE, fallback = RemoteDictServiceFallbackImpl.class)

public interface RemoteDictService {
	

	/**
	 * 
	 * 通过类型查询字典值域列表
	 *
	 * @param type 类型
	 * 
	 * @param from 调用标志
	 * 
	 * @return R
	 * 
	 */

	@GetMapping("/dict/remoteFindDictByType/{type}")

	List<SysDict> list(@PathVariable("type") String type, @RequestHeader("from") String from);

}
