package com.cloud.gds.cleaning.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.service.CalculateService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.utils.DataPoolUtils;
import hammerlab.iterator.group;
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

	@Autowired
	CalculateService calculateService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 设置阀值
	 * @param fieldId
	 * @param threshold
	 */
	@GetMapping("/set/threshold")
	public void setThreshold(@RequestParam Long fieldId,@RequestParam double threshold,@RequestParam Integer degree){
		// 分析程度degree  1、快速分析 2、深度分析
		String fileUrl = dataFieldValueService.getAnalysisData(fieldId);
		//  数据分析接口
		String result =  calculateService.Similarity(degree, fileUrl);

//		String result = "[{"id":1," group ":[{"id":1,"similarity":1},{"id":2,"similarity":0.709008778084403}]},{"id":1525,"group":[{"id":1525,"similarity":1},"id":1526,"similarity":0.7328392218049825},{"id":1601,"similarity":0.795443077662214},{"id":1611,"similarity":0.795443077662214},{"id":1725,"similarity":0.5546353317889164},{"id":2077,"similarity":0.691628594788375},{"id":2078,"similarity":0.6820620141175615},"id":2079,"similarity":0.6858237655151436},{"id":2081,"similarity":0.5946353317889165},{"id":2657,"similarity":0.82}]},{"id":2667,"group":[{"id":2667,"similarity":1},{"id":2668,"similarity":0.9},{"id":2669,"similarity":0.7},{"id":2670,"similarity":0.7499999999999999}]}]"


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
