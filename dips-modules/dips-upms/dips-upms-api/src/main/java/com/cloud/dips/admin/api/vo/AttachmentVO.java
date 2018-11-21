/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author Wilson
 * @date 2017/11/20
 */
@Data
public class AttachmentVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 上传用户ID
	 */
	private Integer userId;
	/**
	 * 附件服务器路径
	 */
	private String url;
	/**
	 * 文件长度
	 */
	private Long length;
	/**
	 * ip
	 */
	private String ip;
	/**
	 * 附件上传时间
	 */
	private Date time;
}
