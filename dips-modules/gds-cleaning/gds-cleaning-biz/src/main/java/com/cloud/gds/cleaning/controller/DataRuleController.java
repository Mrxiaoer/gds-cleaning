package com.cloud.gds.cleaning.controller;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.security.util.SecurityUtils;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.vo.DataRuleVo;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataRuleService;
import com.cloud.gds.cleaning.utils.CommonUtils;
import com.cloud.gds.cleaning.utils.DataRuleUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 清洗规则
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
	 * 分页
	 * 参数要求：page、limit、name
	 */
	@GetMapping("/page")
	@ApiOperation(value = "查看列表", notes = "根据条件获取列表")
	public R page(@RequestParam Map<String, Object> params) {

		CommonUtils.PiPei pp = CommonUtils.createPP();
		List<String> likelist = new ArrayList<>();
		likelist.add("name");
		pp.setLike(likelist);
		Wrapper<DataRule> wrapper = CommonUtils.pagePart(params,pp,new DataRule());
		Page page = DataRuleUtils.changePage(dataRuleService.selectPage(new Query<>(CommonUtils.map2map(params)),wrapper));

		return new R<>(page);
	}

	/**
	 * 根据id查询
	 * @return R
	 */
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Long id) {
		DataRuleVo dataRuleVo = DataRuleUtils.po2Vo(dataRuleService.selectById(id));
		SecurityUtils.getUser();
		return new R<>(dataRuleVo);
	}

	@GetMapping("/list")
	public R selectAll(){
		return new R(dataRuleService.selectAll());
	}

	@GetMapping("/key")
	public R getKey(@RequestParam Long id){
		DataRuleVo dataRuleVo = DataRuleUtils.po2Vo(dataRuleService.selectById(id));
		return new R(DataRuleUtils.convet(dataRuleVo));
	}

	/**
	 * 保存<规则名称信息>
	 * @return R
	 */
	@PostMapping("/create")
	public R save(@RequestBody DataRuleVo dataRuleVo) {
		return new R<>(dataRuleService.save(dataRuleVo));
	}

	/**
	 * 修改
	 * @return R
	 */
	@PostMapping("/update")
	public R update(@RequestBody DataRuleVo dataRuleVo) {
		DataRule dataRule = DataRuleUtils.vo2po(dataRuleVo);
		dataRule.setModifiedUser(SecurityUtils.getUser().getId());
		dataRule.setModifiedTime(LocalDateTime.now());
		// 如果规则的百分比更新,矩阵文件名称也进行清空
		dataFieldService.updateMatrixFile(dataRuleVo.getDetail().isEmpty() ? 0 : dataRuleVo.getId());
		return new R<>(dataRuleService.updateById(dataRule));
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
		if (dataFieldService.selectByRuleIds(ids).size() > 0){
			return new R(new RuntimeException("规则已被选择，请先取消后再删除!"));
		}else {
			return new R<>(dataRuleService.deleteByIds(ids));
		}
	}

}
