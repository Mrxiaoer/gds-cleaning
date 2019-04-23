package com.cloud.gds.gmsanalyse.controller;

import com.cloud.gds.gmsanalyse.dto.GovPolicyDto;
import com.cloud.gds.gmsanalyse.service.GovAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-03
 */
@RestController
@RequestMapping("/gov")
public class GovAnalyseController {


	private final GovAnalyseService govAnalyseService;

	@Autowired
	public GovAnalyseController(GovAnalyseService govAnalyseService) {
		this.govAnalyseService = govAnalyseService;
	}

	/**
	 * 政策分析接口
	 *
	 * @param govPolicyDto
	 * @return
	 */
	@PostMapping("/analyse")
	public void govAnalyse(@RequestBody GovPolicyDto govPolicyDto) throws IOException, ClassNotFoundException {
		govAnalyseService.govAnalyse(govPolicyDto);
	}

}
