package com.cloud.gds.cleaning.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.vo.PageParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import lombok.Data;

/**
 * 常用工具类的封装
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-03
 */
public class CommonUtils {

	/**
	 * 分页页码、每页允许条数获取
	 *
	 * @param params
	 * @return Map 只包含页码（page）和单页大小（limit）；
	 */
	public static Map<String, Object> map2map(Map<String, Object> params) {
		int currPage = 1;
		int limit = 10;
		if (params.get(DataCleanConstant.PAGE) != null) {
			currPage = Integer.parseInt(params.get(DataCleanConstant.PAGE).toString());
		}
		if (params.get(DataCleanConstant.LIMIT) != null) {
			limit = Integer.parseInt(params.get(DataCleanConstant.LIMIT).toString());
		}
		Map<String, Object> map = new HashMap<>(2);
		map.put(DataCleanConstant.PAGE, currPage);
		map.put(DataCleanConstant.LIMIT, limit);
		return map;
	}

	/**
	 * 分页片段
	 *
	 * @param params
	 * @param <T>
	 * @return
	 */
	public static <T> Wrapper<T> pagePart(PageParam<T> params) {

		Wrapper<T> wrapper = new EntityWrapper<T>();

		if (params.getEq() != null) {
			for (String eqKey : params.getEq()) {
				Object eqValue = params.getCondition().get(eqKey);
				if (eqValue != null && StrUtil.isNotBlank(eqValue.toString())) {
					wrapper = wrapper.eq(StrUtil.toUnderlineCase(eqKey), eqValue);
				}
			}
		}
		if (params.getLike() != null) {
			for (String likeKey : params.getLike()) {
				Object likeValue = params.getCondition().get(likeKey);
				if (likeValue != null && StrUtil.isNotBlank(likeValue.toString())) {
					wrapper = wrapper.like(StrUtil.toUnderlineCase(likeKey), likeValue.toString());
				}
			}
		}
		ArrayList<String> coll = new ArrayList<>();
		coll.add("id");
		return wrapper.eq("is_deleted", DataCleanConstant.NO).orderAsc(coll);
	}

	/**
	 * 2个SortedMap比较是否一致
	 *
	 * @param one
	 * @param two
	 * @return
	 */
	public static Boolean checkSortedMap(SortedMap<String, String> one, SortedMap<String, String> two) {
		Set<Map.Entry<String, String>> es = one.entrySet();
		for (Entry<String, String> entry : es) {
			String k = (String) entry.getKey();
			if (two.containsKey(k)) {
				two.remove(k);
			} else {
				return false;
			}
		}
		return two.size() <= 0;
	}

	public static PiPei createPP(){
		return new PiPei();
	}

	@Data
	public static class PiPei{
		private List<String> eq;
		private List<String> like;
	}

}
