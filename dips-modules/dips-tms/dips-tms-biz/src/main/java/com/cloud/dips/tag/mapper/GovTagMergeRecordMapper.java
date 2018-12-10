package com.cloud.dips.tag.mapper;


import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.tag.api.entity.GovTagMergeRecord;
import com.cloud.dips.tag.api.vo.CommonVO;

/**
 * <p>
 * 标签合并记录表  Mapper 接口
 * </p>
 *
 * @author ZB
 */
public interface GovTagMergeRecordMapper extends BaseMapper<GovTagMergeRecord> {
	
	/**
	 * 获取被合并标签集合
	 * @param tagId 合并标签id
	 * @return
	 */
	public List<CommonVO> selectMergeTag(Integer tagId);
	/**
	 * 获取合并标签集合
	 * @param mergeId 被合并标签id
	 * @return
	 */
	public List<CommonVO> selectIncludeTag(Integer mergeId);
	
	
}
