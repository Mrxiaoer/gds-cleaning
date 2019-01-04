package com.cloud.gds.cleaning.api.vo;

import lombok.Data;

import java.util.List;

/**
 * 清洗项级联动
 *
 * @Author: yaonuan
 * @Date 2019/1/3
 */
@Data
public class CleanItem {
	/**
	 * 参数
	 */
	private String label;
	/**
	 * 数值
	 */
	private String value;
	/**
	 * 子级
	 */
	private List<CleanItem> children;
}
