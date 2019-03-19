package com.cloud.gds.preprocessing.controller;

import com.cloud.dips.common.core.util.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 爬取池无效数据清洗
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-19
 */
@RestController
@RequestMapping("/invalid")
public class InvalidPolicyController {


	public R invalidTitle(){

		return new R();
	}

}
