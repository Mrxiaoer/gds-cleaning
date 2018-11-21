package com.cloud.dips.admin.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_menu")
public class SysMenu extends Model<SysMenu> {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@NotNull(message = "菜单ID不能为空")
	@TableId(value = "id", type = IdType.INPUT)
	private Integer id;
	/**
	 * 菜单名称
	 */
	@NotBlank(message = "菜单名称不能为空")
	private String name;
	/**
	 * 菜单权限标识
	 */
	private String permission;
	/**
	 * 父菜单ID
	 */
	private Integer parentId;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * VUE页面
	 */
	private String component;
	/**
	 * 排序值
	 */
	private Integer sort;
	/**
	 * 菜单类型 （0菜单 1按钮）
	 */
	private String type;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime modifiedTime;
	/**
	 * 0--正常 1--删除
	 */
	@TableLogic
	private String isDeleted;
	/**
	 * 前端URL
	 */
	private String path;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
