package com.cloud.gds.cleaning.controller;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.utils.CommonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 清洗池
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-07
 */
@RestController
@RequestMapping("/clean_pool")
public class CleanPoolController {

	@Autowired
	DataFieldService dataFieldService;

	/**
	 * 分页
	 * 参数要求：page、limit、name
	 */
	@GetMapping("/page")
	@ApiOperation(value = "查看列表", notes = "根据条件获取列表")
	public R page(@RequestParam Map<String, Object> params) {

		CommonUtils.PiPei pp = CommonUtils.createPP();
//		List<String> eqList = new ArrayList<>();
//		eqList.add("");
//		pp.setEq(eqList);
		List<String> likelist = new ArrayList<>();
		likelist.add("name");
		pp.setLike(likelist);
		Wrapper<DataField> wrapper = CommonUtils.pagePart(params,pp,new DataField());
		Page page = dataFieldService.selectPage(new Query<>(CommonUtils.map2map(params)),wrapper);

		return new R<>(page);
	}

	/**
	 * 根据id查询
	 * @return R
	 */
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Long id) {
		return new R<>(dataFieldService.selectById(id));
	}

	/**
	 * 保存<规则名称信息>
	 * @return R
	 */
	@PostMapping("/create")
	public R save(@RequestBody DataField dataField) {
		return new R<>(dataFieldService.save(dataField));
	}

	/**
	 * 修改
	 * @return R
	 */
	@PostMapping("/update")
	public R update(@RequestBody DataField dataField) {
		if (!"".equals(dataField.getRuleId())){
			Boolean flag = dataFieldService.checkRule(dataField.getId(),dataField.getRuleId());
			if (flag){
				return new R<>(dataFieldService.update(dataField));
			}else {
				return new R(new RuntimeException("规则选择错误!"));
			}
		}
		return new R<>(dataFieldService.update(dataField));
	}

	/**
	 * 单独删除一条
	 * @param id
	 * @return
	 */
	@PostMapping("/delete/{id}")
	public R delete(@PathVariable("id") Long id) {
		if (dataFieldService.deleteById(id)){
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
		return new R<>(dataFieldService.deleteByIds(ids));
	}




}
