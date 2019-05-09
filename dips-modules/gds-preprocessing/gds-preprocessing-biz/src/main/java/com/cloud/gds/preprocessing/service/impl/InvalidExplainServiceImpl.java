package com.cloud.gds.preprocessing.service.impl;

import com.cloud.gds.preprocessing.entity.BasePolicy;
import com.cloud.gds.preprocessing.mapper.InvalidExplainMapper;
import com.cloud.gds.preprocessing.service.InvalidExplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 申报
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-05-08
 */
@Service
public class InvalidExplainServiceImpl implements InvalidExplainService {

	private final InvalidExplainMapper explainMapper;

	@Autowired
	public InvalidExplainServiceImpl(InvalidExplainMapper explainMapper) {
		this.explainMapper = explainMapper;
	}

	@Override
	public boolean cleanIssueData(Integer titleLength, Integer textLength) {
		List<Long> list = explainMapper.gainIssueId(titleLength, textLength);
		if (list.size() > 0) {
			return explainMapper.updateScrapyIsDeleted(list);
		} else {
			return true;
		}
	}

	@Override
	public boolean cleanInvalidInScape() {
		long a = System.currentTimeMillis();
		// 获取相同名称的id与title详情
		List<BasePolicy> list = explainMapper.gainIdenticalPolicy();
		// 查找出相同名称的ids
		List<Long> ids = new ArrayList<>();
		Set<Object> set = new HashSet<>();
		for (BasePolicy basePolicy : list) {
			if (set.contains(basePolicy.getTitle())) {
				ids.add(basePolicy.getId());
			} else {
				set.add(basePolicy.getTitle());
			}
		}
		// 如果存在重复数据则删除重复的数据
		if (ids.size() > 0) {
			return explainMapper.updateScrapyIsDeleted(ids);
		} else {
			return true;
		}
	}

}
