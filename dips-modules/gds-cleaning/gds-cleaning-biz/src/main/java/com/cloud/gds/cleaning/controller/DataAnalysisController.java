package com.cloud.gds.cleaning.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.service.AnalysisResultService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.utils.DataPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

	@Autowired
	AnalysisResultService analysisResultService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 设置阀值
	 * @param fieldId
	 * @param threshold
	 */
	@GetMapping("/set/threshold")
	public void setThreshold(@RequestParam Long fieldId,@RequestParam Float threshold,@RequestParam Integer degree){
		// python分析数据
		analysisResultService.dataAnalysis(fieldId,(threshold/100),degree );
	}

	/**
	 * 分析结果默认中心数据显示
	 * @param fieldId
	 * @return
	 */
	@GetMapping("/center_data")
	public R gainCleanData(@RequestParam Long fieldId){
		return new R<>(DataPoolUtils.listEntity2Vo(dataFieldValueService.gainCleanData(fieldId)));
	}

	/**
	 * 数据明细
	 * @param id
	 * @return
	 */
	@GetMapping("/details")
	public R gainDetails(@RequestParam Long id){
		return new R<>(DataPoolUtils.listEntity2Vo(dataFieldValueService.gainDetails(id)));
	}

	/**
	 * 手动过滤
	 * @param params
	 * params
	 * @return
	 */
	@PostMapping("/manual/filter")
	public R manualFilter(@RequestBody Map<String,Object> params){

		return new R();
	}


}
