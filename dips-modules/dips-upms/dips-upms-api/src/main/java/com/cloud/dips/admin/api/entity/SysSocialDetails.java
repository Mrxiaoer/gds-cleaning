package com.cloud.dips.admin.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统社交登录账号表
 *
 * @author rcg
 * @date 2018-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_social_details")
public class SysSocialDetails extends Model<SysSocialDetails> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主鍵
	 */
	@TableId
	private Integer id;
	/**
	 * 类型
	 */
	@NotBlank(message = "类型不能为空")
	private String type;
	/**
	 * 描述
	 */
	private String remark;
	/**
	 * appid
	 */
	@NotBlank(message = "账号不能为空")
	private String appId;
	/**
	 * app_secret
	 */
	@NotBlank(message = "密钥不能为空")
	private String appSecret;
	/**
	 * 回调地址
	 */
	private String redirectUrl;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime modifiedTime;
	/**
	 * 删除标记
	 */
	@TableLogic
	private String isDeleted;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
