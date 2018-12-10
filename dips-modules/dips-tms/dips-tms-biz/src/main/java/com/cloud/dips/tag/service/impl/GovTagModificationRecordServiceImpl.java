package com.cloud.dips.tag.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.tag.api.entity.GovTagModificationRecord;
import com.cloud.dips.tag.api.vo.GovTagModificationRecordVO;
import com.cloud.dips.tag.mapper.GovTagModificationRecordMapper;
import com.cloud.dips.tag.service.GovTagModificationRecordService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ZB
 */
@Slf4j
@Service
public class GovTagModificationRecordServiceImpl extends ServiceImpl<GovTagModificationRecordMapper, GovTagModificationRecord>
		implements GovTagModificationRecordService {
	
	@Autowired
	private GovTagModificationRecordMapper mapper;
	
	@Override
	public List<GovTagModificationRecordVO> getByTagId(Integer tagId, Integer limit) {
		return mapper.getByTagId(tagId, limit);
	}

}
