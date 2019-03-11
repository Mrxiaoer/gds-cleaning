package com.cloud.gds.cleaning.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.service.CombineService;
import com.cloud.gds.cleaning.service.DataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

}
