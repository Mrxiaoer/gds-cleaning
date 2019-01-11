package com.cloud.gds.cleaning.api.feign.fallback;

import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.dto.DataDto;
import com.cloud.gds.cleaning.api.feign.DataAnalysisService;
import com.cloud.gds.cleaning.api.vo.CenterData;
import com.cloud.gds.cleaning.api.vo.DARVo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-10
 */
@Slf4j
@Component
public class DataAnalysisServiceFallbackImpl implements DataAnalysisService {

	@Setter
	private Throwable cause;

	@Override
	public R setThreshold(Map<String, Object> params) {
		return null;
	}

	@Override
	public List<CenterData> gainCleanData(Long fieldId) {
		return null;
	}

	@Override
	public R gainDetails(Long id) {
		return null;
	}

	@Override
	public List<DARVo> centerToSatellite(Long id) {
		return null;
	}

	@Override
	public R cleanDate(List<Map<String, Object>> params) {
		return null;
	}

	@Override
	public R automaticCleaning(Long fieldId) {
		return null;
	}

	@Override
	public List<DARVo> filterMethod(String type, DataDto dataDto) {
		return null;
	}
}
