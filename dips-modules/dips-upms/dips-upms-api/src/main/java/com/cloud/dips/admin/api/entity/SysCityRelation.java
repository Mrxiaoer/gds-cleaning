package com.cloud.dips.admin.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 *
 * @author DingBin
 * @date 2018-11-19 10:07:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_city_relation")
public class SysCityRelation extends Model<SysCityRelation> {

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
