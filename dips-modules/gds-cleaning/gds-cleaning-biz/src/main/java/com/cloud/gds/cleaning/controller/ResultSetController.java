package com.cloud.gds.cleaning.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 结果集
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-19
 */
@RestController
@RequestMapping("/result_set")
public class ResultSetController {

	@Autowired
	DataFieldValueService dataFieldValueService;

	/**
	 * 结果集详情分页
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	public R page(@RequestParam Map<String, Object> params){
		return new R<>(dataFieldValueService.queryPage(params));
	}


	/**
	 * 清空数据
	 * 由于结果集中有对比清洗前数据,如果需导入不同状态数据需要->清空数据池
	 * @param fieldId 清洗池id
	 * @return
	 */
	@GetMapping("/clear")
	public R clear(@RequestParam Long fieldId){
		return new R<>(dataFieldValueService.clear(fieldId));
	}

	/**
	 * 清缓冲
	 * 由于结果集中有对比清洗前数据,如果清洗后数据与新一套数据再次进行清洗因此需要对已删除的数据进行缓冲清除
	 * @param fieldId
	 * @return
	 */
	@GetMapping("/clear_buffer")
	public R clearBuffer(@RequestParam Long fieldId){
		return new R<>(dataFieldValueService.clearBuffer(fieldId));
	}





}
