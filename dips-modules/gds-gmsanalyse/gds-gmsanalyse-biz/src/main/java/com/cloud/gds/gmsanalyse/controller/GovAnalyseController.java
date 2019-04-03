package com.cloud.gds.gmsanalyse.controller;

import com.cloud.gds.gmsanalyse.dto.GovPolicyDto;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-03
 */
@RestController
@RequestMapping("/analyse")
public class GovAnalyseController {

	@PostMapping("/test")
	public String govAnalyse(@RequestBody GovPolicyDto govPolicyDto){

		return "hello wrold~";
	}

}
