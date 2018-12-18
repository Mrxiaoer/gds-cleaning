package com.cloud.gds.cleaning.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.gds.cleaning.api.entity.DataField;

import java.util.List;
import java.util.Set;

/**
 * 清洗池接口层
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-07
 */
public interface DataFieldService extends IService<DataField> {

	/**
	 * 根据规则id查询
	 * @param ruleId
	 * @return
	 */
	List<DataField> selectByRuleId(Long ruleId);

	/**
	 * 根据多规则查询是否被使用
	 * @param ruleIds
	 * @return
	 */
	List<DataField> selectByRuleIds(Set<Long> ruleIds);

	/**
	 * 保存
	 * @param dataField
	 * @return
	 */
	Boolean save(DataField dataField);

	/**
	 * 更新数据
	 * @param dataField
	 * @return
	 */
	Boolean update(DataField dataField);

	/**
	 * 单独删除数据
	 * @param id
	 * @return
	 */
	Boolean deleteById(Long id);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	Boolean deleteByIds(Set<Long> ids);

	/**
	 * 即将更新的规则ruleId、id
	 * @param id
	 * @param ruleId
	 * @return
	 */
	Boolean checkRule(Long id, Long ruleId);
}
