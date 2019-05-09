package com.cloud.gds.preprocessing.service;

import com.cloud.gds.preprocessing.entity.GovPolicyGeneral;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionalService {

	/**
	 * 事务迁移数据 通用
	 *
	 * @param list
	 */
	void bathCutSurface(List<GovPolicyGeneral> list);

	/**
	 * 事务迁移数据 申报
	 *
	 * @param list
	 */
	void bathCutSurfaceExplain(List<GovPolicyGeneral> list);
}
