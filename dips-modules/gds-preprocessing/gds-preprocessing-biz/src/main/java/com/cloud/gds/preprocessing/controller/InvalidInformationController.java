package com.cloud.gds.preprocessing.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.preprocessing.service.InvalidInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资讯
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-05-09
 */
@RestController
@RequestMapping("/invalid/information")
public class InvalidInformationController {


	@Autowired
	private InvalidInformationService invalidInformation;

	/**
	 * 清除爬取表中无效title text的数据
	 *
	 * @param titleLength 主题长度
	 * @param textLength  正文长度
	 * @return
	 */
	@GetMapping("/clean_title")
	public R invalidTitle(@RequestParam Integer titleLength, @RequestParam Integer textLength) {
		return new R<>(invalidInformation.cleanIssueData(titleLength, textLength));
	}

	/**
	 * 清除采集表中相同名称的数据
	 *
	 * @return
	 */
	@GetMapping("/clean_equally_title")
	public R cleanInvalidInScape() {
		return new R<>(invalidInformation.cleanInvalidInScape());
	}

	/**
	 * 爬取数据与正式库中数据进行清洗,清洗到采集表中重复的数据
	 *
	 * @return
	 */
	@GetMapping("/clean_repeat_scrapy")
	public R cleanRepeatScrapy() {
		return new R<>(invalidInformation.cleanRepeatScrapy());
	}

}
