package com.cloud.gds.preprocessing.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.preprocessing.service.DataDisposeService;
import com.cloud.gds.preprocessing.service.InvalidInformationService;
import io.swagger.annotations.ApiOperation;
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

	private final InvalidInformationService invalidInformation;
	private final DataDisposeService dataDisposeService;

	@Autowired
	public InvalidInformationController(InvalidInformationService invalidInformation, DataDisposeService dataDisposeService) {
		this.invalidInformation = invalidInformation;
		this.dataDisposeService = dataDisposeService;
	}

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

	/**
	 * 国策数据迁移
	 *
	 * @param examineUserId 用户自定义的审核人id
	 * @return
	 */
	@GetMapping("/transfer")
	@ApiOperation(value = "国策数据清洗完成之后迁移", notes = "国策数据清洗完成之后迁移")
	public R dataMigrationSurface(@RequestParam Long examineUserId) {
		return new R<>(dataDisposeService.dataMigrationSurfaceInformation(examineUserId));
	}


}
