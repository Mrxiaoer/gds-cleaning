package com.cloud.gds.gms.api.fegin;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.gms.api.constant.GmsConstant;
import com.cloud.gds.gms.api.entity.GovPolicyGeneral;
import com.cloud.gds.gms.api.fegin.factory.GovPolicyGeneralServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-17
 */
@FeignClient(value = GmsConstant.MODULE_NAME, path = "/policy/general", fallbackFactory = GovPolicyGeneralServiceFallbackFactory.class)
public interface RemoteGovPolicyGeneralService {

	/**
	 * 根据条件获取title等简要信息
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/list")
	R gainAll(@RequestParam Map<String, Object> params);

	/**
	 * 根据条件获取id数组
	 *
	 * @param params
	 * @return
	 */
	@PostMapping("/ids")
	List<Long> gainList(@RequestBody Map<String, Object> params);


	/**
	 * 根据条件查询政策信息
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/info")
	GovPolicyGeneral info(@RequestParam("id") Long id);
}
