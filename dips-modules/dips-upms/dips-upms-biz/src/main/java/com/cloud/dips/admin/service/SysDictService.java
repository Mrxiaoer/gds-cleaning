package com.cloud.dips.admin.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.dips.admin.api.entity.SysDict;
import com.cloud.dips.admin.api.vo.DictVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
public interface SysDictService extends IService<SysDict> {
	
	/**
	 * 查询所有字典
	 * @return
	 */
	public List<DictVO> selectAllDict();

}
