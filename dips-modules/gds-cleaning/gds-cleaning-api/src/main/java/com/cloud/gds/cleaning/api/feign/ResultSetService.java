package com.cloud.gds.cleaning.api.feign;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.feign.factory.CleanPoolServiceFallbackFactory;
import com.cloud.gds.cleaning.api.vo.CleanItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@FeignClient(value = "gds-cleaning", fallbackFactory = CleanPoolServiceFallbackFactory.class)
public interface ResultSetService {

	/**
	 * 结果集详情分页
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/result_set/page")
	public R page(@RequestParam Map<String, Object> params);

	/**
	 * 对比详情前数据
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/result_set/before_comparison")
	public R contrastBefore(@RequestParam Map<String, Object> params);

	/**
	 * 对比详情后数据
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/result_set/after_comparison")
	public R contrastAfter(@RequestParam Map<String, Object> params);

	/**
	 * 清洗项
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/result_set/cleaning_item/{id}")
	public List<CleanItem> cleaningItem(@PathVariable("id") Long id);

	/**
	 * 清空数据
	 * 由于结果集中有对比清洗前数据,如果需导入不同状态数据需要->清空数据池
	 *
	 * @param fieldId 清洗池id
	 * @return
	 */
	@GetMapping("/result_set/clear/{fieldId}")
	public R clear(@PathVariable("fieldId") Long fieldId);

	/**
	 * 清理缓存
	 * 由于结果集中有对比清洗前数据,如果清洗后数据与新一套数据再次进行清洗因此需要对已删除的数据进行缓存清除
	 *
	 * @param fieldId
	 * @return
	 */
	@GetMapping("/result_set/clear_buffer/{fieldId}")
	public R clearBuffer(@PathVariable("fieldId") Long fieldId);
}
