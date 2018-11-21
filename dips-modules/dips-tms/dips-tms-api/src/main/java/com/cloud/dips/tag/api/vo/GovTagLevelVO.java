package com.cloud.dips.tag.api.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @author ZB
 *
 */
@Data
public class GovTagLevelVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer levelId;

	private String name;

	private Date creationDate;
	
}
