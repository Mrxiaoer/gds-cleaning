package com.cloud.gds.gmsanalyse.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cloud.dips.admin.api.entity.SysDictValue;
import com.cloud.dips.admin.api.vo.DictValueVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDictValueMapper extends BaseMapper<SysDictValue> {

	/**
	 * 通过字典id查询字典值列表
	 *
	 * @param dId 字典id
	 * @return
	 */
	List<DictValueVO> selectDictValueVo(@Param("dId") Integer dId);
}
