package com.cloud.gds.cleaning.controller;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.vo.PageParam;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataRuleService;
import com.cloud.gds.cleaning.utils.CommonUtils;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	private final DataRuleService dataRuleService;

	@Autowired
	public CleanPoolController(DataFieldService dataFieldService, DataRuleService dataRuleService) {
		this.dataFieldService = dataFieldService;
		this.dataRuleService = dataRuleService;
	}

	/**
	 * 分页查询
	 *
	 * @param params 参数要求：page、limit、name
	 * @return R
	 */
	@GetMapping("/page")
	@ApiOperation(value = "查看列表", notes = "根据条件获取列表")
	public R page(@RequestBody PageParam<DataField> params) {

		Wrapper<DataField> wrapper = CommonUtils.pagePart(params, new DataField());
		Page page = dataFieldService.selectPage(params, wrapper);

		return new R<>(page);
	}

	/**
	 * 根据id查询
	 *
	 * @return R
	 */
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Long id) {
		return new R<>(dataFieldService.queryById(id));
	}

	/**
	 * 保存
	 *
	 * @return R
	 */
	@PostMapping("/create")
	public R save(@RequestBody DataField dataField) {
		return new R<>(dataFieldService.save(dataField));
	}

	/**
	 * 修改
	 *
	 * @return R
	 */
	@PostMapping("/update")
	public R update(@RequestBody DataField dataField) {
		if (dataField.getRuleId() != null) {
			Boolean flag = dataFieldService.checkRule(dataField.getId(), dataField.getRuleId());
			if (flag) {
				return new R<>(dataFieldService.update(dataField));
			} else {
				return new R(new RuntimeException("规则选择错误!"));
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
	public R deleteT(@RequestBody Set<Long> ids) {
		return new R<>(dataFieldService.deleteByIds(ids));
	}

}
