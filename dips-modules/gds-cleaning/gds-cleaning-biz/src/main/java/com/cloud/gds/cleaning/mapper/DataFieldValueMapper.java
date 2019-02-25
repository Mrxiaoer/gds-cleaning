package com.cloud.gds.cleaning.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.gds.cleaning.api.dto.DataPoolAnalysis;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.vo.CenterData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 导入数据的内容
 *
 * @author lolilijve
 * @date 2018-11-27 09:43:46
 */
@Mapper
public interface DataFieldValueMapper extends BaseMapper<DataFieldValue> {

	/**
	 * 批量插入
	 *
	 * @param valueList
	 * @return
	 */
	boolean insertAll(@Param("valueList") List<DataFieldValue> valueList);

	// List<DataFieldValue> selectByCleanIds(@Param("ids") Set<Long> ids);

	// List<Long> selectNeedAnalysisIdList(@Param("dataFieldValue") DataFieldValue dataFieldValue);

	/**
	 * 分析结果默认中心数据显示
	 *
	 * @param fieldId
	 * @return
	 */
	List<CenterData> gainCleanData(Long fieldId);

	/**
	 * 清洗接口数据明细
	 *
	 * @param id
	 * @return
	 */
	List<DataFieldValue> gainDetails(Long id);

	/**
	 * 根据中心数据id重新卫星数据百分比
	 *
	 * @param id
	 * @return
	 */
	List<DataPoolAnalysis> selectDataPool(Long id);

	/**
	 * 根据id与滤网大小查询数据
	 *
	 * @param centerId
	 * @param screenSize
	 * @return
	 */
	List<DataPoolAnalysis> centerFiltration(@Param("centerId") Long centerId, @Param("screenSize") Float screenSize);

	/**
	 * 大数据批量更新
	 *
	 * @param list
	 * @return
	 */
	boolean updateBatchById(@Param("valueList") List<DataFieldValue> list);


	/**
	 * 还原数据
	 *
	 * @param id
	 * @param userId
	 * @return
	 */
	Integer reductionById(@Param("id") Long id, @Param("userId") Integer userId);

	/**
	 * 批量还原数据
	 *
	 * @param ids
	 * @param userId
	 * @return
	 */
	Integer reductionByIds(@Param("ids") Set<Long> ids, @Param("userId") Integer userId);

	/**
	 * 根据fieldId一键还原
	 *
	 * @param fieldId
	 * @param userId
	 * @return
	 */
	Integer oneKeyReduction(@Param("fieldId") Long fieldId, @Param("userId") Integer userId);

	/**
	 * 回收站批量删除
	 *
	 * @param ids
	 * @return
	 */
	Integer recyclingBinClear(@Param("ids") Set<Long> ids);

}
