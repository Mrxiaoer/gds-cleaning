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
 * 附件表
 * </p>
 *
 * @author RCG
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
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 存附件用户ID
	 */
	@TableField("user_id")
	private Integer userId;
	/**
	 * 附件服务器路径
	 */
	@TableField("url")
	private String url;
	/**
	 * 文件长度
	 */
	@TableField("file_size")
	private Long length;
	/**
	 * ip
	 */
	@TableField("ip")
	private String ip;
	/**
	 * 附件上传时间
	 */
	@TableField("create_time")
	private Date time;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
