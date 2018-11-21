package com.cloud.dips.admin.api.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.cloud.dips.admin.api.entity.SysRole;

import lombok.Data;

/**
 * @author RCG
 * @date 2018/11/19
 */
@Data
public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户真实姓名
	 */
	private String realName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 随机盐
	 */
	private String salt;

	/**
	 * 微信openid
	 */
	private String weixinOpenid;

	/**
	 * QQ openid
	 */
	private String qqOpenid;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime modifiedTime;
	/**
	 * 0-正常，1-删除
	 */
	private String isDeleted;
	/**
	 * 简介
	 */
	private String phone;
	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 部门ID
	 */
	private Integer deptId;
	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 角色列表
	 */
	private List<SysRole> roleList;
}
