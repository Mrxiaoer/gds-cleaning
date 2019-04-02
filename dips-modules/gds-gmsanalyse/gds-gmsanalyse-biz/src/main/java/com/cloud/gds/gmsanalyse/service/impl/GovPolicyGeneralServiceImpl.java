package com.cloud.gds.gmsanalyse.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.Query;
import com.cloud.gds.gmsanalyse.constant.GovConstant;
import com.cloud.gds.gmsanalyse.entity.GovPolicyGeneral;
import com.cloud.gds.gmsanalyse.mapper.GovPolicyGeneralMapper;
import com.cloud.gds.gmsanalyse.service.GovPolicyGeneralService;
import com.cloud.gds.gmsanalyse.service.SortingDataService;
import com.cloud.gds.gmsanalyse.vo.GeneralVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-29
 */
@Service
public class GovPolicyGeneralServiceImpl extends ServiceImpl<GovPolicyGeneralMapper, GovPolicyGeneral> implements GovPolicyGeneralService {

	private final SortingDataService sortingDataService;

	private final GovPolicyGeneralMapper policyGeneralMapper;

	@Autowired
	public GovPolicyGeneralServiceImpl(SortingDataService sortingDataService, GovPolicyGeneralMapper policyGeneralMapper) {
		this.sortingDataService = sortingDataService;
		this.policyGeneralMapper = policyGeneralMapper;
	}

	@Override
	public Page<GeneralVO> selectAllPage(Query query) {

		Map<String, Object> map = new HashMap<>(0);
		map.put("startTime", query.getCondition().get("startTime"));
		map.put("endTime", query.getCondition().get("endTime"));
		map.put("views", query.getCondition().get("views"));
		map.put("username", query.getCondition().get("username"));
		map.put("formality", query.getCondition().get("formality"));
		map.put("style", query.getCondition().get("style"));
		map.put("level", query.getCondition().get("level"));
		map.put("stage", query.getCondition().get("stage"));
		map.put("target", query.getCondition().get("target"));
		map.put("theme", query.getCondition().get("theme"));
		map.put("scale", query.getCondition().get("scale"));
		map.put("industry", query.getCondition().get("industry"));
		map.put("timeliness", query.getCondition().get("timeliness"));
		map.put("regionArr", query.getCondition().get("region"));
		map.put("prop", query.getCondition().get("prop"));
		map.put("order", query.getCondition().get("order"));
		Object sort = query.getCondition().get("sort");
		if (null != query.getCondition().get("title") && !"".equals(query.getCondition().get("title"))) {
			// 去除空格，并添加匹配符
			String string = query.getCondition().get("title").toString().trim();
			String title = sortingDataService.replaceSpecialSign(string);
			map.put("title", title);
		}
		if (null == query.getCondition().get("title")) {
			map.put("title", query.getCondition().get("title"));
		}

		if (GovConstant.PUSH_TIME.equals(sort)) {
			map.put(GovConstant.PUSH_TIME, sort);
		}
		if (GovConstant.CLICK_VIEWS.equals(sort)) {
			map.put(GovConstant.CLICK_VIEWS, sort);
		}
		List<GeneralVO> selectAll = policyGeneralMapper.selectAll(query, map);
//		List<GeneralVO> selectAll = policyGeneralMapper.selectAll(map);
		query.setRecords(selectAll);

		return query;

	}
}
