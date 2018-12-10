package com.cloud.dips.tag.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.tag.api.entity.GovTagModificationRecord;
import com.cloud.dips.tag.api.vo.GovTagModificationRecordVO;

/**
 * <p>
 * 标签修改记录表  Mapper 接口
 * </p>
 *
 * @author ZB
 */
public interface GovTagModificationRecordMapper extends BaseMapper<GovTagModificationRecord> {
	/**
	 * 根据标签查询修改记录
	 * @param tagId 标签id
	 * @param limit 查询条数
	 * @return
	 */
	public List<GovTagModificationRecordVO> getByTagId(@Param("tagId") Integer tagId,@Param("limit") Integer limit);
}
