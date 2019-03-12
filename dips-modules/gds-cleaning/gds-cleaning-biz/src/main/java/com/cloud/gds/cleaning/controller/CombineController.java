package com.cloud.gds.cleaning.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.service.CombineService;
import com.cloud.gds.cleaning.service.DataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 合并清洗池
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
	 * 分页
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	public R gainCombinePage(@RequestParam Map<String, Object> params) {
		return new R<>(dataFieldService.queryPage(params));
	}


	/**
	 * 根据清洗池查询相同规则的其他清洗池
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/get_distinct/{id}")
	public R getIdenticalCleanPoolT(@PathVariable("id") Long id) {
		return new R<>(combineService.getIdenticalCleanPool(id));
	}

	/**
	 * 命名新数据池的名称
	 *
	 * @param dataField 包含name、ruleId
	 * @return
	 */
	@GetMapping("/nominate_clean_pool")
	public R nominateCleanPool(DataField dataField) {
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
	 * @param params
	 * @return
	 */
	@PostMapping("/regularization")
	public R regularizationData(@RequestBody Map<String, Object> params) {
		return new R<>(combineService.regularizationData(params));
	}

	/**
	 * 获取清洗池的规则项
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/item_list")
	public R itemList(@RequestBody Set<Long> ids) {
		//todo 2019-3-12 9:28:07
		return new R<>(combineService.itemList(ids));
	}

	/**
	 * 重新命名规则池
	 *
	 * @param dataRuleVo 包含name,规则详情信息
	 * @return
	 */
	@PostMapping("/nominate_rule")
	public R nominateRule(DataRuleVo dataRuleVo) {
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

}
