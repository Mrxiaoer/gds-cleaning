package com.cloud.gds.cleaning.api.feign;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.dto.DataDto;
import com.cloud.gds.cleaning.api.feign.factory.DataAnalysisServiceFallbackFactory;
import com.cloud.gds.cleaning.api.vo.CenterData;
import com.cloud.gds.cleaning.api.vo.DARVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@FeignClient(value = DataCleanConstant.MODULE_NAME, path = "/analysis", fallbackFactory =
	DataAnalysisServiceFallbackFactory.class)
public interface DataAnalysisService {

	/**
	 * 设置阀值
	 *
	 * @param params 其中包括：fieldId  threshold 阀值 degree快速、深度
	 */
	@PostMapping("/set/threshold")
	R setThreshold(@RequestBody Map<String, Object> params);

	/**
	 * 分析结果中心数据显示
	 *
	 * @param fieldId
	 * @return
	 */
	@GetMapping("/center_data/{fieldId}")
	List<CenterData> gainCleanData(@PathVariable(value = "fieldId") Long fieldId);

	/**
	 * 数据明细
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/details")
	R gainDetails(@RequestParam(value = "fieldId") Long id);

	/**
	 * 根据中心数据查看卫星数据的百分比
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/center_to_satellite/{centerId}")
	List<DARVo> centerToSatellite(@PathVariable(value = "centerId") Long id);

	/**
	 * 清洗数据(清除接口)
	 *
	 * @param params
	 * @return
	 */
	@PostMapping("/clean")
	R cleanDate(@RequestBody List<Map<String, Object>> params);

	/**
	 * 自动清洗
	 *
	 * @param fieldId
	 * @return
	 */
	@GetMapping("/automatic_cleaning/{fieldId}")
	R automaticCleaning(@PathVariable(value = "fieldId") Long fieldId);

	/**
	 * 数据过滤接口
	 *
	 * @param dataDto
	 * @return
	 */
	@PostMapping("/filter_method")
	Map<String, Object> filterMethod(@RequestParam String type, @RequestBody DataDto dataDto);

	/**
	 * 大数据分块分析(自留地)
	 *
	 * @param filePath
	 * @return jsonString
	 */
	@GetMapping("/big_data_analysis")
	String bigDataAnalysis(@RequestParam("filePath") String filePath);

}
