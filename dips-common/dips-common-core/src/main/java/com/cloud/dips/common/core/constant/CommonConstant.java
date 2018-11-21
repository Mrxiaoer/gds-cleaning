/*
 *
 *      Copyright (c) 2018-2025, BigPan All rights reserved.
 *
 */


package com.cloud.dips.common.core.constant;

/**
 * @author BigPan
 * @date 2017/10/29
 */
public interface CommonConstant {
	/**
	 * token请求头名称
	 */
	String REQ_HEADER = "Authorization";

	/**
	 * token分割符
	 */
	String TOKEN_SPLIT = "Bearer ";

	/**
	 * jwt签名
	 */
	String SIGN_KEY = "DIPS";
	/**
	 * 删除
	 */
	String STATUS_DEL = "1";
	/**
	 * 正常
	 */
	String STATUS_NORMAL = "0";

	/**
	 * 锁定
	 */
	String STATUS_LOCK = "9";

	/**
	 * 菜单
	 */
	String MENU = "0";

	/**
	 * 按钮
	 */
	String BUTTON = "1";

	/**
	 * 删除标记
	 */
	String DEL_FLAG = "is_deleted";

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * JSON 资源
	 */
	String CONTENT_TYPE = "application/json; charset=utf-8";

	/**
	 * 阿里大鱼
	 */
	String ALIYUN_SMS = "aliyun_sms";

	/**
	 * 路由信息Redis保存的key
	 */
	String ROUTE_KEY = "_ROUTE_KEY";
	
	/**
	 * 前端工程名
	 */
	String FRONT_END_PROJECT = "dips-ui";

	/**
	 * 后端工程名
	 */
	String BACK_END_PROJECT = "dips-cloud";
	/**
	 * 通知指示分类
	 */
	String NOTIFY_TYPE = "notify_type";
	
	/**
	 * 系统消息分类
	 * */
	String SYS_INFO_TYPE = "sys_info_type";

	/**
	 * 系统名称
	 */
	String SYSTEM_NAME = "DIPS";
	
}
