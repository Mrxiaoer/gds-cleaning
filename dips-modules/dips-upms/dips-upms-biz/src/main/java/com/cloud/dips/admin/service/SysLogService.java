package com.cloud.dips.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.entity.SysLog;
import com.cloud.dips.admin.api.vo.PreLogVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
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
