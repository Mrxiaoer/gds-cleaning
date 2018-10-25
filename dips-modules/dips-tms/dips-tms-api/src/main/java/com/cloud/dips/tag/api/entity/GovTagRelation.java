package com.cloud.dips.tag.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.google.common.base.Objects;

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
	private Integer gTagId;
	/**
	 * 关联ID
	 */
	@TableId(type = IdType.INPUT)
	private Integer gRelationId;
	
	
	@TableId(type = IdType.INPUT)
	private Integer gTypeId;
	
	@TableId(type = IdType.ID_WORKER_STR)
	private String gNode;


	@Override
	protected Serializable pkVal() {
		return this.gTagId;
	}


	@Override
	public String toString() {
		return "GovTagRelation [gTagId=" + gTagId + ", gRelationId=" + gRelationId + ", gTypeId=" + gTypeId + ", gNode="
				+ gNode + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GovTagRelation that = (GovTagRelation) obj;
		return Objects.equal(gTagId, that.gTagId) && Objects.equal(gRelationId, that.gRelationId) 
				&& Objects.equal(gTypeId, that.gTypeId) && Objects.equal(gNode, that.gNode);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(gTagId, gRelationId,gTypeId,gNode);
	}


}
