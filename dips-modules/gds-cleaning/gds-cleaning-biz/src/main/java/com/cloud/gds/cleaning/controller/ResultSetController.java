package com.cloud.gds.cleaning.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.vo.BaseVo;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	private final DataFieldValueService dataFieldValueService;

	@Autowired
	public ResultSetController(
		DataFieldValueService dataFieldValueService) {
		this.dataFieldValueService = dataFieldValueService;
	}

	/**
	 * 结果集详情分页
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "结果集详情分页")
	public R page(@RequestParam Map<String, Object> params) {
		return new R<>(dataFieldValueService.queryPage(params));
	}

	/**
	 * 对比详情前数据
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/contrast_before")
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
	@GetMapping("/contrast_after")
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
	@GetMapping("/cleaning_item")
	@ApiOperation(value = "清洗项", notes = "清洗项详情")
	public R cleaningItem(@RequestParam Long id) {

		return new R();
	}

	/**
	 * 清空数据
	 * 由于结果集中有对比清洗前数据,如果需导入不同状态数据需要->清空数据池
	 *
	 * @param fieldId 清洗池id
	 * @return
	 */
	@GetMapping("/clear")
	@ApiOperation(value = "清空数据", notes = "清空数据")
	public R clear(@RequestParam Long fieldId) {
		return new R<>(dataFieldValueService.clear(fieldId));
	}

	/**
	 * 清缓冲
	 * 由于结果集中有对比清洗前数据,如果清洗后数据与新一套数据再次进行清洗因此需要对已删除的数据进行缓冲清除
	 *
	 * @param fieldId
	 * @return
	 */
	@GetMapping("/clear_buffer")
	@ApiOperation(value = "清缓冲", notes = "清缓冲")
	public R clearBuffer(@RequestParam Long fieldId) {
		return new R<>(dataFieldValueService.clearBuffer(fieldId));
	}

}
