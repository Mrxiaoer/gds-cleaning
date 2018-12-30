package com.cloud.gds.cleaning.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.core.util.SpecialStringUtil;
import com.cloud.gds.cleaning.api.constant.DataCleanConstant;
import com.cloud.gds.cleaning.api.dto.DataPoolAnalysis;
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
	public Page<DataFieldValue> page(@RequestParam Map<String, Object> params){
		// todo 内移->service
		Boolean isAsc = Boolean.parseBoolean(params.getOrDefault("isAsc", Boolean.TRUE).toString());
		Page<DataFieldValue> p=new Page<DataFieldValue>();
		p.setCurrent(Integer.parseInt(params.getOrDefault("page", 1).toString()));
		p.setSize(Integer.parseInt(params.getOrDefault("limit", 10).toString()));
		p.setOrderByField(params.getOrDefault("orderByField", "id").toString());
		p.setAsc(isAsc);
		EntityWrapper<DataFieldValue> e=new EntityWrapper<DataFieldValue>();
		String name=params.getOrDefault("fieldValue", "").toString();
		if(StrUtil.isNotBlank(name)){
			e.like("field_value",  SpecialStringUtil.escapeExprSpecialWord(name));
		}
		e.eq("is_deleted", DataCleanConstant.NO);
		return dataFieldValueService.selectPage(p,e);
	}



	public Page selectPage(@RequestParam Map<String, Object> params){

		return null;
	}


	/**
	 * 清空数据
	 * 由于结果集中有对比清洗前数据,如果需导入不同状态数据需要->清空数据池
	 * @param fieldId 清洗池id
	 * @return
	 */
	@GetMapping("/clear")
	public R clear(@RequestParam Long fieldId){
		return new R<>(dataFieldValueService.clear(fieldId));
	}

	/**
	 * 清缓冲
	 * 由于结果集中有对比清洗前数据,如果清洗后数据与新一套数据再次进行清洗因此需要对已删除的数据进行缓冲清除
	 * @param fieldId
	 * @return
	 */
	@GetMapping("/clear_buffer")
	public R clearBuffer(@RequestParam Long fieldId){
		return new R<>(dataFieldValueService.clearBuffer(fieldId));
	}





}
