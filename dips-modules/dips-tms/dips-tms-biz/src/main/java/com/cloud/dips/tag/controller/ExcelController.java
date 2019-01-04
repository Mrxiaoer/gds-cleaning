package com.cloud.dips.tag.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.dips.common.core.excel.ExcelUtil;
import com.cloud.dips.common.core.excel.ImportExcelUntil;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.core.util.TemplateUtils;
import com.cloud.dips.common.security.service.DipsUser;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.dips.tag.api.entity.GovTag;
import com.cloud.dips.tag.api.entity.GovTagLevel;
import com.cloud.dips.tag.api.entity.GovTagType;
import com.cloud.dips.tag.api.vo.CommonVO;
import com.cloud.dips.tag.api.vo.GovTagVO;
import com.cloud.dips.tag.service.GovTagLevelService;
import com.cloud.dips.tag.service.GovTagMergeRecordService;
import com.cloud.dips.tag.service.GovTagService;
import com.cloud.dips.tag.service.GovTagTypeService;

import cn.hutool.core.text.StrBuilder;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author ZB
 *
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {
	
	@Autowired
	private GovTagService service;
	
	@Autowired
	private GovTagLevelService levelService;
	
	@Autowired
	private GovTagTypeService typeService;
	
	@Autowired
	private GovTagMergeRecordService mergeRecordservice;

	
	@GetMapping("/export")
	@ApiOperation(value = "excel导出", notes = "excel导出",httpMethod="GET")
	public void export(HttpServletRequest request,HttpServletResponse response) {
        // 定义表的标题
        String title = "标签列表一览";
        //定义表的列名
        String[] rowsName = new String[] { "ID", "标签名称", "标签级别", "所属分类", "标签注释", "来源", "状态", "应用次数", "更新时间","关联标签", "合并标签","归入标签"};
        //定义表的内容
        List<Object[]> dataList = new ArrayList<Object[]>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<GovTagVO> tags=service.getAllTag();
        for(GovTagVO tagvo:tags){
        	tagvo.addTypeNames();
            Object[] objs = new Object[12];
            objs[0] = tagvo.getTagId();
            objs[1] = tagvo.getName();
            objs[2] = tagvo.getLevelName();
            StrBuilder typeName=new StrBuilder("");
            for(String str:tagvo.getTypeNames()){
            	typeName.append(str+",");
            }
            objs[3] = typeName.toString();
            objs[4] = tagvo.getDescription();
            objs[5] = tagvo.getSystem();
            if(tagvo.getStatus()==0){
            	objs[6] = "待审核";
            }else{
            	objs[6] = "启用";
            }
            objs[7] = tagvo.getRefers();
            objs[8] = df.format(tagvo.getUpdateTime());
            StrBuilder relationTags=new StrBuilder("");
            for(CommonVO c:tagvo.getTagList()){
            	relationTags.append(c.getCommonName()+",");
            }
            objs[9] = relationTags.toString();
            
            StrBuilder mergeTags=new StrBuilder("");
            for(CommonVO c:mergeRecordservice.selectMergeTag(tagvo.getTagId())){
            	mergeTags.append(c.getCommonName()+",");
            }
            objs[10] = mergeTags.toString();
            
            StrBuilder includeTags=new StrBuilder("");
            for(CommonVO c:mergeRecordservice.selectIncludeTag(tagvo.getTagId())){
            	includeTags.append(c.getCommonName()+",");
            }
            objs[11] = includeTags.toString();
            
            dataList.add(objs);	
        }
        // 创建ExportExcel对象
        ExcelUtil excelUtil = new ExcelUtil();
        try{
        	//生成word文件的文件名
            String fileName= new String("tagList.xlsx".getBytes("UTF-8"),"iso-8859-1");    
            excelUtil.exportExcel(title,rowsName,dataList,fileName,response);
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	@PostMapping("/import")
	@ApiOperation(value = "excel导入", notes = "excel导出",httpMethod="POST")
	public R<Boolean> importExcel(@RequestParam(value="file")MultipartFile file) {
		EntityWrapper<GovTagLevel> el = new EntityWrapper<GovTagLevel>();
		el.setSqlSelect("id,name");
		List<Map<String, Object>> levelMaps=levelService.selectMaps(el);
		Map<String, Long> levelMap=new HashMap<String, Long>(0);
		for(Map<String, Object> map:levelMaps){
			String key=(String) map.get("name");
			Long value=(Long) map.get("id");
			levelMap.put(key,value);
		}
		EntityWrapper<GovTagType> et = new EntityWrapper<GovTagType>();
		et.setSqlSelect("id,name");
		List<Map<String, Object>> typeMaps=typeService.selectMaps(et);
		Map<String, Long> typeMap=new HashMap<String, Long>(0);
		for(Map<String, Object> map:typeMaps){
			String key=(String) map.get("name");
			Long value=(Long) map.get("id");
			typeMap.put(key,value);
		}
		   try {
		        List<String[]> list = ImportExcelUntil.readExcel(file);
		        StrBuilder messge=new StrBuilder("第");
		        int l=list.size();
		        Boolean b=Boolean.TRUE;
		        for(int i=0;i<l;i++){
		        	String[] strs=list.get(i);
		        	EntityWrapper<GovTag> e = new EntityWrapper<GovTag>();
		        	e.eq("name", strs[0]);
		        	if(service.selectCount(e)>0){
		        		messge.append((i+2)+",");
		        		b=Boolean.FALSE;
		        	}
		        }
		        if(b){
		        	Integer success=0;
		        	DipsUser user = SecurityUtils.getUser();
		        	for(String[] strs:list){
		        		if(StringUtils.isNotBlank(strs[0])){
			        	EntityWrapper<GovTag> e = new EntityWrapper<GovTag>();
			        	e.eq("name", strs[0]);
			        	if(service.selectCount(e)<1){
			        		GovTag tag=new GovTag();
			        		tag.setName(strs[0]);
			        		tag.setLevelId(new Long(levelMap.getOrDefault(strs[1], 0L)).intValue());
			        		List<Integer> typeIds=new ArrayList<Integer>();
			        		for(String str: strs[2].split(",")){
			        			typeIds.add(new Long(typeMap.getOrDefault(str, 0L)).intValue());
			        		}
			        		tag.setDescription(strs[3]);
			        		tag.setSystem(strs[4]);
			        		tag.setCreatorId(user.getId());
			        		service.save(tag,typeIds);
			        		success +=1;
			        	}
		        		}
		        	}
		        	return new R<Boolean>(b,"成功导入"+success+"条标签。");
		        }else{
		        	messge.append("行标签已存在！");
		        	return new R<Boolean>(b,messge.toString());
		        }
		    } catch (Exception e) {  
		        e.printStackTrace();  
		        return new R<Boolean>(Boolean.FALSE);
		    }
	}
	
	
    @PostMapping("/model_download")
    @ApiOperation(value = "模板下载", notes = "模板下载", httpMethod = "POST")
    public ResponseEntity<byte[]> modeldownload() throws IOException {
    	InputStream in  =  this.getClass().getResourceAsStream("/models/tagmodel.xlsx"); 	
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "tagmodel.xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(TemplateUtils.toByteArray(in),headers, HttpStatus.CREATED);
	}

}
