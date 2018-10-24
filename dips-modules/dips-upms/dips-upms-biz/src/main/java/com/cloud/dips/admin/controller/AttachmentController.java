/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.controller;


import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.dips.admin.api.entity.SysAttachment;
import com.cloud.dips.admin.service.SysAttachmentService;
import com.cloud.dips.common.log.annotation.SysLog;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 附件表 前端控制器
 * </p>
 *
 * @author RCG
 * @since 2017-11-20
 */
@RestController
@RequestMapping("/upload")
public class AttachmentController {

	/**
	 * 上传附件
	 *
	 * @param SysAttachment 附件实体类
	 * @return success/false
	 * @throws UnknownHostException 
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */         
	@SysLog("上传用户头像")
	@PostMapping("/uploadAvatar")
	@ApiOperation(value = "上传用户头像", notes = "头像以及被操作用户ID",httpMethod="POST")
	public String save(@RequestParam(value="file",required=false)MultipartFile file,@RequestParam Integer userId,HttpServletRequest request) throws UnknownHostException{		
		
		File targetFile=null;
        String msg="";//返回存储路径

        String fileName=file.getOriginalFilename();//获取文件名加后缀
        if(fileName!=null&&fileName!=""){   
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/imgs/";//存储路径
            String path = request.getSession().getServletContext().getRealPath("upload"); //文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName=new Date().getTime()+"_"+new Random().nextInt(1000)+fileF;//新的文件名
            
            //先判断文件是否存在
            String fileAdd = DateUtil.format(new Date(),"yyyyMMdd");

            File file1 =new File(path+"/imgs/"+fileAdd); 
            
            //如果文件夹不存在则创建    
            if(!file1.exists()  && !file1.isDirectory()){       
                file1.mkdir();  
            }

           targetFile = new File(file1, fileName);
           
           msg=returnUrl+fileAdd+"/"+fileName;
           SysAttachment sysAttachment=new SysAttachment();
           sysAttachment.setUrl(msg);
           sysAttachment.setUserId(userId);
           sysAttachment.setTime(new Date());
           sysAttachment.setIp(getIpAddress());
           sysAttachment.setLength(file.getSize());
           sysAttachmentService.insert(sysAttachment);

           try {
			file.transferTo(targetFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

        }
		
		return msg;
	}
	
	
	@SysLog("上传附件")
	@PostMapping("/uploadAttach")
	@ApiOperation(value = "上传附件", notes = "附件以及操作用户ID",httpMethod="POST")
	public String saveAttach(@RequestParam(value="file",required=false)MultipartFile file,@RequestParam Integer userId,HttpServletRequest request) throws UnknownHostException{		
		
		File targetFile=null;
        String msg="";//返回存储路径

        String fileName=file.getOriginalFilename();//获取文件名加后缀
        if(fileName!=null&&fileName!=""){   
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/imgs/";//存储路径
            String path = request.getSession().getServletContext().getRealPath("upload"); //文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName=new Date().getTime()+"_"+new Random().nextInt(1000)+fileF;//新的文件名
            
            //先判断文件是否存在
            String fileAdd = DateUtil.format(new Date(),"yyyyMMdd");

            File file1 =new File(path+"/"+fileF+"/"+fileAdd); 
            
            //如果文件夹不存在则创建    
            if(!file1.exists()  && !file1.isDirectory()){       
                file1.mkdir();  
            }

           targetFile = new File(file1, fileName);
           
           msg=returnUrl+fileAdd+"/"+fileName;
           SysAttachment sysAttachment=new SysAttachment();
           sysAttachment.setUrl(msg);
           sysAttachment.setUserId(userId);
           sysAttachment.setTime(new Date());
           sysAttachment.setIp(getIpAddress());
           sysAttachment.setLength(file.getSize());
           sysAttachmentService.insert(sysAttachment);

           try {
			file.transferTo(targetFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

        }
		
		return msg;
	}
	
	  
	private static String getIpAddress() throws UnknownHostException {   
        InetAddress address = InetAddress.getLocalHost(); 
        return address.getHostAddress();   
    }
	
	@Autowired
	private SysAttachmentService sysAttachmentService;
}
