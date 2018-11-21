package com.cloud.dips.admin.api.dto;

import java.util.List;

import com.cloud.dips.admin.api.entity.SysUser;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author RCG
 * @date 2018/11/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {
	/**
	 * 角色ID
	 */
	private List<Integer> role;

	private Integer deptId;

	/**
	 * 新密码
	 */
	private String newpassword1;
}
