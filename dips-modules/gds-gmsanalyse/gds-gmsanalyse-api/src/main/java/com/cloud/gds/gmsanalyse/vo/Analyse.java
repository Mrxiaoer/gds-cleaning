package com.cloud.gds.gmsanalyse.vo;

import lombok.Data;

import java.util.List;

/**
 * 分析实体类
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-09
 */
@Data
public class Analyse {
	/**
	 * 概要
	 */
	private String summary;
	/**
	 * 子类
	 */
	private List<Child> list;
}
