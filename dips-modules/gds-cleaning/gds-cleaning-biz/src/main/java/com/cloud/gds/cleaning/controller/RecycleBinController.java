package com.cloud.gds.cleaning.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.service.DataRuleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * 回收站
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-02-19
 */
@RestController
@RequestMapping("/recycle")
public class RecycleBinController {

	private final DataFieldValueService dataFieldValueService;

	private final DataFieldService dataFieldService;

	private final DataRuleService dataRuleService;

	@Autowired
	public RecycleBinController(DataFieldValueService dataFieldValueService, DataFieldService dataFieldService, DataRuleService dataRuleService) {
		this.dataFieldValueService = dataFieldValueService;
		this.dataFieldService = dataFieldService;
		this.dataRuleService = dataRuleService;
	}

	/**
	 * 回收站清洗池分页
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/clean_pool/page")
	@ApiOperation(value = "回收站清洗池分页", notes = "回收站清洗池分页")
	public R cleanPoolPage(@RequestParam Map<String, Object> params) {
		return new R<>(dataFieldService.queryRecycleBinPage(params));
	}

	/**
	 * 还原清洗池中的数据
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/clean_pool/reduction/{id}")
	@ApiOperation(value = "还原清洗池中的数据", notes = "还原清洗池中数据")
	public R cleanPoolReduction(@PathVariable("id") Long id) {
		String str;
		str = dataFieldService.cleanPoolReduction(id);
		if (str.equals("true") || str.equals("false")) {
			return new R<>(str);
		} else {
			R r = new R();
			r.setMsg(str);
			r.setData(false);
			return r;
		}
	}

	/**
	 * 删除清洗池中的数据
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/clean_pool/delete/{id}")
	@ApiOperation(value = "删除清洗池中的数据", notes = "删除清洗池中数据")
	public R cleanPoolDelete(@PathVariable("id") Long id) {
		return new R<>(dataFieldService.cleanPoolDelete(id));

	}

	/**
	 * 回收站数据池
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/data_pool/page")
	@ApiOperation(value = "回收站数据池分页", notes = "回收站数据池分页")
	public R dataPoolPage(@RequestParam Map<String, Object> params) {
		return new R<>(dataFieldValueService.queryRecycleBinPage(params));
	}

	/**
	 * 还原数据池中相应数据
	 *
	 * @return
	 */
	@PostMapping("/data_pool/reduction/{id}")
	@ApiOperation(value = "还原数据池中数据", notes = "还原数据池中数据")
	public R dataPoolReduction(@PathVariable("id") Long id) {
		return new R<>(dataFieldValueService.reductionById(id));
	}

	/**
	 * 批量还原数据池中相应数据
	 *
	 * @return
	 */
	@PostMapping("/data_pool/reduction")
	@ApiOperation(value = "批量还原数据池中数据", notes = "批量还原数据池中数据")
	public R dataPoolReductions(@RequestBody Set<Long> ids) {
		return new R<>(dataFieldValueService.reductionByIds(ids));
	}

	/**
	 * 一键还原数据池信息
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/data_pool/one_key_reduction/{id}")
	@ApiOperation(value = "一键还原数据池信息", notes = "一键还原数据池信息")
	public R oneKeyReduction(@PathVariable("id") Long id) {
		return new R<>(dataFieldValueService.oneKeyReduction(id));
	}

	/**
	 * 删除数据池中数据
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/data_pool/delete/{id}")
	@ApiOperation(value = "删除数据池中数据", notes = "删除数据池中数据")
	public R dataPoolDelete(@PathVariable("id") Long id) {
		return new R<>(dataFieldValueService.dataPoolDelete(id));
	}

	/**
	 * 删除数据池中数据
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/data_pool/delete")
	@ApiOperation(value = "删除数据池中数据", notes = "删除数据池中数据")
	public R dataPoolDeletes(@RequestBody Set<Long> ids) {
		return new R<>(dataFieldValueService.dataPoolDeletes(ids));
	}

	/**
	 * 已删除规则池分页
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/rule_pool/page")
	@ApiOperation(value = "已删除规则池分页", notes = "已删除规则池分页")
	public R rulePoolPage(@RequestParam Map<String, Object> params) {
		return new R<>(dataRuleService.queryRecycleBinPage(params));
	}

	/**
	 * 还原规则池中数据
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/rule_pool/reduction/{id}")
	@ApiOperation(value = "还原规则池中数据", notes = "还原规则池中数据")
	public R rulePoolReduction(@PathVariable("id") Long id) {
		return new R<>(dataRuleService.rulePoolReduction(id));
	}

	/**
	 * 删除规则池中数据
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/rule_pool/delete/{id}")
	@ApiOperation(value = "删除规则池中数据", notes = "删除规则池中数据")
	public R rulePoolDelete(@PathVariable("id") Long id) {
		return new R<>(dataRuleService.rulePoolDelete(id));
	}


	/**
	 * 删除规则池中数据
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/rule_poo/delete")
	@ApiOperation(value = "删除规则池中数据", notes = "删除规则池中数据")
	public R rulePoolDeletes(@PathVariable("ids") Set<Long> ids) {
		return new R<>(dataRuleService.rulePoolDeletes(ids));
	}

}
