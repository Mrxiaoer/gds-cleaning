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

	int deleteByIds(@Param("ids") Set<Long> ids);

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

}
