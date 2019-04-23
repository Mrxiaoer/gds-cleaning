package com.cloud.gds.gmsanalyse.service;

import com.cloud.gds.gmsanalyse.dto.GovPolicyDto;

import java.io.IOException;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-04
 */
public interface GovAnalyseService {

	/**
	 * 政策进行分析
	 *
	 * @param govPolicyDto
	 * @return
	 */
	void govAnalyse(GovPolicyDto govPolicyDto) throws IOException, ClassNotFoundException;

}
