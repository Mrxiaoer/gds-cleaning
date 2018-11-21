package com.cloud.dips.admin.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.entity.SysLog;
import com.cloud.dips.admin.api.vo.PreLogVo;
import com.cloud.dips.admin.mapper.SysLogMapper;
import com.cloud.dips.admin.service.SysLogService;
import com.cloud.dips.common.core.constant.CommonConstant;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

	@Override
	public Boolean updateByLogId(Long id) {

		SysLog sysLog = new SysLog();
		sysLog.setId(id);
		sysLog.setIsDeleted(CommonConstant.STATUS_DEL);
		sysLog.setModifiedTime(LocalDateTime.now());
		return updateById(sysLog);
	}

	/**
	 * 批量插入前端错误日志
	 *
	 * @param preLogVoList 日志信息
	 * @return true/false
	 */
	@Override
	public Boolean insertLogs(List<PreLogVo> preLogVoList) {
		List<SysLog> sysLogList = preLogVoList.stream()
			.map(pre -> {
				SysLog log = new SysLog();
				log.setType(CommonConstant.STATUS_LOCK);
				log.setTitle(pre.getInfo());
				log.setException(pre.getStack());
				log.setParams(pre.getMessage());
				log.setCreateTime(LocalDateTime.now());
				log.setRequestUri(pre.getUrl());
				log.setCreateBy(pre.getUser());
				return log;
			}).collect(Collectors.toList());
		return this.insertBatch(sysLogList);
	}
}
