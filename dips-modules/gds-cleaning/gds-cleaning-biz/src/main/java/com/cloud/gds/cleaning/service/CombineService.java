package com.cloud.gds.cleaning.service;

import com.cloud.gds.cleaning.api.entity.DataField;

import java.util.List;

/**
 * 合并清洗池
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-07
 */
public interface CombineService {


	/**
	 * 查询与此清洗池相同规则的数据
	 *
	 * @param fieldId
	 * @return
	 */
	List<DataField> getIdenticalCleanPool(Long fieldId);

	/**
	 * 保存新池
	 *
	 * @param dataField
	 * @return
	 */
	boolean nominateCleanPool(DataField dataField);

}
