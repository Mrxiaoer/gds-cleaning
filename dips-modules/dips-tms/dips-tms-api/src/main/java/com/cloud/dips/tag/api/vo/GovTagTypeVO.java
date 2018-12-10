package com.cloud.dips.tag.api.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author ZB
 *
 */
@Data
public class GovTagTypeVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer typeId;

	private Integer parentId;
	
	private String parentName;

	private String name;

	private Date createTime;

	private List<GovTagTypeVO> children;
	
	private GovTagTypeVO parentVo;
	
}
