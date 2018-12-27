package com.cloud.gds.cleaning.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.utils.DataPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据分析相关
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-26
 */
@RestController
@RequestMapping(value = "/analysis")
public class DataAnalysisController {

	@Autowired
	DataFieldValueService dataFieldValueService;


	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 设置阀值
	 * @param fieldId
	 * @param threshold
	 */
	@GetMapping("/set/threshold")
	public void setThreshold(@RequestParam Long fieldId,@RequestParam double threshold,@RequestParam Integer degree){
		// 分析程度degree  0、快速分析 1、深度分析
		// TODO 数据分析接口
	}

	/**
	 * 清洗
	 * @param fieldId
	 * @return
	 */
	@GetMapping("/clean")
	public R gainCleanData(@RequestParam Long fieldId){
		return new R(DataPoolUtils.listEntity2Vo(dataFieldValueService.gainCleanData(fieldId)));
	}

	/**
	 * 数据明细
	 * @param id
	 * @return
	 */
	@GetMapping("/details")
	public R gainDetails(@RequestParam Long id){
		return new R(DataPoolUtils.listEntity2Vo(dataFieldValueService.gainDetails(id)));
	}


}
