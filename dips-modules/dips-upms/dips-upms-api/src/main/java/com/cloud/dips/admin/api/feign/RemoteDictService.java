package com.cloud.dips.admin.api.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloud.dips.admin.api.feign.factory.RemoteDictServiceFallbackFactory;
import com.cloud.dips.admin.api.vo.DictVO;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.dips.common.core.constant.ServiceNameConstant;

/**
 * @author RCG
 * @date 2018/11/19
 */
@FeignClient(value = ServiceNameConstant.UMPS_SERVICE, fallbackFactory = RemoteDictServiceFallbackFactory.class)
public interface RemoteDictService {


	/**
	 * 通过类型查询字典值域列表
	 *
	 * @param type 类型
	 * @return R
	 */
	@GetMapping("/dict/list/{number}")
	List<DictValueVO> list(@PathVariable("number") String number);

	/**
	 * 通过类型查询字典值域列表
	 *
	 * @param type 类型
	 * @return R
	 */
	@GetMapping("/dict/all_list")
	List<DictVO> allList();
	
	/**
	 * 通过类型查询字典值域列表
	 *
	 * @param type 类型
	 * @return R
	 */
	@GetMapping("/dict/all_map")
	List<DictVO> getAllMap();

	/**
	 * 通过类型查询字典值域map
	 *
	 * @param numberList 类型
	 * @return R
	 */
	@GetMapping("/dict/map")
	Map<String, List<DictValueVO>> getDictMap(@RequestParam("numberList[]") String[] numberList);

}
