package com.cloud.gds.cleaning.api.dto;

import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 清洗完的数据回显
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DataPoolAnalysis extends DataFieldValue {

	private static final long serialVersionUID = -1240012626749538148L;
	/**
	 * 相似度
	 */
	private Double similarity;

}
