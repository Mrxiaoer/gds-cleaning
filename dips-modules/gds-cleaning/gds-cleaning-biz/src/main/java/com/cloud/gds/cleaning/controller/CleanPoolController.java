package com.cloud.gds.cleaning.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataRuleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * 清洗池
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-07
 */
@RestController
@RequestMapping("/clean_pool")
public class CleanPoolController {

	private final DataFieldService dataFieldService;

	@Autowired
	public CleanPoolController(DataFieldService dataFieldService) {
		this.dataFieldService = dataFieldService;
	}

	/**
	 * 分页查询
	 *
	 * @param params 参数要求：page、limit、name
	 * @return R
	 */
	@GetMapping("/page")
	@ApiOperation(value = "查看列表", notes = "根据条件获取列表")
	public R page(@RequestParam Map<String, Object> params) {
		return new R<>(dataFieldService.queryPage(params));
	}

	/**
	 * 根据id查询
	 *
	 * @return R
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "查询", notes = "根据清洗池主键查询")
	public R info(@PathVariable("id") Long id) {
		return new R<>(dataFieldService.queryById(id));
	}

	/**
	 * 保存
	 *
	 * @return R
	 */
	@PostMapping("/create")
	@ApiOperation(value = "保存", notes = "保存清洗池数据")
	public R save(@RequestBody DataField dataField) {
		return new R<>(dataFieldService.save(dataField));
	}

	/**
	 * 修改
	 *
	 * @return R
	 */
	@PostMapping("/update")
	@ApiOperation(value = "更新", notes = "更新清洗池数据")
	public R update(@RequestBody DataField dataField) {
		if (dataField.getRuleId() != null) {
			Boolean flag = dataFieldService.checkRule(dataField.getId(), dataField.getRuleId());
			if (flag) {
				return new R<>(dataFieldService.update(dataField));
			} else {
				return new R(new RuntimeException("规则更换失败，请检查规则是否符合数据要求!"));
			}
		}
		return new R<>(dataFieldService.update(dataField));
	}

	/**
	 * 单独删除一条
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/delete/{id}")
	@ApiOperation(value = "单删", notes = "根据清洗池主键删除")
	public R delete(@PathVariable("id") Long id) {
		if (dataFieldService.deleteById(id)) {
			return new R<>(true);
		} else {
			return new R(new RuntimeException("删除失败!"));
		}
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/delete")
	@ApiOperation(value = "批量删除", notes = "根据清洗池主键批量删除")
	public R deleteByIds(@RequestBody Set<Long> ids) {
		return new R<>(dataFieldService.deleteByIds(ids));
	}

}
