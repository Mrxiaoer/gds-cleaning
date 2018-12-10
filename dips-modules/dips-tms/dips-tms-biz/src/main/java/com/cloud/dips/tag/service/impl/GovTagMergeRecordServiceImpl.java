package com.cloud.dips.tag.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.tag.api.entity.GovTagMergeRecord;
import com.cloud.dips.tag.api.vo.CommonVO;
import com.cloud.dips.tag.mapper.GovTagMergeRecordMapper;
import com.cloud.dips.tag.service.GovTagMergeRecordService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZB
 */
@Slf4j
@Service
public class GovTagMergeRecordServiceImpl extends ServiceImpl<GovTagMergeRecordMapper, GovTagMergeRecord>
		implements GovTagMergeRecordService {
	
	@Override
	public List<CommonVO> selectMergeTag(Integer tagId) {
		return baseMapper.selectMergeTag(tagId);
	}

	@Override
	public List<CommonVO> selectIncludeTag(Integer mergeId) {
		return baseMapper.selectIncludeTag(mergeId);
	}

}
