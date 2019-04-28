package com.cloud.gds.gmsanalyse.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.gds.gmsanalyse.bo.DeconstructionListBo;
import com.cloud.gds.gmsanalyse.entity.PolicyDeconstruction;

import java.io.IOException;
import java.util.List;

/**
 * 政策解构
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-19
 */
public interface PolicyDeconstructionService extends IService<PolicyDeconstruction> {

	/**
	 * 分析解构的原材料
	 *
	 * @param ids
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	DeconstructionListBo gainMaterials(List<Long> ids) throws IOException, ClassNotFoundException;

	/**
	 * 根据ids查询解构表中不存在的id
	 *
	 * @param ids
	 * @return
	 */
	List<Long> deconstructionNonExistent(List<Long> ids);
}
