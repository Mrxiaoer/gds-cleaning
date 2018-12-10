package com.cloud.dips.admin.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysDict;
import com.cloud.dips.admin.api.vo.DictVO;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
public interface SysDictMapper extends BaseMapper<SysDict> {
	/**
	 * 查询所有字典
	 * @return
	 */
	public List<DictVO> selectAllDict();

}
