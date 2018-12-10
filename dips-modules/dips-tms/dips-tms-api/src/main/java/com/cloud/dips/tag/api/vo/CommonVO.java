package com.cloud.dips.tag.api.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author ZB
 *
 */
@Data
public class CommonVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Object commonId;
	private Object commonName;

}