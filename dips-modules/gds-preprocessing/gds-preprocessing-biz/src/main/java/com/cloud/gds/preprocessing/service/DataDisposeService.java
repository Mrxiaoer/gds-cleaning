package com.cloud.gds.preprocessing.service;


/**
 * 国策数据转移接口类
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-21
 */
public interface DataDisposeService {

	/**
	 * 数据迁移 通用
	 *
	 * @param examineUserId
	 * @return
	 */
	boolean dataMigrationSurface(Long examineUserId);

	/**
	 * 数据迁移 解读
	 *
	 * @param examineUserId
	 * @return
	 */
	boolean dataMigrationSurfaceExplain(Long examineUserId);

}
