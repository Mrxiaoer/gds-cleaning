package com.cloud.dips.admin.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author RCG
 * @date 2018/11/19
 * 部门树
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends TreeNode {
	private String name;
}
