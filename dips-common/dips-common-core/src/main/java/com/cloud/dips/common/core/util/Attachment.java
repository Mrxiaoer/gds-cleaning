package com.cloud.dips.common.core.util;

import lombok.Data;
/**
 * 上传的附件信息
 * @author WS
 *
 */
@Data
public class Attachment {
	
	private String name;//文件名
	
	private String status;//上传状态
	
	private long uid;//文件id
	
	private String url;//服务器路径

	private String path;

}
