package com.cloud.gds.cleaning.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.vo.BaseVo;
import com.cloud.gds.cleaning.api.vo.CleanItem;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 结果集
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-19
 */
@RestController
@RequestMapping("/result_set")
public class ResultSetController {

	@Autowired
	private DataFieldValueService dataFieldValueService;

	/**
	 * 结果集详情分页
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "结果集详情分页", notes = "结果集详情分页")
	public R page(@RequestParam Map<String, Object> params) {
		return new R<>(dataFieldValueService.queryPage(params));
	}

	/**
	 * 对比详情前数据
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/before_comparison")
	@ApiOperation(value = "对比前详情分页", notes = "对比详情前数据")
	public R contrastBefore(@RequestParam Map<String, Object> params) {
		Page<BaseVo> page = dataFieldValueService.contrastBeforePage(params);
		return new R<>(page);
	}

	/**
	 * 对比详情后数据
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/after_comparison")
	@ApiOperation(value = "对比后详情分页", notes = "对比详情后数据")
	public R contrastAfter(@RequestParam Map<String, Object> params) {
		Page<BaseVo> page = dataFieldValueService.contrastAfterPage(params);
		return new R<>(page);
	}

	/**
	 * 清洗项
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/cleaning_item/{id}")
	@ApiOperation(value = "清洗项", notes = "清洗项详情")
	public List<CleanItem> cleaningItem(@PathVariable("id") Long id) {
		List<CleanItem> list = dataFieldValueService.cleaningItem(id);
		return list;
	}

	/**
	 * 清空数据
	 * 由于结果集中有对比清洗前数据,如果需导入不同状态数据需要->清空数据池
	 *
	 * @param fieldId 清洗池id
	 * @return
	 */
	@GetMapping("/clear/{fieldId}")
	@ApiOperation(value = "清空数据", notes = "清空数据")
	public R clear(@PathVariable("fieldId") Long fieldId) {
		return new R<>(dataFieldValueService.clear(fieldId));
	}

	/**
	 * 清缓存
	 * 由于结果集中有对比清洗前数据,如果清洗后数据与新一套数据再次进行清洗因此需要对已删除的数据进行缓存清除
	 *
	 * @param fieldId
	 * @return
	 */
	@GetMapping("/clear_buffer/{fieldId}")
	@ApiOperation(value = "清缓存", notes = "清缓存")
	public R clearBuffer(@PathVariable("fieldId") Long fieldId) {
		return new R<>(dataFieldValueService.clearBuffer(fieldId));
	}

}
