package com.cloud.gds.cleaning.controller;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.service.DataFieldService;
import com.cloud.gds.cleaning.service.DataFieldValueService;
import com.cloud.gds.cleaning.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 结果集
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-19
 */
@RestController
@RequestMapping("/result_set")
public class ResultSetController {

	@Autowired
	DataFieldValueService dataFieldValueService;

	/**
	 * 分页
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	public R page(@RequestParam Map<String, Object> params){
		CommonUtils.PiPei pp = CommonUtils.createPP();
		List<String> eqList = new ArrayList<>();
		eqList.add("fieldId");
		eqList.add("remark");
		pp.setEq(eqList);
//		List<String> likelist = new ArrayList<>();
//		likelist.add("");
//		pp.setLike(likelist);
		Wrapper<DataFieldValue> wrapper = CommonUtils.pagePart(params,pp,new DataFieldValue());
		Page page = dataFieldValueService.pagePo2Vo(dataFieldValueService.selectPage(new Query<>(CommonUtils.map2map(params)),wrapper.eq("be_cleaned_id", DataCleanConstant.NO)));

		return new R(page);
	}





}
