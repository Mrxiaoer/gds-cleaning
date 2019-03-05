package com.cloud.gds.cleaning.controller;

import com.cloud.gds.cleaning.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * ecxel
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-02-27
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

	private final ExcelService excelService;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public ExcelController(ExcelService excelService) {
		this.excelService = excelService;
	}


	/**
	 * 导出规则模板
	 *
	 * @param id       规则id
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/getTemplate/{id}")
	public void getTemplate(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
		excelService.gainTemplate(id, response);
	}

	/**
	 * 数据导入数据池
	 *
	 * @param fieldId
	 * @param file
	 * @return
	 */
	@PostMapping("/importExcel/{fieldId}")
	public String importExcel(@PathVariable("fieldId") Long fieldId, MultipartFile file) {
		String str = excelService.importCleanPool(fieldId, file);
		return str;
	}

	/**
	 * 数据导出数据池
	 *
	 * @param fieldId
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/exportExcel/{fieldId}")
	public void exportExcel(@PathVariable("fieldId") Long fieldId, HttpServletResponse response) throws Exception {
		excelService.exportExcel(fieldId, response);
	}


}
