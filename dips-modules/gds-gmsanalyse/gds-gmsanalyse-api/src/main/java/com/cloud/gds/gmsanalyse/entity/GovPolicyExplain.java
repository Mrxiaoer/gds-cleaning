package com.cloud.gds.gmsanalyse.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 政策解读
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-29
 */
@Data
public class GovPolicyExplain extends Model<GovPolicyExplain> {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 政策解读标题
	 */
	@TableField("title")
	private String title;
	/**
	 * 解读时间
	 */
	@TableField(value = "publish_time", strategy = FieldStrategy.IGNORED)
	private Date publishTime;
	/**
	 * 来源
	 */
	@TableField("source")
	private String source;
	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级))
	 */
	@TableField("level")
	private String level;
	/**
	 * 解读主体
	 */
	@TableField("main")
	private String main;
	/**
	 * 摘要
	 */
	@TableField("summary")
	private String summary;
	/**
	 * 正文
	 */
	@TableField("text")
	private String text;
	/**
	 * 标题图
	 */
	@TableField("image")
	private String image;
	/**
	 * 原文链接
	 */
	@TableField("url")
	private String url;
	/**
	 * 删除标识
	 */
	@TableField("is_deleted")
	private String delFlag;
	/**
	 * 优先级
	 */
	@TableField("priority")
	private String priority;
	/**
	 * 浏览次数
	 */
	@TableField("views")
	private Integer views;
	/**
	 * 创建人ID
	 */
	@TableField("creator_id")
	private Integer creatorId;
	/**
	 * 适用行业
	 */
	@TableField("industry")
	private String industry;
	/**
	 * 主题
	 */
	@TableField("theme")
	private String theme;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@TableField(value = "modified_time", update = "now()")
	private Date modifiedTime;
	/**
	 * 标记
	 */
	@TableField("mark")
	private String mark;

	/**
	 * 地域
	 */
	@TableField("region")
	private String region;

	/**
	 * 地域编码
	 */
	@TableField("region_arr")
	private String regionArray;
	/**
	 * 附件路径
	 */
	@TableField("file")
	private String file;
	/**
	 * 采集库关联ID
	 */
	private Integer scrapyId;
	/**
	 * 审核状态
	 */
	private Integer examineStatus;
	/**
	 * 审核人
	 */
	@TableField("examine_user_id")
	private Integer examineUser;
	/**
	 * 审核日期
	 */
	private Date examineDate;
	/**
	 * 退回次数
	 */
	private Integer retreatCount;
	/**
	 * 退回人
	 */
	private Integer retreatUser;
	/**
	 * 退回原因
	 */
	private String retreatContent;
	/**
	 * 加工者
	 */
	private Integer processorId;
	/**
	 * 提交时间
	 */
	@TableField("commit_time")
	private Date commitTime;

	/**
	 * 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
