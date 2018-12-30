package com.cloud.gds.cleaning.api.vo;

import lombok.Data;

import java.util.List;

/**
 * 清洗结果json
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-28
 */
@Data
public class ResultJsonVo {

	/**
	 * 中心数据id
	 */
	private Long id;
	/**
	 * 卫星数据集
	 */
	private List<GroupVo> group;

}
