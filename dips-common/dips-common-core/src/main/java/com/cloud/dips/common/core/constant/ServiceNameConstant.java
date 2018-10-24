/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.common.core.constant;

/**
 * @author Wilson
 * @date 2018年07月22日16:41:01
 * 服务名称
 */
public interface ServiceNameConstant {
	/**
	 * 认证服务的SERVICEID（zuul 配置的对应）
	 */
	String AUTH_SERVICE = "dips-auth";

	/**
	 * UMPS模块
	 */
	String UMPS_SERVICE = "dips-upms";
	
	/**
	 * TMS模块
	 */
	String TMS_SERVICE = "dips-tms";
	
	/**
	 * MPMS模块
	 */
	String MPMS_SERVICE = "dips-mpms";
	
	/**
	 * WMS模块
	 */
	String WMS_SERVICE = "dips-wms";

	/**
	 * 分布式事务协调服务
	 */
	String  TX_MANAGER = "dips-tx-manager";
}
