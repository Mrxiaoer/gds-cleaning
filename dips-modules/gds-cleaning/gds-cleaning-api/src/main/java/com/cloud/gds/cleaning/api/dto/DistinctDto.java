package com.cloud.gds.cleaning.api.dto;

import com.cloud.gds.cleaning.api.vo.LabelVo;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 同步不同规则的数据参数
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-12
 */
@Data
public class DistinctDto {

	private Long newPoolId;

	private Set<Long> oldPoolIds;

	private List<LabelVo> defaultRuleName;

}
