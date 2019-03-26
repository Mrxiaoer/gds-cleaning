package com.cloud.gds.gmsnlp.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 通用政策模型
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gov_policy_general")
public class GovPolicyGeneral extends Model<GovPolicyGeneral> {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 标题
	 */
	@TableField("title")
	private String title;
	/**
	 * 摘要
	 */
	@TableField("summary")
	private String summary;
	/**
	 * 来源
	 */
	@TableField("source")
	private String source;
	/**
	 * 索引号
	 */
	@TableField("reference")
	private String reference;
	/**
	 * 发文号
	 */
	@TableField("issue")
	private String issue;
	/**
	 * 文体（1|通知 2|公告 3|报告 4|意见 5|办法 6|通报 7|其他）
	 */
	@TableField("style")
	private String style;

	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级))
	 */
	@TableField("level")
	private String level;
	/**
	 * 时效性( 0 有效  1 失效）依据失效日期判断是否过期
	 */
	@TableField("timeliness")
	private String timeliness;
	/**
	 * 政策阶段状态（1|征求意见中  2|已印发）
	 */
	@TableField("stage")
	private String stage;
	/**
	 * 发文形式（1|直接印发  2|转发）
	 */
	@TableField("formality")
	private String formality;
	/**
	 * 有效年限（X年）
	 */
//	@TableField("effective")
//	private String effective;
	/**
	 * 成文时间
	 */
//	@TableField(value = "write_time", strategy = FieldStrategy.IGNORED)
//	private Date writeTime;
	/**
	 * 发文时间
	 */
	@TableField(value = "publish_time", strategy = FieldStrategy.IGNORED)
	private Date publishTime;
	/**
	 * 生效时间
	 */
	@TableField(value = "effect_time", strategy = FieldStrategy.IGNORED)
	private Date effectTime;
	/**
	 * 失效时间
	 */
	@TableField(value = "invalid_time", strategy = FieldStrategy.IGNORED)
	private Date invalidTime;
	/**
	 * 正文
	 */
	@TableField("text")
	private String text;

	/**
	 * 点击次数
	 */
	@TableField("views")
	private Integer views;
	/**
	 * 附件（附件地址）
	 */
	@TableField("attachment")
	private String attachment;

	/**
	 * 原文链接
	 */
	@TableField("url")
	private String url;
	/**
	 * 删除位
	 */
	@TableField("is_deleted")
	private String delFlag;
	/**
	 * 创建人id
	 */
	@TableField("creator_id")
	private Integer creatorId;
	/**
	 * 适用对象
	 */
	@TableField("target")
	private String target;
	/**
	 * 主题
	 */
	@TableField("theme")
	private String theme;
	/**
	 * 适用规模
	 */
	@TableField("scale")
	private String scale;
	/**
	 * 适用对象
	 */
	@TableField("industry")
	private String industry;
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
	 * 采集库关联ID
	 */
	private Integer scrapyId;
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
