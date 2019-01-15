package com.cloud.gds.cleaning.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.dto.InputJsonList;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * 清洗数据池
 *
 * @author lolilijve
 * @date 2018-11-27 09:43:46
 */
@RestController
@RequestMapping("/data_pool")
public class DataPoolController {

	private final DataFieldValueService dataFieldValueService;

	@Autowired
	public DataPoolController(DataFieldValueService dataFieldValueService) {
		this.dataFieldValueService = dataFieldValueService;
	}

	/**
	 * 分页
	 * 参数要求：page、limit、fieldId
	 *
	 * @param params
	 */
	@GetMapping("/page")
	@ApiOperation(value = "分页", notes = "分页")
	public R page(@RequestParam Map<String, Object> params) {
		return new R<>(dataFieldValueService.queryPage(params));
	}

	/**
	 * 根据数据池id查询
	 *
	 * @param id 数据池id
	 * @return R
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "查询", notes = "根据数据池id查询")
	public R info(@PathVariable("id") Long id) {
		return new R<>(dataFieldValueService.queryById(id));
	}

	/**
	 * 保存 数据池信息
	 *
	 * @param params 数据
	 * @param id     清洗池id
	 * @return
	 */
	@PostMapping("/create/{id}")
	@ApiOperation(value = "新增", notes = "新增数据池中数据")
	public R save(@RequestBody JSONObject params, @PathVariable("id") Long id) {
		return new R<>(dataFieldValueService.save(id, params));
	}

	/**
	 * 修改fieldValue
	 *
	 * @param id
	 * @param map
	 * @return
	 */
	@PostMapping("/update/{id}")
	@ApiOperation(value = "更新", notes = "修改fieldValue")
	public R update(@PathVariable("id") Long id, @RequestBody Map<String, Object> map) {
		return new R<>(dataFieldValueService.updateJson(id, map));
	}

	/**
	 * 单独删除
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/delete/{id}")
	@ApiOperation(value = "单删", notes = "单独删除数据池中数据")
	public R delete(@PathVariable("id") Long id) {
		return new R<>(dataFieldValueService.deleteById(id));
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/ids")
	@ApiOperation(value = "批量删除", notes = "批量删除数据池中数据")
	public R deleteByIds(@RequestBody Set<Long> ids) {
		return new R<>(dataFieldValueService.deleteByIds(ids));
	}

	/**
	 * api导入接口
	 *
	 * @param id            主表id
	 * @param inputJsonList
	 * @return
	 */
	@PostMapping("/api")
	public R jsonapi(Long id, @RequestBody InputJsonList inputJsonList) {
		dataFieldValueService.saveAll(id, inputJsonList.getRecords());
		return new R();
	}

	/**
	 * json导入
	 *
	 * @param id
	 * @param jsonArray
	 */
	@PostMapping("/saveJson")
	@ApiOperation(value = "json导入", notes = "json导入")
	public void saveJsonData(long id, @RequestBody JSONArray jsonArray) {
		System.out.println(dataFieldValueService.dataJsonInput(id, jsonArray));
	}

}
