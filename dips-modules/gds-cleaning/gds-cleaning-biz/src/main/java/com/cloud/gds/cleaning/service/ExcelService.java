package com.cloud.gds.cleaning.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface ExcelService {
	/**
	 * 导出规则模板
	 *
	 * @param ruleId
	 * @param response
	 * @throws Exception
	 */
	void gainTemplate(Long ruleId, HttpServletResponse response) throws Exception;


	void importExcel(Long fieldId, MultipartFile file);
}
