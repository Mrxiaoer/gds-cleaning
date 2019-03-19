package com.cloud.gds.preprocessing.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 采集通用政策模型
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-19
 */
@Data
public class ScrapyGovPolicyGeneral extends Model<ScrapyGovPolicyGeneral> {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 索引号
	 */
	private String reference;
	/**
	 * 发文号
	 */
	private String issue;
	/**
	 * 文体（1|通知 2|公告 3|报告 4|意见 5|办法 6|通报 7|其他）
	 */
	private String style;
	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级))
	 */
	private String level;
	/**
	 * 发文时间
	 */
	private LocalDateTime publishTime;
	/**
	 * 正文
	 */
	private String text;
	/**
	 * 附件（附件地址）
	 */
	private String attachment;
	/**
	 * 原文链接
	 */
	private String url;
	/**
	 * 地域
	 */
	private String region;
	/**
	 * 加工者
	 */
	private Long creatId;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
