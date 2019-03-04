package com.cloud.gds.cleaning.controller;

import com.cloud.gds.cleaning.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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


	@PostMapping("/importExcel")
	public String importExcel(MultipartFile file) {
		String str = excelService.importCleanPool(103L, file);
		return null;
	}

	/**
	 * 导出规则模板
	 *
	 * @param id       规则id
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/getTemplate/{id}")
	public void create(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
		excelService.gainTemplate(id, response);
	}


}
