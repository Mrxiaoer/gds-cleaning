package com.cloud.gds.cleaning.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 属性
 *
 * @author lolilijve
 * @date 2018-11-27 09:27:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dataclean_attribute_match")
public class AttributeMatch extends Model<AttributeMatch> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 数据id（关联data_wait_clean表）
	 */
	private Long dataId;
	/**
	 * 属性值
	 */
	private String attributeValue;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime modifiedTime;
	/**
	 * 修改用户
	 */
	private Long createUser;
	/**
	 * 更新用户
	 */
	private Long modifiedUser;
	/**
	 * 是否删除（0，未删除；1，已删除）
	 */
	private Integer isDeleted;

	/**
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
