package com.cloud.gds.cleaning.api.feign;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.api.feign.factory.CleanPoolServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * ate : 2019-01-10
 */
@FeignClient(value = "gds-cleaning", fallbackFactory = CleanPoolServiceFallbackFactory.class)
public interface CleanPoolService {

	/**
	 * 分页查询
	 *
	 * @param params 参数要求：page、limit、name
	 * @return R
	 */
	@GetMapping("/page")
	public R page(@RequestParam Map<String, Object> params);

	/**
	 * 根据id查询
	 *
	 * @return R
	 */
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Long id);

	/**
	 * 保存
	 *
	 * @return R
	 */
	@PostMapping("/create")
	public R save(@RequestBody DataField dataField);

	/**
	 * 修改
	 *
	 * @return R
	 */
	@PostMapping("/update")
	public R update(@RequestBody DataField dataField);

	/**
	 * 单独删除一条
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/delete/{id}")
	public R delete(@PathVariable("id") Long id);

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/delete")
	public R deleteByIds(@RequestBody Set<Long> ids);
}
