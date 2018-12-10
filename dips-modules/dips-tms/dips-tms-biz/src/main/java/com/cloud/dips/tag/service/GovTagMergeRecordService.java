package com.cloud.dips.tag.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.tag.api.entity.GovTagMergeRecord;
import com.cloud.dips.tag.api.vo.CommonVO;


/**
 * @author ZB
 */
public interface GovTagMergeRecordService extends IService<GovTagMergeRecord> {

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

