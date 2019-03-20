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
	public boolean cleanInvalidInScape() {
//		long a = System.currentTimeMillis();
		// 获取相同名称的id与title详情
		List<BasePolicy> list = mapper.gainIdenticalPolicy();
		// 查找出相同名称的ids
//		List<Long> ids = new ArrayList<>();
//		Set<Object> set = new HashSet<>();
//		for (BasePolicy basePolicy : list) {
//			if (set.contains(basePolicy.getTitle())) {
//				ids.add(basePolicy.getId());
//			} else {
//				set.add(basePolicy.getTitle());
//			}
//		}
//		System.out.println(ids.size());
//		long b = System.currentTimeMillis();
//		System.out.println("耗时：" + (b - a));
		return true;
	}
}
