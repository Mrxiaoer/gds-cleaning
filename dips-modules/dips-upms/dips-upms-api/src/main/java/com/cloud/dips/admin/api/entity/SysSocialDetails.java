/*
 *
 * Copyright (c) 2018-2025, BigPan All rights reserved.
 *
 * Author: Wilson
 *
 */

package com.cloud.dips.admin.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统社交登录账号表
 *
 * @author BigPan
 * @date 2018-08-16 21:30:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_social_details")
public class SysSocialDetails extends Model<SysSocialDetails> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主鍵
	 */
	@TableId(value = "g_id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 类型
	 */
	@TableField("g_type")
	private String type;
	/**
	 * 描述
	 */
	@TableField("g_remark")
	private String remark;
	/**
	 * appid
	 */
	@TableField("g_app_id")
	private String appId;
	/**
	 * app_secret
	 */
	@TableField("g_app_secret")
	private String appSecret;
	/**
	 * 回调地址
	 */
	@TableField("g_redirect_url")
	private String redirectUrl;
	/**
	 * 创建时间
	 */
	@TableField("g_create_time")
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	@TableField("g_update_time")
	private LocalDateTime updateTime;
	/**
	 * 删除标记
	 */
	@TableLogic
	@TableField("g_del_flag")
	private String delFlag;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
