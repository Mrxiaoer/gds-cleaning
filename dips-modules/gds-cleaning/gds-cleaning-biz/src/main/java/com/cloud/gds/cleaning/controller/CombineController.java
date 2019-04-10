package com.cloud.gds.cleaning.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.dto.DistinctDto;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.service.CombineService;
import com.cloud.gds.cleaning.service.DataFieldService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 合并清洗池controller
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-07
 */
@RestController
@RequestMapping("/combine")
public class CombineController {

	private final DataFieldService dataFieldService;

	private final CombineService combineService;

	@Autowired
	public CombineController(DataFieldService dataFieldService, CombineService combineService) {
		this.dataFieldService = dataFieldService;
		this.combineService = combineService;
	}


	/**
	 * 合并清洗池index分页
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation(value = "合并清洗池首页分页", notes = "合并清洗池首页分页")
	public R gainCombinePage(@RequestParam Map<String, Object> params) {
		return new R<>(dataFieldService.queryPage(params));
	}

	/**
	 * 根据清洗池查询相同规则的其他清洗池
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/identical/{id}")
	@ApiOperation(value = "根据清洗池查询相同规则的其他清洗池", notes = "根据清洗池查询相同规则的其他清洗池")
	public R getIdenticalCleanPool(@PathVariable("id") Long id) {
		return new R<>(combineService.getIdenticalCleanPool(id));
	}

	/**
	 * 根据清洗池查询不同规则的其他清洗池
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/distinct/{id}")
	@ApiOperation(value = "根据清洗池查询不同规则的其他清洗池", notes = "根据清洗池查询不同规则的其他清洗池")
	public R getDistinctCleanPool(@PathVariable("id") Long id) {
		return new R<>(combineService.getDistinctCleanPool(id));
	}

	/**
	 * 命名新数据池的名称
	 *
	 * @param dataField 包含name、ruleId
	 * @return
	 */
	@PostMapping("/nominate_clean_pool")
	@ApiOperation(value = "命名新数据池的名称", notes = "命名新数据池的名称")
	public R nominateCleanPool(@RequestBody DataField dataField) {
		if (combineService.nominateCleanPool(dataField)) {

			return new R<>(dataField.getId());
		} else {
			R r = new R();
			r.setData(false);
			r.setMsg("未命名成功~");
			return r;
		}
	}

	/**
	 * 相同规则同步数据
	 *
	 * @param distinctDto
	 * @return
	 */
	@PostMapping("/regularization_data")
	@ApiOperation(value = "相同规则同步数据", notes = "相同规则同步数据")
	public R regularizationData(@RequestBody DistinctDto distinctDto) {
		return new R<>(combineService.regularizationData(distinctDto));
	}

	/**
	 * 获取清洗池的规则项
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/item_list")
	@ApiOperation(value = "获取清洗池的规则项", notes = "获取清洗池的规则项")
	public R itemList(@RequestBody Set<Long> ids) {
		return new R<>(combineService.itemList(ids));
	}

	/**
	 * 重新命名规则池
	 *
	 * @param dataRuleVo 包含name,规则详情信息
	 * @return
	 */
	@PostMapping("/nominate_rule")
	@ApiOperation(value = "重新命名规则池", notes = "重新命名规则池")
	public R nominateRule(@RequestBody DataRuleVo dataRuleVo) {
		Long id = combineService.nominateRule(dataRuleVo);
		if (DataCleanConstant.ZERO.equals(id)) {
			R r = new R();
			r.setMsg("规则命名失败~");
			r.setData(false);
			return r;
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("ruleId", id);
			return new R<>(map);
		}
	}

	/**
	 * 不同规则的数据同步
	 *
	 * @param distinctDto
	 * @return
	 */
	@PostMapping("/distinct_data")
	@ApiOperation(value = "不同规则的数据同步", notes = "不同规则的数据同步")
	public R distinctData(@RequestBody DistinctDto distinctDto) {
		return new R<>(combineService.distinctData(distinctDto));
	}

}
