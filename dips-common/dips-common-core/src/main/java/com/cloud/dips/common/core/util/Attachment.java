package com.cloud.dips.common.core.util;

import lombok.Data;
/**
 * 上传的附件信息
 * @author WS
 *
 */
@Data
public class Attachment {
	/**
     * 文件名
     */
	private String name;
	/**
     * 上传状态
     */
	private String status;
	/**
     * id
     */
	private long uid;
	/**
     * 服务器路径
     */
	private String url;
	/**
     * path
     */
	private String path;

}
