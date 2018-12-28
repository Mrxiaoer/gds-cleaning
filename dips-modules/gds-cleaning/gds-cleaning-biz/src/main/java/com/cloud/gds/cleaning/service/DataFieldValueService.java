package com.cloud.gds.cleaning.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.vo.DataFieldValueTree;
import com.cloud.gds.cleaning.api.vo.DataPoolVo;

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
	 * 获取清洗完的数据
	 * @param fieldId
	 * @return
	 */
	List<DataFieldValue> gainCleanData(Long fieldId);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	DataPoolVo queryById(Long id);

	/**
	 * 清洗接口数据明细
	 * @param id
	 * @return
	 */
	List<DataFieldValue> gainDetails(Long id);

	/**
	 * 修改结果集字段
	 * @param id
	 * @param map
	 * @return
	 */
	Boolean updateJson(Long id,Map<String,Object> map);

	/**
	 * 单独删除
	 * @param id
	 * @return
	 */
	Boolean deleteById(Long id);

	/**
	 * 批量删除
	 * @param ids
	 */
	Boolean deleteByIds(Set<Long> ids);

	/**
	 * 保存单一数据
	 * @param id
	 * @param params
	 * @return
	 */
	Boolean save(Long id, JSONObject params);

	/**
	 * excel工具导数据保存到数据库
	 *
	 * @param maps
	 */
	void saveAll(Long fieldId, List<Map<String, Object>> maps);

	/**
	 * page po 2 vo
	 *
	 * @param page
	 * @return
	 */
	Page pagePo2Vo(Page page);

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
	 * @param id 数据集id
	 * @return String JSON字符串
	 */
	String getAnalysisData(Long fieldId);

}

