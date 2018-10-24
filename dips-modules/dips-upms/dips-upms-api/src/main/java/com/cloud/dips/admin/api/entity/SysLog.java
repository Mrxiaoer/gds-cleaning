/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */
package com.cloud.dips.admin.api.entity;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author Wilson
 * @since 2017-11-20
 */
@Data
@TableName("gov_log")
public class SysLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(value = "g_id", type = IdType.AUTO)
	private Long id;
	/**
	 * 日志类型
	 */
	@TableField("g_type")
	private String type;
	/**
	 * 日志标题
	 */
	@TableField("g_title")
	private String title;
	/**
	 * 创建者
	 */
	@TableField("g_create_by")
	private String createBy;
	/**
	 * 创建时间
	 */
	@TableField("g_create_time")
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	@TableField("g_update_time")
	private LocalDateTime updateTime;
	/**
	 * 操作IP地址
	 */
	@TableField("g_remote_addr")
	private String remoteAddr;
	/**
	 * 用户代理
	 */
	@TableField("g_user_agent")
	private String userAgent;
	/**
	 * 请求URI
	 */
	@TableField("g_request_uri")
	private String requestUri;
	/**
	 * 操作方式
	 */
	@TableField("g_method")
	private String method;
	/**
	 * 操作提交的数据
	 */
	@TableField("g_params")
	private String params;
	/**
	 * 执行时间
	 */
	@TableField("g_time")
	private Long time;

	/**
	 * 删除标记
	 */
	@TableLogic
	@TableField("g_del_flag")
	private String delFlag;

	/**
	 * 异常信息
	 */
	@TableField("g_exception")
	private String exception;

	/**
	 * 服务ID
	 */
	@TableField("g_service_id")
	private String serviceId;

}
