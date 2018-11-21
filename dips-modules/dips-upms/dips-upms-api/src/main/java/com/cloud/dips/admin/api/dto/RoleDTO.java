package com.cloud.dips.admin.api.dto;

import com.cloud.dips.admin.api.entity.SysRole;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author RCG
 * @date 2018/11/19
 * 角色Dto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends SysRole {
	/**
	 * 角色部门Id
	 */
	private Integer roleDeptId;

	/**
	 * 部门名称
	 */
	private String deptName;
}
