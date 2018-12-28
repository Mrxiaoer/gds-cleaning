package com.cloud.dips.tag.api.vo;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

/**
 * 
 * @author ZB
 *
 */
@Data
public class GovTagVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private Integer tagId;
	/**
	 * 标签名称
	 */
	private String name;
	/**
	 * 标签创建时间
	 */
	private Date createTime;
	/**
	 * 标签更新时间
	 */
	private Date updateTime;
	/**
	 * 标签应用次数
	 */
	private Integer refers;
	/**
	 * 标签优先级
	 */
	private Integer orderNum;
	/**
	 * 标签级别id
	 */
	private Integer levelId;
	/**
	 * 标签级别名称
	 */
	private String levelName;
	/**
	 * 标签浏览量
	 */
	private Integer views;
	/**
	 * 标签介绍
	 */
	private String description;
	/**
	 * 创建者ID
	 */
	private Integer creatorId;
	/**
	 * 创建者真实姓名
	 */
	private String creatorRealName;
	/**
	 * 所属系统
	 */
	private String system;
	
	/**
	 * 标签状态
	 */
	private Integer status;
	/**
	 * 标签启用
	 */
	private Integer enable;
	/**
	 * 关联标签
	 */
	private List<CommonVO> tagList;
	
	/**
	 * 分类id数组
	 */
	private List<CommonVO> typeObjs;
	/**
	 * 分类名称
	 */
	private List<String> typeNames;

	private List<GovTagTypeVO> typeVos;
	
	
    @Transient
    public void addTypeObjs() {
    	this.typeObjs=new LinkedList<CommonVO>();
    	for(GovTagTypeVO typeVo:typeVos){
        	CommonVO bean=new CommonVO();
        	bean.setCommonId(typeVo.getTypeId());
    		StringBuilder typeName=new StringBuilder(typeVo.getName());
    		typeVo=typeVo.getParentVo();
        	while(typeVo!=null){
        		typeName.insert(0, typeVo.getName()+"-");
        		typeVo=typeVo.getParentVo();
        	}
        	bean.setCommonName(typeName.toString());
        	typeObjs.add(bean);
    	}
    }
	
	
    @Transient
    public void addTypeNames() {
    	this.typeNames=new LinkedList<String>();
    	for(GovTagTypeVO typeVo:typeVos){
    		StringBuilder typeName=new StringBuilder(typeVo.getName());
    		typeVo=typeVo.getParentVo();
        	while(typeVo!=null){
        		typeName.insert(0, typeVo.getName()+"-");
        		typeVo=typeVo.getParentVo();
        	}
        	typeNames.add(typeName.toString());
    	}
    }
	
}