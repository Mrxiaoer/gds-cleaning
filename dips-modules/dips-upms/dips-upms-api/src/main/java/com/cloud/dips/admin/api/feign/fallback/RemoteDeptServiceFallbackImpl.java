package com.cloud.dips.admin.api.feign.fallback;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloud.dips.admin.api.feign.RemoteDeptService;
import com.cloud.dips.admin.api.vo.DeptCityVO;
import com.cloud.dips.admin.api.vo.DeptVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BigPan
 */
@Slf4j
@Component
public class RemoteDeptServiceFallbackImpl implements RemoteDeptService {
	@Setter
	private Throwable cause;

	@Override
	public List<DeptCityVO> list() {
		log.error("feign 查询机构信息失败:{}", cause);
		return null;
	}

	@Override
	public Integer findDeptIdByName(String name) {
		log.error("feign 查询机构信息失败:{}", cause);
		return null;
	}

	@Override
	public Map<Integer, DeptCityVO> getDeptCityVOMap() {
		log.error("feign 查询机构信息失败:{}", cause);
		return null;
	}

	@Override
	public DeptVO get(Integer id) {
		log.error("feign 查询机构信息失败:{}", cause);
		return null;
	}
}
