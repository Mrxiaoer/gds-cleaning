package com.cloud.gds.gmsanalyse.service;

import com.cloud.gds.gmsanalyse.bo.DeconstructionListBo;
import com.cloud.gds.gmsanalyse.vo.Analyse;

import java.util.List;

public interface AnalyseAlgorithmService {

	/**
	 * 政策分析
	 *
	 * @param bo         分析原料组
	 * @param featureNum 特征数量
	 * @return
	 */
	Analyse policyWonk(DeconstructionListBo bo, Integer featureNum);

	/**
	 * 政策分析method
	 *
	 * @param ids
	 * @return
	 */
	String govAnalysis(List<Long> ids);
}
