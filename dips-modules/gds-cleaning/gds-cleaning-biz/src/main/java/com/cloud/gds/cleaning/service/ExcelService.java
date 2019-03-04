package com.cloud.gds.cleaning.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author : yaonuan
 */
public interface ExcelService {
	/**
	 * 导出规则excel模板
	 *
	 * @param ruleId
	 * @param response
	 * @throws Exception
	 */
	void gainTemplate(Long ruleId, HttpServletResponse response) throws Exception;

	/**
	 * 导入清洗池
	 *
	 * @param fieldId
	 * @param file
	 * @return
	 */
	String importCleanPool(Long fieldId, MultipartFile file);

}
