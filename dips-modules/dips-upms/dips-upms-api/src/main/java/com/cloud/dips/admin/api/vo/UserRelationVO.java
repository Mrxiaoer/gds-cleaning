package com.cloud.dips.admin.api.vo;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class UserRelationVO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 用户工号
	 */
	private String staffID;// 用户工号
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 性别
	 */
	private String gender;
	
	/**
	 * 手机
	 */
	private String phone;

	/**
	 * 入职时间
	 */
	private String startTime;
	
	/**
	 * 部门集合
	 */
	private List<DeptVO> deptList;

}
