package com.cloud.gds.cleaning.service;

import com.cloud.gds.cleaning.api.dto.DistinctDto;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.vo.ComBineRuleVo;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;

import java.util.List;
import java.util.Set;

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
	 * 查询与此清洗池不同规则的数据
	 *
	 * @param fieldId
	 * @return
	 */
	List<DataField> getDistinctCleanPool(Long fieldId);

	/**
	 * 保存新池
	 *
	 * @param dataField
	 * @return
	 */
	boolean nominateCleanPool(DataField dataField);

	/**
	 * 相同规则同步数据
	 *
	 * @param distinctDto
	 * @return
	 */
	boolean regularizationData(DistinctDto distinctDto);

	/**
	 * 获取清洗池的规则项
	 *
	 * @param ids
	 * @return
	 */
	List<ComBineRuleVo> itemList(Set<Long> ids);

	/**
	 * 重新命名规则池信息
	 *
	 * @param dataRuleVo
	 * @return
	 */
	Long nominateRule(DataRuleVo dataRuleVo);

	/**
	 * 不同规则的数据同步
	 *
	 * @param distinctDto
	 * @return
	 */
	boolean distinctData(DistinctDto distinctDto);

}
