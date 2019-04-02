package com.cloud.gds.gmsanalyse.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 公用类vo
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-25
 */
@Data
public class CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private Integer commonId;
	/**
	 * 名称
	 */
	private String commonName;
}
