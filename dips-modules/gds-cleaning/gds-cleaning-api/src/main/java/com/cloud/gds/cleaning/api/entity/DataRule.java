package com.cloud.gds.cleaning.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户规则
 *
 * @author lolilijve
 * @date 2018-12-03 15:58:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dataclean_data_rule")
public class DataRule extends Model<DataRule> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 规则名称
	 */
	private String name;
	/**
	 * 字段集合
	 */
	private String params;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime modifiedTime;
	/**
	 * 创建用户
	 */
	private Integer createUser;
	/**
	 * 更新用户
	 */
	private Integer modifiedUser;
	/**
	 * 是否删除（0，未删除；1，已删除）
	 */
	private Integer isDeleted;
	/**
	 * 状态（0，未清洗；1，已清洗）
	 */
	private Integer state;
	/**
	 * 数据来源
	 */
	private Integer deptId;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
