package com.cloud.gds.cleaning.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据池entity
 *
 * @author lolilijve
 * @date 2018-12-03 15:58:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dataclean_data_field_value")
public class DataFieldValue extends Model<DataFieldValue> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 字段名id
	 */
	private Long fieldId;
	/**
	 * 导入数据的字段内容（json格式）
	 */
	private String fieldValue;
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
	 * 分析状态(0、未分析 1、已分析）
	 */
	private Integer state;
	/**
	 * 被某条数据清洗掉的id
	 */
	private Long beCleanedId;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
