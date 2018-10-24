package com.cloud.dips.common.core.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.date.DateUtil;
/**
 * 上传文件工具类
 * @author WS
 *
 */
public class UploadUtils {
	
	public static Attachment uploadFile(MultipartFile file,HttpServletRequest request){
		
		Attachment attachment=null;
		String fileName=file.getOriginalFilename();
		if(!file.isEmpty()){
			//存储路径
			String fileUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/doc/";
			System.out.println("存储路径fileUrl:"+fileUrl);
			//文件存储位置
			String filePath = request.getSession().getServletContext().getRealPath("upload/doc");
			System.out.println("存储位置filePath:"+filePath+fileName);
            //先判断文件夹是否存在,不存在则创建
            //String dat = DateUtil.format(new Date(),"yyyyMMdd");
            File dir =new File(filePath);    
            if(!dir.exists()  && !dir.isDirectory()){       
                dir.mkdirs();  
            }
           attachment=new Attachment();
           attachment.setUrl(fileUrl+"/");
           attachment.setName(fileName);
           attachment.setPath(filePath+"/"+fileName);
           try {
        	   file.transferTo(new File(dir, fileName));
           } catch (IllegalStateException | IOException e) {
        	   e.printStackTrace();
           }
		}
		return attachment;
	}

}
