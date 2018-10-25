package com.cloud.dips.tag.api.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

@Data
@TableName("gov_tag_relation_type")
public class GovTagRelationType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

	@TableId(value = "g_type_id", type = IdType.AUTO)
	private Integer typeId;


	@TableField("g_type_name")
	private String name;

	@TableField("g_type_number")
	private String number;

	@Override
	public String toString() {
		return "GovTagRelationType [typeId=" + typeId + ", name=" + name + ", number=" + number + "]";
	}
	


}
