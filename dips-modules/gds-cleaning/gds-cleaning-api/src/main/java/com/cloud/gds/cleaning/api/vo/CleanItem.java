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
	private Long id;
	/**
	 * 数值
	 */
	private String label;
	/**
	 * 子节点是否存在(0、有 1、无)
	 */
	private Boolean isLeaf;
}
