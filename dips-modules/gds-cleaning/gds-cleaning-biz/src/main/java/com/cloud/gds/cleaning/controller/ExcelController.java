package com.cloud.gds.cleaning.controller;

import com.alibaba.fastjson.JSON;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.DataSetVo;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataRuleService;
import com.cloud.gds.cleaning.service.ExcelService;
import com.cloud.gds.cleaning.utils.CommonUtils;
import com.cloud.gds.cleaning.utils.DataRuleUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;

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
	public List<Object[]> importExcel(MultipartFile file) {
		excelService.importExcel(103L,file );
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
