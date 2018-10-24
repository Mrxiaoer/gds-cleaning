/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.entity.SysLog;
import com.cloud.dips.admin.api.vo.PreLogVo;

import java.util.List;
/**
 * <p>
 * 日志表 服务类
 * </p>
 *
 * @author Wilson
 * @since 2017-11-20
 */
public interface SysLogService extends IService<SysLog> {

	/**
	 * 通过ID删除日志（逻辑删除）
	 *
	 * @param id 日志ID
	 * @return true/false
	 */
	Boolean updateByLogId(Long id);
	

	/**
	 * 批量插入前端错误日志
	 *
	 * @param preLogVoList 日志信息
	 * @return true/false
	 */
	Boolean insertLogs(List<PreLogVo> preLogVoList);
}
