package com.cloud.gds.cleaning.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.dto.DataDto;
import com.cloud.gds.cleaning.api.vo.CenterData;
import com.cloud.gds.cleaning.api.vo.DARVo;
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
	public R setThreshold(@RequestBody Map<String, Object> params) {
		// python分析数据
		analysisResultService.dataAnalysis(params);
		return new R();
	}

	/**
	 * 分析结果中心数据显示
	 *
	 * @param fieldId
	 * @return
	 */
	@GetMapping("/center_data/{fieldId}")
	@ApiOperation(value = "中心数据显示", notes = "分析结果中心数据显示")
	public List<CenterData> gainCleanData(@PathVariable Long fieldId) {
		return dataFieldValueService.gainCleanData(fieldId);
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
	@GetMapping("center_to_satellite/{centerId}")
	@ApiOperation(value = "中心数据显示带相似度", notes = "中心数据显示带相似度")
	public List<DARVo> centerToSatellite(@PathVariable(value = "centerId") Long id) {
		return dataFieldValueService.centerToSatellite(id);
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
	@GetMapping("/automatic_cleaning/{fieldId}")
	@ApiOperation(value = "自动清洗", notes = "根据fieldId自动清洗数据")
	public R automaticCleaning(@PathVariable Long fieldId) {
		return new R<>(analysisResultService.automaticCleaning(fieldId));
	}


//	/**
//	 * 根据中心数据过滤
//	 *
//	 * @param params include centerId、screenSize
//	 * @return
//	 */
//	@PostMapping("/filter_method_center")
//	@ApiOperation(value = "根据中心数据过滤", notes = "根据中心数据过滤")
//	public List<DARVo> centerFiltration(@RequestBody DataDto dataDto) {
//		return analysisResultService.centerFiltration(dataDto.getId(), dataDto.getScreenSize());
//	}
//
//	/**
//	 * 根据非中心数据过滤
//	 *
//	 * @param dataDto
//	 * @return
//	 */
//	@PostMapping("/filter_method_non_center")
//	@ApiOperation(value = "根据非中心数据过滤", notes = "根据非中心数据过滤")
//	public List<DARVo> nonCentralFiltration(@RequestBody DataDto dataDto) {
//		return analysisResultService.nonCentralFiltration(dataDto.getId(), dataDto.getScreenSize());
//	}
//
//	/**
//	 * 自定义中心过滤
//	 *
//	 * @param dataDto
//	 * @return
//	 */
//	@PostMapping("/filter_method_new_center")
//	@ApiOperation(value = "自定义中心过滤", notes = "自定义中心过滤")
//	public List<DARVo> newCenterPointFiltration(@RequestBody DataDto dataDto) {
//		return analysisResultService.centerPointFiltration(dataDto);
//	}

	/**
	 * 整体过滤接口、此接口数据不完善
	 *
	 * @param dataDto
	 * @return
	 */
	@PostMapping("/filter_method")
	@ApiOperation(value = "数据过滤", notes = "此接口有问题")
	public Map<String,Object> filterMethod(@RequestParam String type, @RequestBody DataDto dataDto) {
		if ("center".equals(type)) {
			System.out.println("请求中心数据接口");
			return analysisResultService.centerFiltration(dataDto.getId(), dataDto.getScreenSize());
		} else if ("non_center".equals(type)) {
			System.out.println("请求非中心数据接口");
			return analysisResultService.nonCentralFiltration(dataDto.getId(), dataDto.getScreenSize());
		} else if ("new_center".equals(type)) {
			System.out.println("请求新中心数据接口");
			return analysisResultService.centerPointFiltration(dataDto);
		}
		return null;
	}


}
