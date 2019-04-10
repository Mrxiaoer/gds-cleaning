package com.cloud.gds.gmsanalyse.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.gds.gmsanalyse.dto.GovPolicyDto;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyse;

/**
 * 政策分析
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-03
 */
public interface PolicyAnalyseService extends IService<PolicyAnalyse> {

	/**
	 * 保存信息
	 *
	 * @param govPolicyDto
	 * @return
	 */
	PolicyAnalyse save(GovPolicyDto govPolicyDto);


}
