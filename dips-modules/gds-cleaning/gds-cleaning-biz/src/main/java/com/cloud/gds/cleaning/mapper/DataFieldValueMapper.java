package com.cloud.gds.cleaning.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
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

	List<DataFieldValue> selectByCleanIds(@Param("ids") Set<Long> ids);

	List<Long> selectNeedAnalysisIdList(@Param("dataFieldValue") DataFieldValue dataFieldValue);

	List<DataFieldValue> gainCleanData(Long fieldId);

	List<DataFieldValue> gainDetails(Long id);

}
