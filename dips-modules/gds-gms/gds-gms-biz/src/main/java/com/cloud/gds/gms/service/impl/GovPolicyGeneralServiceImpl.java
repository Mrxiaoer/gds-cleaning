package com.cloud.gds.gms.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.common.core.util.Query;
import com.cloud.gds.gms.api.constant.GmsConstant;
import com.cloud.gds.gms.api.entity.GovPolicyGeneral;
import com.cloud.gds.gms.mapper.GovPolicyGeneralMapper;
import com.cloud.gds.gms.service.GovPolicyGeneralService;
import com.cloud.gds.gms.service.SortingDataService;
import com.cloud.gds.gms.api.vo.GeneralVO;
import com.cloud.gds.gms.api.vo.GovAnalyseVo;
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

		if (GmsConstant.PUSH_TIME.equals(sort)) {
			map.put(GmsConstant.PUSH_TIME, sort);
		}
		if (GmsConstant.CLICK_VIEWS.equals(sort)) {
			map.put(GmsConstant.CLICK_VIEWS, sort);
		}
		List<GeneralVO> selectAll = policyGeneralMapper.selectAll(query, map);
//		List<GeneralVO> selectAll = policyGeneralMapper.selectAll(map);
		query.setRecords(selectAll);

		return query;

	}

	@Override
	public List<GovAnalyseVo> gainAll(Map<String, Object> params) {
		if (null != params.get("title") && !"".equals(params.get("title"))) {
			// 去除空格，并添加匹配符
			String string = params.get("title").toString().trim();
			String title = sortingDataService.replaceSpecialSign(string);
			params.put("title", title);
		}
		if (null == params.get("title")) {
			params.put("title", params.get("title"));
		}
		if (params.size() > 1) {
			return  policyGeneralMapper.queryAll(params);
		}else {
			return null;
		}

	}

	@Override
	public List<Long> gainList(Map<String, Object> params) {
		if (null != params.get("title") && !"".equals(params.get("title"))) {
			// 去除空格，并添加匹配符
			String string = params.get("title").toString().trim();
			String title = sortingDataService.replaceSpecialSign(string);
			params.put("title", title);
		}
		if (null == params.get("title")) {
			params.put("title", params.get("title"));
		}
		if (params.size() > 1) {
			return  policyGeneralMapper.gainList(params);
		}else {
			return null;
		}
	}
}
