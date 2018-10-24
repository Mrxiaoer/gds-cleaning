/*
 *
 * Copyright (c) 2018-2025, Wilson All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author Wilson
 * @since 2017-11-08
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("gov_attachment")
public class SysAttachment extends Model<SysAttachment> {

	private static final long serialVersionUID = 1L;

	@Transient
    public void applyDefaultValue() {
        if (getTime()==null) {
            setTime(new Timestamp(System.currentTimeMillis()));
        }
    }

	/**
	 * 主键ID
	 */
	@TableId(value = "g_file_id", type = IdType.AUTO)
	private Integer attachmentId;
	/**
	 * 存附件用户ID
	 */
	@TableField("g_user_id")
	private Integer userId;
	/**
	 * 附件服务器路径
	 */
	@TableField("g_url")
	private String url;
	/**
	 * 文件长度
	 */
	@TableField("g_length")
	private Long length;
	/**
	 * ip
	 */
	@TableField("g_ip")
	private String ip;
	/**
	 * 附件上传时间
	 */
	@TableField("g_time")
	private Date time;

	@Override
	public String toString() {
		return "Attachment [attachmentId=" + attachmentId + ", userId=" + userId + ", url=" + url + ", length=" + length
				+ ", ip=" + ip + ", time=" + time + "]";
	}

	@Override
	protected Serializable pkVal() {
		return this.attachmentId;
	}
}
