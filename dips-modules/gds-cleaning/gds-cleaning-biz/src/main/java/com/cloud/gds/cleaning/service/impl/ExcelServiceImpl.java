package com.cloud.gds.cleaning.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.DataSetVo;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataRuleService;
import com.cloud.gds.cleaning.service.ExcelService;
import com.cloud.gds.cleaning.utils.CommonUtils;
import com.cloud.gds.cleaning.utils.DataRuleUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * excel导入导出
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-02-28
 */
@Service
public class ExcelServiceImpl implements ExcelService {

	@Autowired
	private DataRuleService dataRuleService;

	@Autowired
	private DataFieldService dataFieldService;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void gainTemplate(Long ruleId, HttpServletResponse response) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("规则模板表");
//		createTitle(workbook, sheet);
		SortedMap<String, String> sortedMap = dataRuleService.gainRuleData(ruleId);
		//设置日期格式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		Iterator<Map.Entry<String, String>> it = sortedMap.entrySet().iterator();
		int rowNum = 1;
		HSSFRow row = sheet.createRow(rowNum);
		HSSFRow nextRow = sheet.createRow(rowNum + 1);
		int column = 0;
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			column = column + 1;
			row.createCell(column).setCellValue(entry.getValue());
			nextRow.createCell(column).setCellValue(entry.getKey());
		}
		nextRow.setZeroHeight(true);
		String fileName = CommonUtils.generateUUID() + ".xls";

		//生成excel文件
		buildExcelFile(fileName, workbook);
		//浏览器下载excel
		buildExcelDocument(fileName, workbook, response);

	}


	private void buildExcelFile(String filename, HSSFWorkbook workbook) throws Exception {
		FileOutputStream fos = new FileOutputStream(filename);
		workbook.write(fos);
		fos.flush();
		fos.close();
	}


	private void buildExcelDocument(String filename, HSSFWorkbook workbook, HttpServletResponse response) throws Exception {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

	@Override
	public void importExcel(Long fieldId, MultipartFile file){
		log.info("导入解析开始，fileName:{}", file.getOriginalFilename());
		try {
			List<Object[]> list = new ArrayList<>();
			InputStream inputStream = file.getInputStream();
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			// 获取sheet的行数
			int rows = sheet.getPhysicalNumberOfRows();

			// 获取参数
			Row row = sheet.getRow(2);

			//对比规则是否与规则池中规则匹配
			SortedMap<String, String> sortedMap = rowTSortedMap(row);

			DataRule dataRule = dataRuleService.selectById(dataFieldService.selectById(fieldId).getRuleId());
			SortedMap<String, String> map = DataRuleUtils.changeSortedMap(JSON.parseArray(dataRule.getParams(), DataSetVo.class));
			boolean flag = CommonUtils.checkSortedMap(sortedMap, map);

			// TODO 如果flag等于true继续进行

			System.out.println(row.getCell(2).getStringCellValue());
//			for (Cell cell : row) {
//				System.out.println(cell.getColumnIndex());
//				// 控制第二列开始读取
//				if (cell.getColumnIndex() == 0) {
//					continue;
//				}
//				basemap.put(cell.getStringCellValue(), String.valueOf(cell.getColumnIndex()));
//			}
			// 组装真实数据
			List<Map<String,String>> mapList = new ArrayList<>();
			for (int i = 2; i < rows; i++) {
				if (i == 2) {
					continue;
				}
				Row row1 = sheet.getRow(i);
				Map<String,String> map1 = new HashMap<>();
				for (Cell cell : row) {
					// 处理真实数据
					if (cell.getColumnIndex() == 0) {
						continue;
					}
					if (row1.getCell(cell.getColumnIndex()) == null){
						map1.put(cell.getStringCellValue(), "");
						continue;
					}
					switch (row1.getCell(cell.getColumnIndex()).getCellTypeEnum()){
						case NUMERIC:
							map1.put(cell.getStringCellValue(), String.valueOf(row1.getCell(cell.getColumnIndex()).getNumericCellValue()));
							break;
						case STRING:
							map1.put(cell.getStringCellValue(),row1.getCell(cell.getColumnIndex()).getStringCellValue());
							break;
						case BOOLEAN:
							map1.put(cell.getStringCellValue(), String.valueOf(row1.getCell(cell.getColumnIndex()).getBooleanCellValue()));
							break;
						case ERROR:
							map1.put(cell.getStringCellValue(), String.valueOf(row1.getCell(cell.getColumnIndex()).getErrorCellValue()));
							break;
						default:
							map1.put(cell.getStringCellValue(), "");
					}
				}
				mapList.add(map1);
			}
			System.out.println(mapList);
			log.info("已执行到--->119col" );
			// TODO 批量处理数据



		} catch (Exception e) {
			e.printStackTrace();
		}
//		return null;
	}

	private SortedMap<String, String> rowTSortedMap(Row row) {
		SortedMap<String, String> sortedMap = new TreeMap<>();
		for (Cell cell : row) {
			if (cell.getColumnIndex() == 0) {
				continue;
			}
			sortedMap.put(cell.getStringCellValue(), cell.getStringCellValue());
		}
		return sortedMap;
	}

}
