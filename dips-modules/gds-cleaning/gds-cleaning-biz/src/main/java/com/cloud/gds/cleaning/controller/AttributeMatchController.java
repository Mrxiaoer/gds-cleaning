package com.cloud.gds.cleaning.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.entity.AttributeMatch;
import com.cloud.gds.cleaning.service.AttributeMatchService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 属性
 *
 * @author lolilijve
 * @date 2018-11-27 09:27:28
 */
@RestController
@RequestMapping("/attribute_match")
public class AttributeMatchController {

	private final AttributeMatchService attributeMatchService;

	@Autowired
	public AttributeMatchController(
		AttributeMatchService attributeMatchService) {this.attributeMatchService = attributeMatchService;}

	/**
	 * 列表
	 *
	 * @param params
	 */
	@GetMapping("/page")
	public Page page(@RequestParam Map<String, Object> params) {
		return attributeMatchService.selectPage(new Query<>(params), new EntityWrapper<>());
	}

	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Long id) {
		return new R<>(attributeMatchService.selectById(id));
	}

	/**
	 * 保存
	 *
	 * @param attributeMatch
	 * @return R
	 */
	@PostMapping
	public R save(@RequestBody AttributeMatch attributeMatch) {
		return new R<>(attributeMatchService.insert(attributeMatch));
	}

	/**
	 * 修改
	 *
	 * @param attributeMatch
	 * @return R
	 */
	@PutMapping
	public R update(@RequestBody AttributeMatch attributeMatch) {
		return new R<>(attributeMatchService.updateById(attributeMatch));
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return R
	 */
	@DeleteMapping("/{id}")
	public R delete(@PathVariable Long id) {
		return new R<>(attributeMatchService.deleteById(id));
	}

}
