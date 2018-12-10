package com.cloud.dips.tag.api.dto;

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
}