package com.cloud.gds.gms.api.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 通用政策模型Vo
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-01
 */
@Data
public class GeneralVO {

	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 摘要
	 */
	private String summary;
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
	private String styleName;
	/**
	 * 发文单位（关联机构）
	 */
	private List<CommonVO> dispatchList = new ArrayList<CommonVO>();
	/**
	 * 联合发文单位（关联机构）
	 */
	private List<CommonVO> unionList = new ArrayList<CommonVO>();
	/**
	 * 层级(1|国家级 2|省级 3|市级 4|区级（县级))
	 */
	private String level;
	private String levelName;

	/**
	 * 时效性( 0 有效  1 失效）依据失效日期判断是否过期
	 */
	private String timeliness;
	private String timelinessName;
	/**
	 * 政策阶段状态（1|征求意见中  2|已印发）
	 */
	private String stage;
	private String stageName;
	/**
	 * 发文形式（1|直接印发  2|转发）
	 */
	private String formality;
	private String formalityName;
	/**
	 * 有效年限（X年）
	 */
//	private String effective;
	/**
	 * 成文时间
	 */
//	private Date writeTime;
	/**
	 * 发文时间
	 */
	private Date publishTime;
	/**
	 * 生效时间
	 */
	private Date effectTime;
	/**
	 * 失效时间
	 */
	private Date invalidTime;

	/**
	 * 点击次数
	 */
	private Integer views;
	/**
	 * 政策依据（关联政策）
	 */
	private List<CommonVO> policyList = new ArrayList<CommonVO>();
	/**
	 * 适用区域（关联地域）
	 */
	private List<CommonVO> regionList = new ArrayList<CommonVO>();
	/**
	 * 适用对象（1|企业  2|个人  3|政府  4|科研机构  5|行业协会  6|园区  7|其他）
	 */
	private String target;
	/**
	 * 主题（1|企业创办  2|人才引进和能力提升  3|投资融资  4|税收优惠  5|市场拓展  品牌建设  7|创新研发  8|科研课题  9|成果转化  10|知识产权  11|信息化建设  12|资质认定  13|产业发展  14|节能环保  15|转型升级  16|改制上市  17|并购重组  18|平台基地建设  19|孵化器及基地建设  20|招商引资  21|其他）
	 */
	private String theme;
	/**
	 * 适用规模（1|大型企业  2|规上企业  3|中小企业  4|小微企业）
	 */
	private String scale;
	/**
	 * 适用行业（1|农业）
	 */
	private String industry;
	/**
	 * 正文
	 */
	private String text;
	/**
	 * 标签
	 */
	private List<CommonVO> tagList = new ArrayList<CommonVO>();
	/**
	 * 附件（附件地址）
	 */
	private String attachment;

	/**
	 * 原文链接
	 */
	private String url;

	/**
	 * 创建人id
	 */
	private Integer creatorId;
	/**
	 * 创建人姓名
	 */
	private String creatorName;
	/**
	 * 上一条
	 */
	private CommonVO prev = new CommonVO();
	/**
	 * 下一条
	 */
	private CommonVO next = new CommonVO();
	/**
	 * 对象字典
	 */
	private List<String> targetList = new ArrayList<String>();
	/**
	 * 主题字典
	 */
	private List<String> themeList = new ArrayList<String>();
	/**
	 * 规模字典
	 */
	private List<String> scaleList = new ArrayList<String>();
	/**
	 * 行业字典
	 */
	private List<String> industryList = new ArrayList<String>();
	/**
	 * 标记
	 */
	private String mark;

	/**
	 * 地域
	 */
	private String region;
	/**
	 * 地域编码
	 */
	private String regionArrString;

	/**
	 * 地域编码
	 */
	private List<String> regionArr = new ArrayList<String>();
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
	 * 生成时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifiedTime;

	/**
	 * 文件下载名称
	 */
	private String newFile;
	/**
	 * 文件下载路径
	 */
	private String newUrl;
	/**
	 * 提交时间
	 */
	private Date commitTime;

	/**
	 * 相关标签
	 */
	private String relatedTags;

	/**
	 * 审核人姓名
	 */
	private String examineUserName;

	/**
	 * 标签相识度
	 */
	private float similarityDegree;

}
