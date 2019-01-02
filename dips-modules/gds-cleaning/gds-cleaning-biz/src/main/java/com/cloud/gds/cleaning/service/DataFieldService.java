package com.cloud.gds.cleaning.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.vo.DataFieldVo;

import java.util.List;
import java.util.Map;
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
	 * 清洗池分页
	 * @param params
	 * @return
	 */
	Page<DataField> queryPage(Map<String,Object> params);

	/**
	 * 根据规则id查询
	 * @param ruleId
	 * @return
	 */
	List<DataField> selectByRuleId(Long ruleId);

	/**
	 * 根据多规则查询那些清洗池中使用过
	 * @param ruleIds
	 * @return
	 */
	List<DataField> selectByRuleIds(Set<Long> ruleIds);

	/**
	 * 根据清洗池id查询
	 * @param id
	 * @return
	 */
	DataFieldVo queryById(Long id);

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
	 * 检测规则是否可以更换
	 * @param id 清洗池id
	 * @param ruleId 选择的规则id
	 * @return
	 */
	Boolean checkRule(Long id, Long ruleId);

	/**
	 * 根据规则id更新是否需要重新分析
	 * @param ruleId
	 * @return
	 */
	Boolean updateNeedReanalysis(Long ruleId);
}
