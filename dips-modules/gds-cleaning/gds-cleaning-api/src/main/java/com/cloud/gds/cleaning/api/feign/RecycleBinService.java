package com.cloud.gds.cleaning.api.feign;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.feign.factory.RecycleBinServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@FeignClient(value = DataCleanConstant.MODULE_NAME, fallbackFactory = RecycleBinServiceFallbackFactory.class)
public interface RecycleBinService {

	/**
	 * 回收站清洗池分页
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/recycle/clean_pool/page")
	R cleanPoolPage(@RequestParam Map<String, Object> params);

	/**
	 * 还原清洗池中的数据
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/recycle/clean_pool/reduction/{id}")
	R cleanPoolReduction(@PathVariable("id") Long id);

	/**
	 * 删除清洗池中的数据
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/recycle/clean_pool/delete/{id}")
	R cleanPoolDelete(@PathVariable("id") Long id);

	/**
	 * 回收站数据池
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/recycle/data_pool/page")
	R dataPoolPage(@RequestParam Map<String, Object> params);

	/**
	 * 还原数据池中相应数据
	 *
	 * @return
	 */
	@PostMapping("/recycle/data_pool/reduction/{id}")
	R dataPoolReduction(@PathVariable("id") Long id);

	/**
	 * 批量还原数据池中相应数据
	 *
	 * @return
	 */
	@PostMapping("/recycle/data_pool/reduction")
	R dataPoolReductions(@RequestBody Set<Long> ids);

	/**
	 * 一键还原数据池信息
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/recycle/data_pool/one_key_reduction/{id}")
	R oneKeyReduction(@PathVariable("id") Long id);

	/**
	 * 删除数据池中数据
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/recycle/data_pool/delete/{id}")
	R dataPoolDelete(@PathVariable("id") Long id);

	/**
	 * 删除数据池中数据
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/recycle/data_pool/delete")
	R dataPoolDeletes(@RequestBody Set<Long> ids);

	/**
	 * 已删除规则池分页
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/recycle/rule_pool/page")
	R rulePoolPage(@RequestParam Map<String, Object> params);

	/**
	 * 还原规则池中数据
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/recycle/rule_pool/reduction/{id}")
	R rulePoolReduction(@PathVariable("id") Long id);

	/**
	 * 删除规则池中数据
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/recycle/rule_pool/delete/{id}")
	R rulePoolDelete(@PathVariable("id") Long id);


	/**
	 * 批量删除规则池中数据
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/recycle/rule_poo/delete")
	R rulePoolDeletes(@PathVariable("ids") Set<Long> ids);
}
