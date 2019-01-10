package com.cloud.gds.cleaning.api.feign;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.entity.DataRule;
import com.cloud.gds.cleaning.api.feign.factory.DataFieldServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author : lolilijve
 * @Email : 1042703214@qq.com
 * @Date : 2018-11-22
 */
@FeignClient(value = "gds-cleaning", fallbackFactory = DataFieldServiceFallbackFactory.class)
public interface DataFieldService {

	/**
	 * 信息列表
	 *
	 * @param params Map<String, Object>
	 * @return R
	 */
	@GetMapping("/page")
	Page page(@RequestParam Map<String, Object> params);

	/**
	 * 信息
	 *
	 * @param id Long
	 * @return R
	 */
	@GetMapping("/clean_pool/{id}")
	R info(@PathVariable("id") Long id);

	/**
	 * 保存
	 *
	 * @param dataField DataField
	 * @return R
	 */
	@PostMapping
	R save(@RequestBody DataRule dataField);

	/**
	 * 修改
	 *
	 * @param dataField DataField
	 * @return R
	 */
	@PutMapping
	R update(@RequestBody DataRule dataField);

	/**
	 * 删除
	 *
	 * @param id Long
	 * @return R
	 */
	@DeleteMapping("/{id}")
	R delete(@PathVariable("id") Long id);

}
