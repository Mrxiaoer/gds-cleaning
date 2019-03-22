package com.cloud.gds.preprocessing.service;

import com.cloud.gds.preprocessing.entity.GovPolicyGeneral;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 国策数据转移接口类
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-21
 */
public interface DataDisposeService {

	/**
	 * 数据迁移
	 *
	 * @param examineUserId
	 * @return
	 */
	boolean dataMigrationSurface(Long examineUserId);

}
