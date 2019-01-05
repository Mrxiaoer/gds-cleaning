package com.cloud.gds.cleaning.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.gds.cleaning.api.dto.DataPoolAnalysis;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.vo.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据池接口
 *
 * @author lolilijve
 * @date 2018-11-27 09:43:46
 */
public interface DataFieldValueService extends IService<DataFieldValue> {

	/**
	 * 结果集分页
	 *
	 * @param params
	 * @return
	 */
	Page<DataPoolVo> queryPage(Map<String, Object> params);

	/**
	 * 对比前数据分页
	 *
	 * @param params
	 * @return
	 */
	Page<BaseVo> contrastBeforePage(Map<String, Object> params);

	/**
	 * 对比后数据分页
	 *
	 * @param params
	 * @return
	 */
	Page<BaseVo> contrastAfterPage(Map<String, Object> params);

	/**
	 * 分析结果默认中心数据显示
	 *
	 * @param fieldId
	 * @return
	 */
	List<CenterData> gainCleanData(Long fieldId);

	/**
	 * 根据id查询
	 *
	 * @param id
	 * @return
	 */
	DataPoolVo queryById(Long id);

	/**
	 * 清洗接口数据明细
	 *
	 * @param id
	 * @return
	 */
	List<DataFieldValue> gainDetails(Long id);

	/**
	 * 根据中心数据的Id查看卫星数据信息
	 *
	 * @param centerId
	 * @return
	 */
	List<DARVo> centerToSatellite(Long centerId);

	/**
	 * 根据清洗池id查询
	 *
	 * @param fieldId 清洗池id
	 * @return 数据池集合
	 */
	List<DataFieldValue> selectByfieldId(Long fieldId);

	/**
	 * 修改结果集字段
	 *
	 * @param id
	 * @param map
	 * @return
	 */
	Boolean updateJson(Long id, Map<String, Object> map);

	/**
	 * 单独删除
	 *
	 * @param id
	 * @return
	 */
	Boolean deleteById(Long id);

	/**
	 * 批量删除
	 *
	 * @param ids
	 */
	Boolean deleteByIds(Set<Long> ids);

	/**
	 * 保存单一数据
	 *
	 * @param fieldId 清洗池id
	 * @param params  数据信息
	 * @return
	 */
	Boolean save(Long fieldId, JSONObject params);

	/**
	 * excel工具导数据保存到数据库
	 *
	 * @param maps
	 */
	void saveAll(Long fieldId, List<Map<String, Object>> maps);

	/**
	 * 清洗前后数据比较差异并输出
	 *
	 * @param id 数据集id
	 * @return 被清洗的数据的id集合（value）与标准数据id（key）的map;例：id为2,3的数据被id为1的数据做标准清洗掉了,key为1，value为[2,3];
	 */
	List<DataFieldValueTree> compareDifference(Long id);

	/**
	 * 获取待分析数据
	 *
	 * @param fieldId   数据集id
	 * @param threshold 阀值
	 * @return String JSON字符串
	 */
	String getAnalysisData(Long fieldId, Float threshold);

	/**
	 * 手动清洗数据
	 *
	 * @param params
	 * @return
	 */
	boolean cleanDate(List<Map<String, Object>> params);

	/**
	 * 清空数据
	 *
	 * @param fieldId 清洗池id
	 * @return 是否已清空缓冲标记
	 */
	Boolean clear(Long fieldId);

	/**
	 * 清空缓冲数据
	 *
	 * @param fieldId 清洗池id
	 * @return
	 */
	Boolean clearBuffer(Long fieldId);

}

