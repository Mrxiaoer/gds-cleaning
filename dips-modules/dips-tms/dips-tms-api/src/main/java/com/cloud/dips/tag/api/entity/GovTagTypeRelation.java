package com.cloud.dips.tag.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * <p>
 * 标签与标签分类关联表
 * </p>
 *
 * @author ZB
 */
@Data
@TableName("gov_tag_type_relation")
public class GovTagTypeRelation extends Model<GovTagTypeRelation> {

	private static final long serialVersionUID = 1L;

	/**
	 * 标签ID
	 */
	@TableId(type = IdType.INPUT)
	private Integer tagId;
	/**
	 * 分类ID
	 */
	@TableId(type = IdType.INPUT)
	private Integer typeId;

	@Override
	protected Serializable pkVal() {
		return this.tagId;
	}

	public GovTagTypeRelation(Integer tagId,Integer typeId){
		this.tagId=tagId;
		this.typeId=typeId;
	}
	
}
