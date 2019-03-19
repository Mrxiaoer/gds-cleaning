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
	 * @param fieldId
	 * @param response
	 * @throws Exception
	 */
	void gainTemplate(Long fieldId, HttpServletResponse response) throws Exception;

	/**
	 * 导入数据池
	 *
	 * @param fieldId
	 * @param file
	 * @return
	 */
	String importCleanPool(Long fieldId, MultipartFile file);

	/**
	 * 导出数据池
	 *
	 * @param fieldId
	 * @param response
	 */
	void exportExcel(Long fieldId, HttpServletResponse response) throws Exception;

}
