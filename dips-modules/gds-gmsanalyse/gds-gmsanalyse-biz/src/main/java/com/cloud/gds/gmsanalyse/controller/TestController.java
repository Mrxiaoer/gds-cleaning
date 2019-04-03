package com.cloud.gds.gmsanalyse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-03
 */
@RestController
@RequestMapping("/analyse")
public class TestController {

	@GetMapping("/test")
	public String test(){
		return "hello wrold~";
	}

}
