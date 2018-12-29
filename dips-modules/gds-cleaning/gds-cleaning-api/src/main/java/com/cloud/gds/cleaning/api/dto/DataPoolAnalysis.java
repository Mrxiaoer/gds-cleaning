package com.cloud.gds.cleaning.api.dto;

import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import lombok.Data;

/**
 * 清洗完的数据回显
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-29
 */
@Data
public class DataPoolAnalysis extends DataFieldValue {

	/**
	 * 相似度
	 */
	private Double similarity;

}
