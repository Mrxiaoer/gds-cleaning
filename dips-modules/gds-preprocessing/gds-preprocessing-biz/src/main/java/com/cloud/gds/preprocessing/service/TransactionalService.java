package com.cloud.gds.preprocessing.service;

import com.cloud.gds.preprocessing.entity.GovPolicyGeneral;

import java.util.List;

public interface TransactionalService {

	/**
	 * 事务迁移数据
	 *
	 * @param list
	 */
	void bathCutSurface(List<GovPolicyGeneral> list);
}
