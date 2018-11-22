package com.cloud.dips.admin.api.feign.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cloud.dips.admin.api.feign.RemoteDictService;
import com.cloud.dips.admin.api.vo.DictVauleVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author RCG
 * @date 2018/11/19
 */
@Slf4j

@Component

public class RemoteDictServiceFallbackImpl implements RemoteDictService {
	@Setter
	private Throwable cause;
	
	/**
	 * 
	 * 通过类型查询字典值域列表
	 * 
	 * @param type 类型
	 * 
	 * @return R
	 * 
	 */

	@Override

	public List<DictVauleVO> list(String number) {
		
		log.error("feign 查询字典信息失败:{}", cause);
		
		return null;

	}

}
