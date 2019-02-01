package com.cloud.gds.cleaning.service;

import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lolilijve
 */
public interface DoAnalysisService {

	/**
	 * 获取待分析数据
	 *
	 * @param fieldId   数据集id
	 * @param threshold 阀值
	 * @param oneSize   分发块大小
	 * @return String JSON字符串
	 */
	@Transactional(rollbackFor = Exception.class)
	void handOutAnalysis(long fieldId, float threshold, int oneSize);

	/**
	 * 获取所有待分析数据并存储为文件返回文件路径
	 *
	 * @param fieldId
	 * @param threshold
	 * @return
	 */
	String getAllNeedAnalysisDataFile(Long fieldId, Float threshold);

	/**
	 * 自动清洗
	 *
	 * @param fieldId
	 * @return
	 */
	boolean automaticCleaning(Long fieldId);

	/**
	 * 完全相同的数据id
	 *
	 * @param map
	 * @return
	 */
	List<Long> exactlySame(Map<Long, String> map);

	/**
	 * 获得完全相同的数据id
	 *
	 * @param fieldId
	 * @return
	 */
	List<Long> getExactlySameDataIds(long fieldId);

}
