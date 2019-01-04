package com.cloud.gds.cleaning.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.BaseVo;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.api.vo.DataSetVo;
import com.cloud.gds.cleaning.api.vo.LabelVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 待清洗数据
 *
 * @author lolilijve
 * @date 2018-11-22 10:59:36
 */
public interface DataRuleService extends IService<DataRule> {

	/**
	 * 规则分页查询
	 *
	 * @param params
	 * @return
	 */
	Page queryPage(Map<String, Object> params);

	/**
	 * 根据id查询规则信息
	 *
	 * @param id
	 * @return
	 */
	DataRuleVo queryById(Long id);

	/**
	 * 查询所有规则
	 *
	 * @return
	 */
	List<BaseVo> selectAll();

	/**
	 * 根据规则id获取动态参数
	 *
	 * @param id
	 * @return
	 */
	ArrayList<LabelVo> gainDynamicKey(Long id);

	/**
	 * 由于规则百分比更新，进行清洗池分析状态更新
	 *
	 * @param dataRuleVo
	 * @return
	 */
	Boolean customUpdate(DataRuleVo dataRuleVo);

	/**
	 * 单独删除数据
	 *
	 * @param id
	 * @return
	 */
	Boolean deleteById(Long id);

	/**
	 * 批量删除数据
	 *
	 * @param ids
	 */
	Boolean deleteByIds(Set<Long> ids);

	/**
	 * 保存规则基本信息
	 *
	 * @param dataRuleVo
	 * @return
	 */
	Boolean save(DataRuleVo dataRuleVo);

	/**
	 * 根据规则id获取规则中权重最高的字段信息
	 * 存在有同义的情况,取同义比高的部分,如无同义,取高比重
	 *
	 * @param ruleId
	 * @return
	 */
	DataSetVo gainUpperPower(Long ruleId);
}

