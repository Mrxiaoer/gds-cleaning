package com.cloud.gds.cleaning.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import lombok.Data;

import java.util.*;

/**
 * 常用工具类的封装
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-03
 */
public class CommonUtils {


	/**
	 * 分页页码、每页允许条数获取
	 * @param params
	 * @return
	 */
	public static Map<String,Object> map2map(Map<String,Object> params ){
		Integer currPage = 1;
		Integer limit = 10;
		if (params.get("page") != null) {
			currPage = Integer.parseInt(params.get("page").toString());
		}
		if (params.get("limit") != null) {
			limit = Integer.parseInt(params.get("limit").toString());
		}
		Map<String,Object> map = new HashMap<>();
		map.put("page", currPage);
		map.put("limit", limit);
		return map;
	}

	/**
	 * 分页片段
	 * @param params
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> Wrapper<T> pagePart(Map<String,Object> params, PiPei pp, T t){

		Wrapper<T> wrapper=new EntityWrapper<T>();

		if(pp.getEq()!=null){
			for(String eqKey:pp.getEq()){
				Object eqValue = params.get(eqKey);
				if(eqValue!=null&&StrUtil.isNotBlank(eqValue.toString())){
					wrapper = wrapper.eq(StrUtil.toUnderlineCase(eqKey), eqValue);
				}
			}
		}
		if(pp.getLike()!=null) {
			for (String likeKey : pp.getLike()) {
				Object likeValue = params.get(likeKey);
				if (likeValue != null && StrUtil.isNotBlank(likeValue.toString())) {
					wrapper = wrapper.like(StrUtil.toUnderlineCase(likeKey), likeValue.toString());
				}
			}
		}
		ArrayList<String> coll =  new ArrayList<String>();
		coll.add("id");
		return wrapper.eq("is_deleted", DataCleanConstant.NO).orderAsc(coll);
	}

	/**
	 * 2个SortedMap比较是否一致
	 * @param one
	 * @param two
	 * @return
	 */
	public static Boolean checkSortedMap(SortedMap<String,String> one, SortedMap<String,String> two){
		Set<Map.Entry<String, String>> es = one.entrySet();
		Iterator<Map.Entry<String,String>> it = es.iterator();
		while (it.hasNext()){
			Map.Entry<String,String> entry =  (Map.Entry<String,String>)it.next();
			String k = (String)entry.getKey();
			if (two.containsKey(k)){
				two.remove(k);
			}else {
				return false;
			}
		}
		if (two.size() > 0){
			return false;
		}
		return true;
	}

	@Data
	public static class PiPei{
		private List<String> eq;
		private List<String> like;
	}

	public static PiPei createPP() {
		return new PiPei();
	}

}
