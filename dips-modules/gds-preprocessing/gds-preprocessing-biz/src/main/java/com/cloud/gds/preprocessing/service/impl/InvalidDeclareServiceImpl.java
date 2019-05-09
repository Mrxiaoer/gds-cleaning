package com.cloud.gds.preprocessing.service.impl;

import com.cloud.gds.preprocessing.entity.BasePolicy;
import com.cloud.gds.preprocessing.mapper.InvalidDeclareMapper;
import com.cloud.gds.preprocessing.service.InvalidDeclareService;
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
 * @Date : 2019-05-09
 */
@Service
public class InvalidDeclareServiceImpl implements InvalidDeclareService {

	private final InvalidDeclareMapper invalidDeclareMapper;

	@Autowired
	public InvalidDeclareServiceImpl(InvalidDeclareMapper invalidDeclareMapper) {
		this.invalidDeclareMapper = invalidDeclareMapper;
	}

	@Override
	public boolean cleanIssueData(Integer titleLength, Integer textLength) {
		List<Long> list = invalidDeclareMapper.gainIssueId(titleLength, textLength);
		if (list.size() > 0) {
			return invalidDeclareMapper.updateScrapyIsDeleted(list);
		} else {
			return true;
		}
	}

	@Override
	public boolean cleanInvalidInScape() {
		long a = System.currentTimeMillis();
		// 获取相同名称的id与title详情
		List<BasePolicy> list = invalidDeclareMapper.gainIdenticalPolicy();
		// 查找出相同名称的ids
		List<Long> ids = new ArrayList<>();
		if (list.size()>0){
			ids = duplication(list);
		}
		// 如果存在重复数据则删除重复的数据
		if (ids.size() > 0) {
			return invalidDeclareMapper.updateScrapyIsDeleted(ids);
		} else {
			return true;
		}
	}

	private List<Long> duplication(List<BasePolicy> list){
		List<Long> ids = new ArrayList<>();
		Set<Object> set = new HashSet<>();
		for (BasePolicy basePolicy : list) {
			if (set.contains(basePolicy.getTitle())) {
				ids.add(basePolicy.getId());
			} else {
				set.add(basePolicy.getTitle());
			}
		}
		return ids;
	}

	private Set<Object> basePolicy2Set(List<BasePolicy> list) {
		Set<Object> set = new HashSet<>();
		for (BasePolicy basePolicy : list) {
			set.add(basePolicy.getTitle());
		}
		return set;
	}

	/**
	 * 从list中找与set里面名称一样的id
	 *
	 * @param list
	 * @param set
	 * @return
	 */
	private List<Long> gainIdInList(List<BasePolicy> list, Set<Object> set) {
		List<Long> ids = new ArrayList<>();
		for (BasePolicy basePolicy : list) {
			if (set.contains(basePolicy.getTitle())) {
				ids.add(basePolicy.getId());
			}
		}
		return ids;
	}

}
