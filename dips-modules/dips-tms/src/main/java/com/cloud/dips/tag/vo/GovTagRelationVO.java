package com.cloud.dips.tag.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class GovTagRelationVO implements Serializable{
	
	private static final long serialVersionUID = 1L;


	private Integer tagId;
	
	private String tagName;

	private Integer relationId;

	private Integer typeId;
	
	private String typeNumber;
	
	private String typeName;
	

	private String node;

}
