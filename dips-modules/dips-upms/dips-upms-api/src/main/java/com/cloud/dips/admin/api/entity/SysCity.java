package com.cloud.dips.admin.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Transient;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 *
 * @author DingBin
 * @date 2018-11-19 10:07:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_city")
public class SysCity extends Model<SysCity> {
	private static final long serialVersionUID = 1L;

	/**
	 * 城市ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 城市名称
	 */
	private String name;
	/**
	 * 城市编号
	 */
	private String number;
	/**
	 * 城市代码
	 */
	private String code;
	/**
	 * 城市等级
	 */
	private String cityLevel;
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
	 * 是否删除 -1：已删除 0：正常
	 */
	private String isDeleted;
	/**
	 * 父级ID
	 */
	private Integer parentId;

	@Transient
	public void applyDefaultValue() {
		if (StringUtils.isBlank(getNumber())) {
			setNumber("");
		}
		if (StringUtils.isBlank(getCode())) {
			setCode("");
		}
		if (getOrderNum() == null) {
			setOrderNum(0);
		}
		if (getParentId() == null) {
			setParentId(0);
		}
		if (getCreateTime() == null) {
			setCreateTime(LocalDateTime.now());
		}
		if (getModifiedTime() == null) {
			setModifiedTime(LocalDateTime.now());
		}
		if (StringUtils.isBlank(getIsDeleted())) {
			setIsDeleted("0");
		}
	}

	/**
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
