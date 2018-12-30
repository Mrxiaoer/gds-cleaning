package com.cloud.gds.cleaning.api.vo;

import lombok.Data;

/**
 * "卫星"集片段
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-28
 */
@Data
public class GroupVo {
	/**
	 * 对比数据id
	 */
	private Long id;
	/**
	 * 相似度
	 */
	private Double similarity;

}
