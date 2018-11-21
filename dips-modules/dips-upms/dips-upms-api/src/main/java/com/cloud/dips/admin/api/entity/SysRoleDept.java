package com.cloud.dips.admin.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色与部门对应关系
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_role_dept")
public class SysRoleDept extends Model<SysRoleDept> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 角色ID
	 */
	private Integer roleId;
	/**
	 * 部门ID
	 */
	private Integer deptId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
