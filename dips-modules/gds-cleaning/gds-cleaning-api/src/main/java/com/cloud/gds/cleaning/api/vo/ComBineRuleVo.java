package com.cloud.gds.cleaning.api.vo;

import lombok.Data;

/**
 * 返回规则详情归属所在清洗池
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-11
 */
@Data
public class ComBineRuleVo extends DataSetVo {

	/**
	 * 清洗池名称
	 */
	private String cleanPoolName;

}
