package com.cloud.gds.cleaning.api.feign;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
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
@FeignClient(value = DataCleanConstant.MODULE_NAME, fallbackFactory = CleanPoolServiceFallbackFactory.class)
public interface CleanPoolService {

	/**
	 * 分页查询
	 *
	 * @param params 参数要求：page、limit、name
	 * @return R
	 */
	@GetMapping("/clean_pool/page")
	public R page(@RequestParam Map<String, Object> params);

	/**
	 * 根据id查询
	 *
	 * @return R
	 */
	@GetMapping("/clean_pool/{id}")
	public R info(@PathVariable("id") Long id);

	/**
	 * 保存
	 *
	 * @return R
	 */
	@PostMapping("/clean_pool/create")
	public R save(@RequestBody DataField dataField);

	/**
	 * 修改
	 *
	 * @return R
	 */
	@PostMapping("/clean_pool/update")
	public R update(@RequestBody DataField dataField);

	/**
	 * 单独删除一条
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/clean_pool/delete/{id}")
	public R delete(@PathVariable("id") Long id);

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/clean_pool/delete")
	public R deleteByIds(@RequestBody Set<Long> ids);
}
