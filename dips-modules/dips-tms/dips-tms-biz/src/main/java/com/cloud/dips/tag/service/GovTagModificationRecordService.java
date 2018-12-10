package com.cloud.dips.tag.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.tag.api.entity.GovTagModificationRecord;
import com.cloud.dips.tag.api.vo.GovTagModificationRecordVO;

/**
 * @author ZB
 */
public interface GovTagModificationRecordService extends IService<GovTagModificationRecord> {

	/**
	 * 根据标签查询修改记录
	 * @param tagId 标签id
	 * @param limit 查询条数
	 * @return
	 */
	public List<GovTagModificationRecordVO> getByTagId(Integer tagId,Integer limit);
}

