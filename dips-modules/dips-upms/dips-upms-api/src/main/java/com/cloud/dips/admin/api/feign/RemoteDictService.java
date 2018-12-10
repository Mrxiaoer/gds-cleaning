package com.cloud.dips.admin.api.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
	 * 
	 * 通过类型查询字典值域列表
	 *
	 * @param type 类型
	 * 
	 * @return R
	 * 
	 */
	@GetMapping("/dict/list/{number}")
	List<DictValueVO> list(@PathVariable("number") String number);
	
	/**
	 * 
	 * 通过类型查询字典值域列表
	 *
	 * @param type 类型
	 * 
	 * @return R
	 * 
	 */
	@GetMapping("/dict/all_list")
	List<DictVO> allList();

}
