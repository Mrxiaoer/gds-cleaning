package com.cloud.gds.gmsanalyse.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 政策分析
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-03
 */
@Data
public class GovPolicyDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 分析名称
	 */
	private String analyseName;
	/**
	 * 分析政策的id集
	 */
	private List<Long> originalList;
	/**
	 * 特征数
	 */
	private Integer featureNum;


}
