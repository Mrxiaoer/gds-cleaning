package com.cloud.dips.admin.api.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Data
@TableName("gov_user")
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Transient
    public void applyDefaultValue() {
        if (getCreateTime()==null) {
            setCreateTime(LocalDateTime.now());
        }
        if (getModifiedTime()==null) {
            setModifiedTime(LocalDateTime.now());
        }
        if (getSalt()==null) {
            setSalt("");
        }
        if (getPhone()==null) {
            setPhone("");
        }
        if (getAvatar()==null) {
            setAvatar("");
        }
        if (getWeixinOpenid()==null) {
            setWeixinOpenid("");
        }
        if (getQqOpenid()==null) {
            setQqOpenid("");
        }
    }

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 用户真实姓名
	 */
	private String realName;

	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 随机盐
	 */
	@JsonIgnore
	private String salt;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime modifiedTime;
	/**
	 * 0-正常，1-删除
	 */
	@TableLogic
	private String isDeleted;

	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 部门ID
	 */
	private Integer deptId;

	/**
	 * 微信openid
	 */
	private String weixinOpenid;

	/**
	 * QQ openid
	 */
	private String qqOpenid;

}
