package com.cloud.dips.tag.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * <p>
 * 标签关联表
 * </p>
 *
 * @author ZB
 */
@Data
@TableName("gov_tag_relation")
public class GovTagRelation extends Model<GovTagRelation> {

	private static final long serialVersionUID = 1L;

	/**
	 * 标签ID
	 */
	@TableId(type = IdType.INPUT)
	private Integer tagId;
	/**
	 * 关联ID
	 */
	@TableId(type = IdType.INPUT)
	private Integer relationId;
	
	
	@TableId(type = IdType.INPUT)
	private Integer typeId;
	
	@TableId(type = IdType.ID_WORKER_STR)
	private String node;


	@Override
	protected Serializable pkVal() {
		return this.tagId;
	}
	@Override
	public String toString() {
		return "GovTagRelation [tagId=" + tagId + ", relationId=" + relationId + ", typeId=" + typeId + ", node=" + node
				+ "]";
	}

}
