package com.cloud.dips.admin.api.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author RCG
 * @since 2018-11-18
 */
@Data
@TableName("gov_log")
public class SysLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Transient
    public void applyDefaultValue() {
        if (getCreateTime()==null) {
            setCreateTime(LocalDateTime.now());
        }
        if (getModifiedTime()==null) {
            setModifiedTime(LocalDateTime.now());
        }
        if (getException()==null) {
            setException("");
        }
        
    }

	/**
	 * 编号
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 日志类型
	 */
	@NotBlank(message = "日志类型不能为空")
	private String type;
	/**
	 * 日志标题
	 */
	@NotBlank(message = "日志标题不能为空")
	private String title;
	/**
	 * 创建者
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime modifiedTime;
	/**
	 * 操作IP地址
	 */
	private String remoteAddr;
	/**
	 * 用户代理
	 */
	private String userAgent;
	/**
	 * 请求URI
	 */
	private String requestUri;
	/**
	 * 操作方式
	 */
	private String method;
	/**
	 * 操作提交的数据
	 */
	private String params;
	/**
	 * 执行时间
	 */
	private Long time;

	/**
	 * 删除标记
	 */
	@TableLogic
	private String isDeleted;

	/**
	 * 异常信息
	 */
	private String exception;

	/**
	 * 服务ID
	 */
	private String serviceId;


}
