package com.cloud.gds.cleaning.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.api.vo.DataSetVo;
import com.cloud.gds.cleaning.utils.DataRuleUtils;
import org.apache.poi.hssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.util.*;

@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DataRuleServiceTest {

	@Autowired
	DataRuleService dataRuleService;

	public static SortedMap<String, String> changeSortedMap(List<DataSetVo> dataSetVos) {
		SortedMap<String, String> sortedMap = new TreeMap<>();
		for (DataSetVo vo : dataSetVos) {
			if (vo.getLabel() != "" || vo.getLabel().trim() != "") {
				sortedMap.put(vo.getLabel(), vo.getLabel());
			}
		}
		return sortedMap;
	}

	public static Boolean checkSortedMap(SortedMap<String, String> one, SortedMap<String, String> two) {
		Set<Map.Entry<String, String>> es = one.entrySet();
		Iterator<Map.Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			String k = (String) entry.getKey();
			if (two.containsKey(k)) {
				two.remove(k);
			} else {
				return false;
			}
		}
		if (two.size() > 0) {
			return false;
		}
		return true;
	}

	@Test
	public void deleteById() {
		System.out.println(dataRuleService.deleteById(7L));
	}

	@Test
	public void deleteByIds() {
		Set<Long> ids = new HashSet<>();
		ids.add(1L);
		ids.add(2L);
		ids.add(3L);
		dataRuleService.deleteByIds(ids);

	}

	@Test
	public void save() {
		DataRuleVo dataRuleVo = new DataRuleVo();
		dataRuleVo.setName("小二规则1214");
		dataRuleService.save(dataRuleVo);
	}

	@Test
	public void check() {
		DataRule one = dataRuleService.selectById(4);
		DataRule two = dataRuleService.selectById(5);
		DataRuleVo oneVo = DataRuleUtils.po2Vo(one);
		DataRuleVo twoVo = DataRuleUtils.po2Vo(two);
		List<DataSetVo> listone = oneVo.getDetail();
		List<DataSetVo> listtwo = twoVo.getDetail();
		SortedMap<String, String> sorteOne = changeSortedMap(listone);
		SortedMap<String, String> sorteTwo = changeSortedMap(listtwo);
		Boolean flag = checkSortedMap(sorteTwo, sorteOne);
		System.out.println(flag);

	}

	@Test
	public void queryPage() {
		Map<String, Object> map = new HashMap<>();
		map.put("page", 1);
		map.put("limit", 10);
		map.put("isAsc", true);
//		map.put("name", "小二");
		Page page = dataRuleService.queryPage(map);
		System.out.println(page);

	}

	@Test
	public void gainUpperPower() {

		Long ruleId = 8L;
		DataSetVo dataSetVo = dataRuleService.gainUpperPower(ruleId);
		System.out.println(dataSetVo);

	}

	@Test
	public void sortmap() {
		DataRule dataRule = dataRuleService.selectById(10L);
		List<DataSetVo> dataSetVos = JSON.parseArray(dataRule.getParams(), DataSetVo.class);
		SortedMap<String, String> sortedMap = new TreeMap<>();
		for (DataSetVo vo : dataSetVos) {
			if (!"".equals(vo.getLabel()) || "".equals(vo.getLabel().trim())) {
				sortedMap.put(vo.getProp(), vo.getLabel());
			}
		}
	}

	@Test
	public void create() throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("规则模板表");
		createTitle(workbook, sheet);
		SortedMap<String, String> sortedMap = dataRuleService.gainRuleData(10L);
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
		String fileName = "导出规则例子.xls";

		buildExcelFile(fileName, workbook);


	}

	private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet) {
		HSSFRow row = sheet.createRow(0);
		//设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
		sheet.setColumnWidth(1, 12 * 256);
		sheet.setColumnWidth(3, 17 * 256);

		//设置为居中加粗
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBold(true);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);

		HSSFCell cell;
		cell = row.createCell(0);
		cell.setCellValue("ID");
		cell.setCellStyle(style);


		cell = row.createCell(1);
		cell.setCellValue("显示名");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("用户名");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("创建时间");
		cell.setCellStyle(style);
	}

	public void buildExcelFile(String filename, HSSFWorkbook workbook) throws Exception {
		FileOutputStream fos = new FileOutputStream(filename);
		workbook.write(fos);
		fos.flush();
		fos.close();
	}


}
