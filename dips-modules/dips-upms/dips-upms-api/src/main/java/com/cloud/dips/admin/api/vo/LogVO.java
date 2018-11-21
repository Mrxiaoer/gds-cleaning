package com.cloud.dips.admin.api.vo;

import java.io.Serializable;

import com.cloud.dips.admin.api.entity.SysLog;

import lombok.Data;

/**
 * @author RCG
 * @date 2018/11/19
 */
@Data
public class LogVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private SysLog sysLog;
	private String username;
}
