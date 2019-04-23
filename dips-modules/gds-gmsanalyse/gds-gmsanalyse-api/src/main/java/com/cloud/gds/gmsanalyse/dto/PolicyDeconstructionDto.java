package com.cloud.gds.gmsanalyse.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 政策解构DTO
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-19
 */
@Data
public class PolicyDeconstructionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 政策id
	 */
	private Long policyId;

	/**
	 * 政策标题
	 */
	private String policyTitle;
	/**
	 * 解构完的动词
	 */
	private List<String> verbsList;

}
