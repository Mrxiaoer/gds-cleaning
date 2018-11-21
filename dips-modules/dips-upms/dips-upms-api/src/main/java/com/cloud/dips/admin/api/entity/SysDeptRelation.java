package com.cloud.dips.admin.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 部门关系表
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_dept_relation")
public class SysDeptRelation extends Model<SysDeptRelation> {

	private static final long serialVersionUID = 1L;

	/**
	 * 祖先节点
	 */
	private Integer ancestor;
	/**
	 * 后代节点
	 */
	private Integer descendant;


	@Override
	protected Serializable pkVal() {
		return this.ancestor;
	}

}
