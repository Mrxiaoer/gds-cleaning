/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author DingBin
 * @date 2018/1/20
 * 城市树
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CityTree extends TreeNode {
	private String name;
}
