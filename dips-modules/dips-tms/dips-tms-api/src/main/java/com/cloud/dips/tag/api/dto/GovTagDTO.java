package com.cloud.dips.tag.api.dto;

import java.util.List;

import com.cloud.dips.tag.api.entity.GovTag;

import lombok.Data;

/**
 * @author ZB
 */

@Data
public class GovTagDTO extends GovTag {
	
	/**
	 * 关联标签
	 */
	private String[] tagList;
	/**
	 * 分类id集合
	 */
	private List<Integer> typeIds;
}