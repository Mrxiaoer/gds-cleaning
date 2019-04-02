package com.cloud.gds.gmsanalyse.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.dips.admin.api.entity.SysDict;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.gds.gmsanalyse.service.SysDictService;
import com.cloud.gds.gmsanalyse.service.SysDictValueService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 国策查询条件字典
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-01
 */
@RestController
@RequestMapping("/gov/dict")
public class DictController {

	private final SysDictService service;

	private final SysDictValueService valueService;

	@Autowired
	public DictController(SysDictService service, SysDictValueService valueService) {
		this.service = service;
		this.valueService = valueService;
	}

	@GetMapping("/map")
	@ApiOperation(value = "通过字典编码集合查询字典值信息集合", notes = "通过字典编码集合查询字典值信息集合", httpMethod = "GET")
	public Map<String, List<DictValueVO>> getDictMap(@RequestParam("numberList[]") String[] numberList) {
		Map<String, List<DictValueVO>> dictMap = new HashMap<>(0);
		for (String number : numberList) {
			dictMap.put(number, dictValueList(number));
		}
		return dictMap;
	}

	@GetMapping("/list/{number}")
	@ApiOperation(value = "通过字典编码查询字典值信息", notes = "通过字典编码查询字典值信息", httpMethod = "GET")
	public List<DictValueVO> dictValueList(@PathVariable("number") String number) {
		EntityWrapper<SysDict> e = new EntityWrapper<SysDict>();
		e.eq("number", number);
		SysDict sysDict = service.selectOne(e);
		if (sysDict != null) {
			return valueService.selectDictValueVo(sysDict.getId());
		} else {
			return null;
		}
	}

}
