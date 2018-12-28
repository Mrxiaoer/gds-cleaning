package com.cloud.gds.cleaning.api.vo;

import lombok.Data;

import java.util.List;

/**
 * 清洗结果json
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2018-12-28
 */
@Data
public class ResultJsonVo {

	private Long id;

	private List<GroupVo> group;

}
