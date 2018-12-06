package com.cloud.dips.admin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.entity.SysDict;
import com.cloud.dips.admin.api.vo.DictVO;
import com.cloud.dips.admin.mapper.SysDictMapper;
import com.cloud.dips.admin.service.SysDictService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author RCG
 * @since 2018-11-19
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

	@Override
	public List<DictVO> selectAllDict() {
		return baseMapper.selectAllDict();
	}

}
