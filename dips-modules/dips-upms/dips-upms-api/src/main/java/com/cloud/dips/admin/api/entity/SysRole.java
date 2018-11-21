package com.cloud.dips.admin.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_role")
public class SysRole extends Model<SysRole> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@NotBlank(message = "角色名称 不能为空")
	private String roleName;

	@NotBlank(message = "角色标识 不能为空")
	private String roleCode;

	@NotBlank(message = "角色描述 不能为空")
	private String roleDesc;

	private LocalDateTime createTime;
	private LocalDateTime modifiedTime;
	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@TableLogic
	private String isDeleted;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
