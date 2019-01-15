package com.cloud.gds.cleaning.api.feign;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.dto.InputJsonList;
import com.cloud.gds.cleaning.api.feign.factory.DataPoolServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@FeignClient(value = "gds-cleaning", fallbackFactory = DataPoolServiceFallbackFactory.class)
public interface DataPoolService {

	/**
	 * 分页
	 * 参数要求：page、limit、fieldId
	 *
	 * @param params
	 */
	@GetMapping("/data_pool/page")
	public R page(@RequestParam Map<String, Object> params);

	/**
	 * 根据数据池id查询
	 *
	 * @param id 数据池id
	 * @return R
	 */
	@GetMapping("/data_pool/{id}")
	public R info(@PathVariable("id") Long id);

	/**
	 * 保存 数据池信息
	 *
	 * @param params 数据
	 * @param id     清洗池id
	 * @return
	 */
	@PostMapping("/data_pool/create/{id}")
	public R save(@RequestBody JSONObject params, @PathVariable("id") Long id);

	/**
	 * 修改fieldValue
	 *
	 * @param id
	 * @param map
	 * @return
	 */
	@PostMapping("/data_pool/update/{id}")
	public R update(@PathVariable("id") Long id, @RequestBody Map<String, Object> map);

	/**
	 * 单独删除
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/data_pool/delete/{id}")
	public R delete(@PathVariable("id") Long id);

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/data_pool/ids")
	public R deleteByIds(@RequestBody Set<Long> ids);


	/**
	 * api导入接口
	 *
	 * @param id            主表id
	 * @param inputJsonList
	 * @return
	 */
	@PostMapping("/data_pool/api")
	public R jsonapi(Long id, @RequestBody InputJsonList inputJsonList);

	/**
	 * json导入
	 *
	 * @param id
	 * @param jsonArray
	 */
	@PostMapping("/saveJson")
	public void saveJsonData(long id, @RequestBody JSONArray jsonArray);

}
