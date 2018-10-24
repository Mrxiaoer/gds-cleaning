/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.common.core.exception;

import lombok.NoArgsConstructor;

/**
 * @author Wilson
 * @date 2018年06月22日16:22:03
 * 403 授权拒绝
 */
@NoArgsConstructor
public class DipsDeniedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DipsDeniedException(String message) {
		super(message);
	}

	public DipsDeniedException(Throwable cause) {
		super(cause);
	}

	public DipsDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DipsDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
