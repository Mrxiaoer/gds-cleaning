package com.cloud.gds.cleaning.utils;

import com.alibaba.fastjson.JSONObject;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.api.vo.DataPoolVo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;

/**
 * 清洗池常用方法
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-11
 */
public class DataPoolUtils {

	/**
	 * string格式的enity转json格式的vo
	 *
	 * @param dataFieldValue
	 * @return
	 */
	public static DataPoolVo entity2Vo(DataFieldValue dataFieldValue) {
		DataPoolVo vo = new DataPoolVo();
		// String 2 json object
		JSONObject myJson = JSONObject.parseObject(dataFieldValue.getFieldValue());

		// 赋值
		BeanUtils.copyProperties(dataFieldValue, vo);
		vo.setFieldValue(myJson);

		return vo;
	}

	/**
	 * json格式的vo转string格式的entity
	 *
	 * @param dataPoolVo
	 * @return
	 */
	public static DataFieldValue vo2Entity(DataPoolVo dataPoolVo) {
		DataFieldValue entity = new DataFieldValue();

		// json object 2 String
		String value = dataPoolVo.getFieldValue().toJSONString();

		// 赋值
		BeanUtils.copyProperties(dataPoolVo, entity);
		entity.setFieldValue(value);

		return entity;
	}

	/**
	 * list po 2 vo
	 *
	 * @param dataFieldValues
	 * @return
	 */
	public static List<DataPoolVo> listEntity2Vo(List<DataFieldValue> dataFieldValues) {

		List<DataPoolVo> vos = new ArrayList<>();

		for (DataFieldValue temp : dataFieldValues) {

			DataPoolVo vo = DataPoolUtils.entity2Vo(temp);
			vos.add(vo);

		}
		return vos;
	}

	/**
	 * list vo 2 po
	 *
	 * @param dataPoolVos
	 * @return
	 */
	public static List<DataFieldValue> listVo2Entity(List<DataPoolVo> dataPoolVos) {

		List<DataFieldValue> entitys = new ArrayList<>();

		for (DataPoolVo temp : dataPoolVos) {

			DataFieldValue entity = DataPoolUtils.vo2Entity(temp);
			entitys.add(entity);

		}
		return entitys;
	}

}
