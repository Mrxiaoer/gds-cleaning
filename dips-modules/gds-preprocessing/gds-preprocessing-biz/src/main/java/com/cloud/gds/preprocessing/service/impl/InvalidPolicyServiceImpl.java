package com.cloud.gds.preprocessing.service.impl;

import com.cloud.gds.preprocessing.entity.BasePolicy;
import com.cloud.gds.preprocessing.mapper.InvalidPolicyMapper;
import com.cloud.gds.preprocessing.service.InvalidPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 清洗国策数据
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-19
 */
@Service
public class InvalidPolicyServiceImpl implements InvalidPolicyService {

	@Autowired
	private InvalidPolicyMapper mapper;

	@Override
	public boolean cleanIssueData(Integer titleLength, Integer textLength) {

		List<Long> list = mapper.gainIssueId(titleLength, textLength);
		if (list.size() > 0) {
			return mapper.updateScrapyIsDeleted(list);
		} else {
			return true;
		}

	}

	@Override
	public boolean cleanInvalidInScape() {
		long a = System.currentTimeMillis();
		// 获取相同名称的id与title详情
		List<BasePolicy> list = mapper.gainIdenticalPolicy();
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
			return mapper.updateScrapyIsDeleted(ids);
//			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean cleanRepeatScrapy() {
		// 爬取的政策数据
		List<BasePolicy> scrapyPolicy = mapper.gainScrapyPolicy();
		// 正式表中的政策数据
		List<BasePolicy> realPolicy = mapper.gainRealPolicy();
		Set<Object> scrapySet = basePolicy2Set(scrapyPolicy);
		Set<Object> realSet = basePolicy2Set(realPolicy);
		// 查询爬取表与真实表之间的相同title
		scrapySet.retainAll(realSet);
		// 拿取爬取表中与真实表同学标题的id
		List<Long> ids = gainIdInList(scrapyPolicy, scrapySet);

		System.out.println("scrapySet.size():" + scrapySet.size());
		System.out.println("ids.size()" + ids.size());
		System.out.println(ids);

		return false;
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
