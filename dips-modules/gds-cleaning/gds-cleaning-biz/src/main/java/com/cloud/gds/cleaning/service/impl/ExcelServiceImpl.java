package com.cloud.gds.cleaning.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.DataSetVo;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.service.DataRuleService;
import com.cloud.gds.cleaning.service.ExcelService;
import com.cloud.gds.cleaning.utils.CommonUtils;
import com.cloud.gds.cleaning.utils.DataRuleUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
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

	private final DataRuleService dataRuleService;
	private final DataFieldService dataFieldService;
	private final DataFieldValueService dataFieldValueService;

	@Autowired
	public ExcelServiceImpl(DataRuleService dataRuleService, DataFieldService dataFieldService, DataFieldValueService dataFieldValueService) {
		this.dataRuleService = dataRuleService;
		this.dataFieldService = dataFieldService;
		this.dataFieldValueService = dataFieldValueService;
	}

	@Override
	public void gainTemplate(Long fieldId, HttpServletResponse response) throws Exception {

		DataField field = dataFieldService.selectById(fieldId);
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("数据池表");

		SortedMap<String, String> sortedMap = dataRuleService.gainRuleData(field.getRuleId());
		//生成表头信息
		buildExcelHead(workbook, sheet, field.getName(), sortedMap.size());

		//构建模板sheet里面的内容
		buildExcelTemplate(workbook, sheet, sortedMap);

		// 自定义名称 todo 暂时使用uuid后期是否考虑到需要修改 2019-3-18 11:06:22
		String fileName = CommonUtils.generateUUID() + ".xls";
		//生成excel文件
		buildExcelFile(fileName, workbook);
		//浏览器下载excel
		buildExcelDocument(fileName, workbook, response);

	}

	private void buildExcelHead(HSSFWorkbook workbook, HSSFSheet sheet, String headName, Integer columnNum) {
		HSSFRow row = sheet.createRow(0);
		row.createCell(1).setCellValue(headName);

		CellRangeAddress address = new CellRangeAddress(0, 0, 1, columnNum);
		sheet.addMergedRegion(address);
		// 设置字体居中
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		// 设置边框
		setBorder(address, sheet);

	}

	private void setBorder(CellRangeAddress address, HSSFSheet sheet) {
		// 设置单元格框线
		RegionUtil.setBorderBottom(BorderStyle.THIN, address, sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN, address, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, address, sheet);
		RegionUtil.setBorderRight(BorderStyle.THIN, address, sheet);
	}


	private void buildExcelTemplate(HSSFWorkbook workbook, HSSFSheet sheet, SortedMap<String, String> sortedMap) {
		//set cell style
		HSSFCellStyle style = setExcelStyle(workbook);
		Iterator<Map.Entry<String, String>> it = sortedMap.entrySet().iterator();
		int rowNum = 1;
		HSSFRow row = sheet.createRow(rowNum);
		HSSFRow nextRow = sheet.createRow(rowNum + 1);
		int column = 0;
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			column = column + 1;
			HSSFCell cell = row.createCell(column);
			cell.setCellValue(entry.getValue());
			cell.setCellStyle(style);
			HSSFCell nextRowCell = nextRow.createCell(column);
			nextRowCell.setCellValue(entry.getKey());
			nextRowCell.setCellStyle(style);

		}
		nextRow.setZeroHeight(true);

	}

	private HSSFCellStyle setExcelStyle(HSSFWorkbook workbook) {
		//设置日期格式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		return style;
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
	public String importCleanPool(Long fieldId, MultipartFile file) {

		List<Map<String, String>> mapList = importExcel(fieldId, file);
		if (mapList == null) {
			return "请检查规则模板是否存在问题!";
		}
		if (mapList.size() > 0) {
			return String.valueOf(dataFieldValueService.saveAllMap(fieldId, mapList));
		}
		return null;
	}

	@Override
	public void exportExcel(Long fieldId, HttpServletResponse response) throws Exception {
		DataField field = dataFieldService.selectById(fieldId);

		HSSFWorkbook workbook = new HSSFWorkbook();

		List<DataFieldValue> valueList = dataFieldValueService.selectList(new EntityWrapper<DataFieldValue>().eq("field_id", fieldId).eq("is_deleted", DataCleanConstant.FALSE));

		SortedMap<String, String> sortedMap = dataRuleService.gainRuleData(field.getRuleId());
		// excel 行最多65536,因为抬头已写3行
		double num = 65530;
		// 判断需要写多少个excel表,向上取整
		double sum = Math.ceil(valueList.size() / num);

		// 切分list数组
		List<DataFieldValue> subList;
		for (int i = 0; i < sum; i++) {
			if (valueList.size() > (int) num * (i + 1)) {
				subList = valueList.subList(i * (int) num, (int) num * (i + 1));

			} else {
				subList = valueList.subList(i * (int) num, valueList.size());
			}
			// 建议此处使用多线程,提高写入的效率
			buildExcelSheet(workbook, i, subList, sortedMap);
		}

		// 自定义名称
		String fileName = CommonUtils.generateUUID() + ".xls";
		//生成excel文件
		buildExcelFile(fileName, workbook);

		//浏览器下载excel
		buildExcelDocument(fileName, workbook, response);

	}

	private void buildExcelSheet(HSSFWorkbook workbook, int i, List<DataFieldValue> valueList, SortedMap<String, String> sortedMap) {

		HSSFSheet sheet = workbook.createSheet("数据池表第" + (i + 1) + "页");

		//构建模板sheet里面的内容
		buildExcelTemplate(workbook, sheet, sortedMap);

		//将实体数据导入excel
		buildExcelMap(sheet, valueList, sortedMap);
	}


	private void buildExcelMap(HSSFSheet sheet, List<DataFieldValue> valueList, SortedMap<String, String> sortedMap) {
//		SortedMap<String, String> sortedMap = dataRuleService.gainRuleData(ruleId);
		// 操作excel
		int rowNum = 2;
		for (DataFieldValue dataFieldValue : valueList) {
			Iterator<Map.Entry<String, String>> it = sortedMap.entrySet().iterator();
			// value -> sortedmap
			SortedMap<String, String> value = DataRuleUtils.strToSortedMap(dataFieldValue.getFieldValue());
			rowNum = rowNum + 1;
			int column = 0;
			HSSFRow row = sheet.createRow(rowNum);
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				column = column + 1;
				row.createCell(column).setCellValue(value.containsKey(entry.getKey()) ? value.get(entry.getKey()) : "");
			}

		}


	}

	private List<Map<String, String>> importExcel(Long fieldId, MultipartFile file) {
		try {
			InputStream inputStream = file.getInputStream();
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			// 获取sheet的行数
			int rows = sheet.getPhysicalNumberOfRows();

			// 获取参数所在行
			Row row = sheet.getRow(2);

			//对比规则是否与规则池中规则匹配
			SortedMap<String, String> sortedMap = rowTSortedMap(row);
			DataRule dataRule = dataRuleService.selectById(dataFieldService.selectById(fieldId).getRuleId());
			SortedMap<String, String> map = DataRuleUtils.changeSortedMap(JSON.parseArray(dataRule.getParams(), DataSetVo.class));
			boolean flag = CommonUtils.checkSortedMap(sortedMap, map);

			// flag true导入模板的规则与当前清洗池的规则一致，反之不一致
			List<Map<String, String>> mapList = new ArrayList<>();
			if (flag) {
				// 组装真实数据
				for (int i = 3; i <= rows; i++) {
					Row row1 = sheet.getRow(i);
					Map<String, String> map1 = new HashMap<>();
					for (Cell cell : row) {
						// 处理真实数据
						if (cell.getColumnIndex() == 0) {
							continue;
						}
						if (row1.getCell(cell.getColumnIndex()) == null) {
							map1.put(cell.getStringCellValue(), "");
							continue;
						}
						switch (row1.getCell(cell.getColumnIndex()).getCellTypeEnum()) {
							case NUMERIC:
								Cell id = row1.getCell(cell.getColumnIndex());
								id.setCellType(1);
								map1.put(cell.getStringCellValue(), id.getStringCellValue());
								break;
							case STRING:
								map1.put(cell.getStringCellValue(), row1.getCell(cell.getColumnIndex()).getStringCellValue());
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
				return mapList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
