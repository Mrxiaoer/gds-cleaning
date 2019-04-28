package com.cloud.gds.gmsanalyse.bo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 解构列表
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-22
 */
@Data
public class DeconstructionListBo {
	/**
	 * 解构动词
	 */
	private List<String> one;
	/**
	 * 动词对应的政策id
	 */
	private List<Long> two;
	/**
	 * 政策id与政策的title关系
	 */
	private Map<Long,String> map;
}
