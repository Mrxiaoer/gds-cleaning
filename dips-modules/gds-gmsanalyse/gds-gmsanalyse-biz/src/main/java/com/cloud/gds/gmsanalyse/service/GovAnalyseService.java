package com.cloud.gds.gmsanalyse.service;

import com.cloud.gds.gmsanalyse.dto.GovPolicyDto;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-04
 */
public interface GovAnalyseService {

	/**
	 * 政策分析入口 TODO
	 *
	 * @param govPolicyDto
	 * @return
	 */
	boolean govAnalyse(GovPolicyDto govPolicyDto);

}
