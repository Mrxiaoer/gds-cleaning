package com.cloud.gds.gms.controller;

import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.gds.gms.service.GovPolicyGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/page")
	public R page(@RequestParam Map<String,Object> params){
		return new R<>(govPolicyGeneralService.selectAllPage(new Query<>(params)));
	}

	public R gainAll(){

		return new R();
	}



}
