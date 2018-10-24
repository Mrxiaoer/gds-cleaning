package com.cloud.dips.admin.api.feign;

import com.cloud.dips.admin.api.feign.fallback.RemoteRelationServiceFallbackImpl;
import com.cloud.dips.common.core.constant.ServiceNameConstant;
import com.cloud.dips.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author C.Z.H
 *
 */
@FeignClient(value = ServiceNameConstant.UMPS_SERVICE, fallback = RemoteRelationServiceFallbackImpl.class)

public interface RemoteRelationService {

	@PostMapping("/relation/save")
	R<Boolean> saveRelation(@RequestParam Map<String, Object> params);

	@PostMapping("/relation/delete")
	R<Boolean> deleteRelation(@RequestParam Map<String, Object> params);

}
