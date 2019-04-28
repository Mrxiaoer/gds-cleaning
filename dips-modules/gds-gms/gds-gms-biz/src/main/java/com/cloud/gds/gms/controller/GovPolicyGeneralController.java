package com.cloud.gds.gms.controller;

import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.gds.gms.api.entity.GovPolicyGeneral;
import com.cloud.gds.gms.service.GovPolicyGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-29
 */
@RestController
@RequestMapping("/policy/general")
public class GovPolicyGeneralController {

	private final GovPolicyGeneralService govPolicyGeneralService;

	@Autowired
	public GovPolicyGeneralController(GovPolicyGeneralService govPolicyGeneralService) {
		this.govPolicyGeneralService = govPolicyGeneralService;
	}

	/**
	 * 分页数据
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	public R page(@RequestParam Map<String, Object> params) {
		return new R<>(govPolicyGeneralService.selectAllPage(new Query<>(params)));
	}

	/**
	 * 根据条件获取title等简要信息
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/list")
	public R gainAll(@RequestParam Map<String, Object> params) {
		return new R<>(govPolicyGeneralService.gainAll(params));
	}

	/**
	 * 根据条件获取id数组
	 *
	 * @param params
	 * @return
	 */
	@PostMapping("/ids")
	public List<Long> gainList(@RequestBody Map<String, Object> params) {
		return govPolicyGeneralService.gainList(params);
	}


	/**
	 * 查询单一政策信息
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/info")
	public GovPolicyGeneral info(@RequestParam("id") Long id) {
		return govPolicyGeneralService.selectById(id);
	}


	@PostMapping("/selectByIds")
	public List<GovPolicyGeneral> selectByIds(@RequestBody List<Long> ids) {
		return govPolicyGeneralService.queryByInfos(ids);
	}


}
