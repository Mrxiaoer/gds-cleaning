/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.common.security.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.util.Map;

/**
 * @author Wilson
 * @date 2018/7/8
 * 自定义OAuth2Exception
 */
public class DipsOAuth2Exception extends OAuth2Exception {

	public DipsOAuth2Exception(String msg) {
		super(msg);
	}
}
