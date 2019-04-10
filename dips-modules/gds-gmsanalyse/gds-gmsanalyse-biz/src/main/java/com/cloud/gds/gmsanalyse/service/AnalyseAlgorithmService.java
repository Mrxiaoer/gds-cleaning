package com.cloud.gds.gmsanalyse.service;

import java.util.List;

public interface AnalyseAlgorithmService {

	/**
	 * 政策分析method
	 *
	 * @param ids
	 * @return
	 */
	String govAnalysis(List<Long> ids);
}
