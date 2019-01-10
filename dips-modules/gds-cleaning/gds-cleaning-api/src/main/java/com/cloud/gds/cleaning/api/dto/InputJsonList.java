package com.cloud.gds.cleaning.api.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-01-09
 */
@Data
public class InputJsonList {

	private List<Map<String, Object>> records;

}
