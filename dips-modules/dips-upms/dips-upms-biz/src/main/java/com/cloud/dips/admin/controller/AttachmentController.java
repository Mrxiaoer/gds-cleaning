package com.cloud.dips.admin.controller;


import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.cloud.dips.admin.api.entity.SysAttachment;
import com.cloud.dips.admin.service.SysAttachmentService;
import com.cloud.dips.common.security.util.SecurityUtils;

/**
 * 上传附件 前端控制器
 *
 * @author RCG
 * @since 2017-11-20
 */
@RestController
@RequestMapping("/upload")
public class AttachmentController {

	private static final int BUFFERLEGTH = 8192;
	private final String endpoint = "";
	private final String accessKeyId = "";
	private final String accessKeySecret = "";

	private final String bucketName = "";
	private final String key = "";


	/**
	 * 上传图片，视频，文件
	 */
	@PostMapping("/uploadAvatar")
	public String save(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) throws IOException {

		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		// 获取文件名.后缀
		String fileName = file.getOriginalFilename();
		InputStream ins = file.getInputStream();
		File f = new File(fileName);
		inputStreamToFile(ins, f);

		FileInputStream is = new FileInputStream(f);
		String filePostfix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String newFileName;
		if (isImage(f)) {
			newFileName = key + "images/" + sdf.format(date) + "/";
		} else if (isVideo(f)) {
			newFileName = key + "videos/" + sdf.format(date) + "/";
		} else {
			newFileName = key + "files/" + sdf.format(date) + "/";
		}
		newFileName = newFileName +  UUID.randomUUID() + filePostfix;
		// 上传文件
		ossClient.putObject(bucketName, newFileName, is);
		String url = "//" + bucketName + "." + endpoint + "/" + newFileName;

		// 删除本地临时文件
		File del = new File(f.toURI());
		del.delete();

		// 关闭OSSClient。
		ossClient.shutdown();

		// 保存上传记录
		SysAttachment sysAttachment = new SysAttachment();
		sysAttachment.setUrl(url);
		sysAttachment.setUserId(SecurityUtils.getUser().getId());
		sysAttachment.setTime(new Date());
		sysAttachment.setIp(getIpAddress());
		sysAttachment.setLength(file.getSize());
		sysAttachmentService.insert(sysAttachment);
		// 返回上传的地址
		return url;
	}


	private static void inputStreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[BUFFERLEGTH];
			while ((bytesRead = ins.read(buffer, 0, BUFFERLEGTH)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isImage(File file) {
		try {
			// 通过ImageReader来解码这个file并返回一个BufferedImage对象
			// 如果找不到合适的ImageReader则会返回null，我们可以认为这不是图片文件
			// 或者在解析过程中报错，也返回false
			Image image = ImageIO.read(file);
			return image != null;
		} catch (IOException ex) {
			return false;
		}
	}

	private boolean isVideo(File file) {
		String reg = "(mp4|flv|avi|rm|rmvb|wmv)";
		Pattern p = Pattern.compile(reg);
		boolean boo = p.matcher(file.getName()).find();
		return boo;
	}

	private static String getIpAddress() throws UnknownHostException {
		InetAddress address = InetAddress.getLocalHost();
		return address.getHostAddress();
	}

	@Autowired
	private SysAttachmentService sysAttachmentService;

}
