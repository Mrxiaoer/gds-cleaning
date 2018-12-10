package com.cloud.dips.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.dips.admin.api.entity.SysDict;
import com.cloud.dips.admin.api.entity.SysDictValue;
import com.cloud.dips.admin.service.SysDictService;
import com.cloud.dips.admin.service.SysDictValueService;
import com.cloud.dips.common.core.excel.ExcelUtil;
import com.cloud.dips.common.core.excel.ImportExcelUntil;
import com.cloud.dips.common.core.util.R;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author ZB
 *
 */
@RestController
@RequestMapping("/excel")
public class DictExcelController {
	
	@Autowired
	private SysDictService service;
	
	@Autowired
	private SysDictValueService valueService;

	/**
	 * 字典Excel导出
	 * @param request
	 * @param response
	 */
	@GetMapping("/dict_export")
	@ApiOperation(value = "字典excel导出", notes = "字典excel导出",httpMethod="GET")
	public void dictExport(HttpServletRequest request,HttpServletResponse response) {
        // 定义表的标题
        String title = "字典列表一览";
        //定义表的列名
        String[] rowsName = new String[] { "字典编码", "字典名称", "所属系统" };
        //定义表的内容
        List<Object[]> dataList = new ArrayList<Object[]>();
        List<SysDict> dicts=service.selectList(new EntityWrapper<SysDict>());
        for(SysDict dict:dicts){
            Object[] objs = new Object[3];
            objs[0] = dict.getNumber();
            objs[1] = dict.getName();
            objs[2] = dict.getSystem();
            dataList.add(objs);	
        }
        // 创建ExportExcel对象
        ExcelUtil excelUtil = new ExcelUtil();
        try{
        	//生成word文件的文件名
            String fileName= new String("dictList.xlsx".getBytes("UTF-8"),"iso-8859-1");    
            excelUtil.exportExcel(title,rowsName,dataList,fileName,response);
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	/**
	 * 字典Excel导入
	 * @param file
	 * @return
	 */
	@PostMapping("/dict_import")
	@ApiOperation(value = "字典excel导入", notes = "字典excel导入",httpMethod="POST")
	public R<Boolean> dictImport(@RequestParam(value="file")MultipartFile file) {
		   try {
		        List<String[]> list = ImportExcelUntil.readExcel(file);
		        Integer success=0;
		        for(String[] strs:list){
		        	if(StringUtils.isNotBlank(strs[0])){
			        EntityWrapper<SysDict> e = new EntityWrapper<SysDict>();
			        e.eq("number", strs[0]);
			        if(service.selectCount(e)<1){
			        	SysDict dict=new SysDict();
			        	dict.setNumber(strs[0]);
			        	dict.setName(strs[1]);
			        	dict.setSystem(strs[2]);
			        	service.insert(dict);
			        	success +=1;
			        }
		        	}
		        	}
		        return new R<Boolean>(Boolean.TRUE,"成功导入"+success+"条字典。");
		    } catch (Exception e) {  
		        e.printStackTrace();  
		        return new R<Boolean>(Boolean.FALSE);
		    }
	}
	
	/**
	 * 字典值excel导出
	 * @param request
	 * @param response
	 */
	@GetMapping("/dict_value_export")
	@ApiOperation(value = "字典值excel导出", notes = "字典值excel导出",httpMethod="GET")
	public void dictValueExport(HttpServletRequest request,HttpServletResponse response) {
		EntityWrapper<SysDict> ed = new EntityWrapper<SysDict>();
		ed.setSqlSelect("id,number");
		List<Map<String, Object>> dictMaps=service.selectMaps(ed);
		Map<String, String> dictMap=new HashMap<String, String>(0);
		for(Map<String, Object> map:dictMaps){
			String key=map.get("id").toString();
			String value=(String) map.get("number");
			dictMap.put(key,value);
		}

		// 定义表的标题
		String title = "字典值列表一览";
		//定义表的列名
		String[] rowsName = new String[] { "字典键", "字典值", "所属字典编码", "父类id", "排序" };
		//定义表的内容
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<SysDictValue> values=valueService.selectList(new EntityWrapper<SysDictValue>());
		for(SysDictValue dictValue:values){
			Object[] objs = new Object[5];
			objs[0] = dictValue.getKey();
			objs[1] = dictValue.getValue();
			objs[2] = dictMap.get(dictValue.getDictId().toString());
			objs[3] = dictValue.getParentId();
			objs[4] = dictValue.getSort();
			dataList.add(objs);	
		}
		// 创建ExportExcel对象
		ExcelUtil excelUtil = new ExcelUtil();
		try{
			//生成word文件的文件名
			String fileName= new String("dictValueList.xlsx".getBytes("UTF-8"),"iso-8859-1");    
			excelUtil.exportExcel(title,rowsName,dataList,fileName,response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 字典值excel导入
	 * @param file
	 * @return
	 */
	@PostMapping("/dict_value_import")
	@ApiOperation(value = "字典值excel导入", notes = "字典值excel导入",httpMethod="POST")
	public R<Boolean> dictValueImport(@RequestParam(value="file")MultipartFile file) {
		EntityWrapper<SysDict> ed = new EntityWrapper<SysDict>();
		ed.setSqlSelect("id,number");
		List<Map<String, Object>> dictMaps=service.selectMaps(ed);
		Map<String, String> dictMap=new HashMap<String, String>(0);
		for(Map<String, Object> map:dictMaps){
			String value=map.get("id").toString();
			String key=(String) map.get("number");
			dictMap.put(key,value);
		}
		try {
			List<String[]> list = ImportExcelUntil.readExcel(file);
			Integer success=0;
			for(String[] strs:list){
				if(StringUtils.isNotBlank(strs[0]) && StringUtils.isNumeric(dictMap.get(strs[2]))){
					SysDictValue dictValue=new SysDictValue();
					dictValue.setKey(strs[0]);
					dictValue.setValue(strs[1]);
					dictValue.setDictId(Integer.parseInt(dictMap.get(strs[2])));
					dictValue.setParentId(Integer.parseInt(strs[3]));
					dictValue.setSort(Integer.parseInt(strs[4]));
					valueService.insert(dictValue);
					success +=1;
			}
			}
			return new R<Boolean>(Boolean.TRUE,"成功导入"+success+"条字典值。");
		}catch (Exception e) {  
			e.printStackTrace();  
			return new R<Boolean>(Boolean.FALSE);
		}
	}


}
