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
		log.error("feign 分析出错", cause);
		return null;
	}

	@Override
	public List<CenterData> gainCleanData(Long fieldId) {
		log.error("feign 中心数据查询失败", cause);
		return null;
	}

	@Override
	public R gainDetails(Long id) {
		log.error("feign 获取数据明细失败", cause);
		return null;
	}

	@Override
	public List<DARVo> centerToSatellite(Long id) {
		log.error("feign 获取卫星数据信息失败", cause);
		return null;
	}

	@Override
	public R cleanDate(List<Map<String, Object>> params) {
		log.error("feign 清洗数据失败", cause);
		return null;
	}

	@Override
	public R automaticCleaning(Long fieldId) {
		log.error("feign 自动分析失败", cause);
		return null;
	}

	// @Override
	// public Map<String, Object> filterMethod(String type, DataDto dataDto) {
	// 	log.error("feign 数据过滤接口获取失败", cause);
	// 	return null;
	// }

	@Override
	public String bigDataAnalysis(String filePath) {
		log.error("feign 数据分析接口获取失败", cause);
		return null;
	}

}
