package com.cloud.gds.cleaning.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 清洗池entity
 * @author yaonuan
 * @date 2018-12-6 13:46:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dataclean_data_field")
public class DataField extends Model<DataField> {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 数据名称
	 */
	private String name;
	/**
	 * 关联规则表id
	 */
	private Long ruleId;
	/**
	 * 来源部门
	 */
	private Integer deptId;
	/**
	 * 来源部门名称
	 */
	private String deptName;
	/**
	 * 来源方式
	 */
	private String methodName;
	/**
	 * 创建时间
	 */
	@JsonIgnore
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	@JsonIgnore
	private LocalDateTime modifiedTime;
	/**
	 * 创建用户
	 */
	@JsonIgnore
	private Integer createUser;
	/**
	 * 更新用户
	 */
	@JsonIgnore
	private Integer modifiedUser;
	/**
	 * 是否删除（0，未删除；1，已删除）
	 */
	@JsonIgnore
	private Integer isDeleted;
	/**
	 * 备注(备用)
	 */
	private String remark;
	/**
	 * 分析状态（0、未分析 1、正在分析 2、已分析 3、分析出错）
	 */
	private Integer analyseState;
	/**
	 * 矩阵文件名称
	 */
	@JsonIgnore
	private String matrixFile;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
