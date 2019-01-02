package com.cloud.gds.cleaning.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataRuleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * 清洗规则池
 *
 * @author lolilijve
 * @date 2018-11-22 10:59:36
 */
@RestController
@RequestMapping("/data_rule")
public class DataRuleController {

	private final DataRuleService dataRuleService;

	@Autowired
	public DataRuleController(DataRuleService dataFieldService) {this.dataRuleService = dataFieldService;}

	@Autowired
	public DataFieldService dataFieldService;

	/**
	 * 规则名称分页
	 * 参数要求：page、limit、name
	 */
	@GetMapping("/page")
	@ApiOperation(value = "查看列表", notes = "根据条件获取列表")
	public R page(@RequestParam Map<String, Object> params) {
		return new R<>(dataRuleService.queryPage(params));
	}

	/**
	 * 根据id查询
	 * @return R
	 */
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Long id) {
		return new R<>(dataRuleService.queryById(id));
	}

	/**
	 * 查询部门规则
	 * @return
	 */
	@GetMapping("/list")
	public R selectAll(){
		return new R<>(dataRuleService.selectAll());
	}

	/**
	 * 获取规则动态参数
	 * @param id
	 * @return
	 */
	@GetMapping("/key/{id}")
	public R getKey(@PathVariable("id") Long id){
		return new R<>(dataRuleService.gainDynamicKey(id));
	}

	/**
	 * 保存 规则名称信息
	 * @return R
	 */
	@PostMapping("/create")
	public R save(@RequestBody DataRuleVo dataRuleVo) {
		return new R<>(dataRuleService.save(dataRuleVo));
	}

	/**
	 * 修改 规则信息
	 * @return R
	 */
	@PostMapping("/update")
	public R update(@RequestBody DataRuleVo dataRuleVo) {
		return new R<>(dataRuleService.customUpdate(dataRuleVo));
	}

	/**
	 * 单独删除一条
	 * @param id
	 * @return
	 */
	@PostMapping("/delete/{id}")
	public R delete(@PathVariable("id") Long id) {
		// 判断该规则其他地方是否使用过
		if (dataFieldService.selectByRuleId(id).size() > 0){
			return new R(new RuntimeException("规则已被选择，请先取消后再删除!"));
		}else if (dataRuleService.deleteById(id)){
			return new R<>(true);
		}else {
			return new R(new RuntimeException("删除失败!"));
		}
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@PostMapping("/delete")
	public R deleteT(@RequestBody Set<Long> ids){
		// 判断规则中是否有一条被使用过
		if (dataFieldService.selectByRuleIds(ids).size() > 0){
			return new R(new RuntimeException("规则已被选择，请先取消后再删除!"));
		}else {
			return new R<>(dataRuleService.deleteByIds(ids));
		}
	}

}
