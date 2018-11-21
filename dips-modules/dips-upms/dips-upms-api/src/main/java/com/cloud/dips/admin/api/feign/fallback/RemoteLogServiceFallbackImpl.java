package com.cloud.dips.admin.api.feign.fallback;

import org.springframework.stereotype.Component;

import com.cloud.dips.admin.api.entity.SysLog;
import com.cloud.dips.admin.api.feign.RemoteLogService;
import com.cloud.dips.common.core.util.R;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author RCG
 * @date 2018/11/19
 */
@Slf4j
@Component
public class RemoteLogServiceFallbackImpl implements RemoteLogService {
	@Setter
	private Throwable cause;
	/**
	 * 保存日志
	 *
	 * @param sysLog
	 * @return R
	 */
	@Override
	public R<Boolean> saveLog(SysLog sysLog) {
		log.error("feign 插入日志失败:{}");
		return null;
	}
}
