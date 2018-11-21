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
 * 部门管理
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_dept")
public class SysDept extends Model<SysDept> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 部门名称
	 */
	@NotBlank(message = "部门名称不能为空")
	private String name;
	/**
	 * 排序
	 */
	private Integer orderNum;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime modifiedTime;
	/**
	 * 是否删除  -1：已删除  0：正常
	 */
	@TableLogic
	private String isDeleted;

	/**
	 * 父级ID
	 */
	private Integer parentId;
	
	/**
	 * 所属城市
	 */
	private Integer cityId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
