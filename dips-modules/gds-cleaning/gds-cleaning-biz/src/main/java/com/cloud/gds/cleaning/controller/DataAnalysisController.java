package com.cloud.gds.cleaning.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.service.AnalysisResultService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.utils.DataPoolUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

	private final DataFieldValueService dataFieldValueService;
	private final AnalysisResultService analysisResultService;

	@Autowired
	public DataAnalysisController(DataFieldValueService dataFieldValueService,
								  AnalysisResultService analysisResultService) {
		this.dataFieldValueService = dataFieldValueService;
		this.analysisResultService = analysisResultService;
	}

	/**
	 * 设置阀值
	 *
	 * @param params 其中包括：fieldId  threshold 阀值 degree快速、深度
	 */
	@PostMapping("/set/threshold")
	@ApiOperation(value = "设置阀值", notes = "设置阀值")
	public void setThreshold(@RequestBody Map<String,Object> params) {
		// python分析数据
		analysisResultService.dataAnalysis(params);
	}

	/**
	 * 分析结果中心数据显示
	 *
	 * @param fieldId
	 * @return
	 */
	@GetMapping("/center_data")
	@ApiOperation(value = "中心数据显示", notes = "分析结果中心数据显示")
	public R gainCleanData(@RequestParam Long fieldId) {
		return new R<>(dataFieldValueService.gainCleanData(fieldId));
	}

	/**
	 * 数据明细
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/details")
	@ApiOperation(value = "数据明细", notes = "数据明细")
	public R gainDetails(@RequestParam(value = "fieldId") Long id) {
		return new R<>(DataPoolUtils.listEntity2Vo(dataFieldValueService.gainDetails(id)));
	}

	/**
	 * 根据中心数据查看卫星数据的百分比
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("center_to_satellite")
	@ApiOperation(value = "中心数据显示带相似度", notes = "中心数据显示带相似度")
	public R centerToSatellite(@RequestParam(value = "fieldId") Long id) {
		return new R<>(dataFieldValueService.centerToSatellite(id));
	}

	/**
	 * 清洗数据(清除接口)
	 *
	 * @param params
	 * @return
	 */
	@PostMapping("/clean")
	@ApiOperation(value = "清洗数据", notes = "清洗数据")
	public R cleanDate(@RequestBody List<Map<String, Object>> params) {
		return new R<>(dataFieldValueService.cleanDate(params));
	}

	/**
	 * 自动清洗
	 *
	 * @param fieldId
	 * @return
	 */
	@GetMapping("/automatic_cleaning")
	@ApiOperation(value = "自动清洗", notes = "根据fieldId自动清洗数据")
	public R automaticCleaning(@RequestParam Long fieldId) {
		return new R<>(analysisResultService.automaticCleaning(fieldId));
	}


	@PostMapping("/filter_method_one")
	public R filterMethodOne(@RequestBody Map<String,Object> params){

		return new R<>();
	}

	@PostMapping("/filter_method_two")
	public R filterMethodTwo(@RequestBody Map<String,Object> params){

		return new R<>();
	}

	@PostMapping("/filter_method_three")
	public R filterMethodThree(@RequestBody Map<String,Object> params){

		return new R<>();
	}



}
