package com.cloud.gds.gms.api.vo;

import lombok.Data;

import java.util.Date;

/**
 * 政策分析前端显示页
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-12
 */
@Data
public class GovAnalyseVo {

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
	 * 发文时间
	 */
	private Date publishTime;
	/**
	 * 标记
	 */
	private String mark;
	/**
	 * 审核日期
	 */
	private Date examineDate;
	/**
	 * 发文单位（关联机构）
	 */
//	private List<CommonVO> dispatchList = new ArrayList<CommonVO>();
	/**
	 * 标签
	 */
//	private List<CommonVO> tagList = new ArrayList<CommonVO>();
}
