package com.cloud.dips.tag.api.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author ZB
 *
 */
@Data
public class MapVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 键
	 */
	private Object key;
	/**
	 * 值
	 */
	private Object value;

}