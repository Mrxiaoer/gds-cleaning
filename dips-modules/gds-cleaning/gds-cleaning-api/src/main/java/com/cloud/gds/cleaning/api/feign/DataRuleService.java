package com.cloud.gds.cleaning.api.feign;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.feign.factory.CleanPoolServiceFallbackFactory;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.api.vo.LabelVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@FeignClient(value = DataCleanConstant.MODULE_NAME, fallbackFactory = CleanPoolServiceFallbackFactory.class)
public interface DataRuleService {

	/**
	 * 规则名称分页
	 * 参数要求：page、limit、name
	 */
	@GetMapping("/data_rule/page")
	public R page(@RequestParam Map<String, Object> params);

	/**
	 * 根据id查询
	 *
	 * @return R
	 */
	@GetMapping("/data_rule/{id}")
	public DataRuleVo info(@PathVariable("id") Long id);

	/**
	 * 查询部门规则
	 *
	 * @return
	 */
	@GetMapping("/data_rule/list")
	public R selectAll();

	/**
	 * 获取规则动态参数
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/data_rule/key/{id}")
	public List<LabelVo> getKey(@PathVariable("id") Long id);

	/**
	 * 保存 规则名称信息
	 *
	 * @return R
	 */
	@PostMapping("/data_rule/create")
	public R save(@RequestBody DataRuleVo dataRuleVo);

	/**
	 * 修改 规则信息
	 *
	 * @return R
	 */
	@PostMapping("/data_rule/update")
	public R update(@RequestBody DataRuleVo dataRuleVo);

	/**
	 * 单独删除一条
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/data_rule/delete/{id}")
	public R delete(@PathVariable("id") Long id);

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/data_rule/delete")
	public R deleteT(@RequestBody Set<Long> ids);

}
