package com.cloud.dips.tag.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * <p>
 * 标签合并记录表
 * </p>
 *
 * @author ZB
 */
@Data
@TableName("gov_tag_merge_record")
public class GovTagMergeRecord extends Model<GovTagMergeRecord> {

	private static final long serialVersionUID = 1L;

	/**
	 * 标签ID
	 */
	@TableId(type = IdType.INPUT)
	private Integer tagId;
	/**
	 * 合并ID
	 */
	@TableId(type = IdType.INPUT)
	private Integer mergeId;


	@Override
	protected Serializable pkVal() {
		return this.tagId;
	}
}
