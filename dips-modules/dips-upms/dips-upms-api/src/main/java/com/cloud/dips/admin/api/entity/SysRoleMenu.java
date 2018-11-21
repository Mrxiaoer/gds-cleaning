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
 * 角色菜单表
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_role_menu")
public class SysRoleMenu extends Model<SysRoleMenu> {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@TableId(type = IdType.INPUT)
	private Integer roleId;
	/**
	 * 菜单ID
	 */
	@TableId(type = IdType.INPUT)
	private Integer menuId;

	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}
}
