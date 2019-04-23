package com.cloud.gds.gmsanalyse.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyse;
import com.cloud.gds.gmsanalyse.service.PolicyAnalyseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 政策分析controller
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-16
 */
@RestController
@RequestMapping("/policy")
public class PolicyAnalyseController {

	private final PolicyAnalyseService policyAnalyseService;

	@Autowired
	public PolicyAnalyseController(PolicyAnalyseService policyAnalyseService) {
		this.policyAnalyseService = policyAnalyseService;
	}

	/**
	 * 分页查询
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "国策分析分页", notes = "国策分析分页")
	public R page(@RequestParam Map<String, Object> params) {
		return new R<>(policyAnalyseService.queryPage(params));
	}

	/**
	 * 根据id查询单一的数据
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/info")
	@ApiOperation(value = "根据id查询", notes = "根据id查询")
	public R info(@RequestParam Long id) {
		return new R<>(policyAnalyseService.selectById(id));
	}


	/**
	 * 根据用户更新的信息进行更新
	 *
	 * @param policyAnalyse
	 * @return
	 */
	@PostMapping("/update")
	public R update(@RequestBody PolicyAnalyse policyAnalyse) {
		return new R<>(policyAnalyseService.individuationUpdate(policyAnalyse));
	}

}
