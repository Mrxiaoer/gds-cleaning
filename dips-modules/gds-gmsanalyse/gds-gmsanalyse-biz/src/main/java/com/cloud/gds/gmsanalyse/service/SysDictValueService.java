package com.cloud.gds.gmsanalyse.service;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.entity.SysDictValue;
import com.cloud.dips.admin.api.vo.DictValueVO;

import java.util.List;

public interface SysDictValueService extends IService<SysDictValue> {

	/**
	 * 通过字典id查询字典值列表
	 *
	 * @param dId
	 * @return
	 */
	List<DictValueVO> selectDictValueVo(Integer dId);
}
