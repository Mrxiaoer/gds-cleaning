package com.cloud.gds.gms.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.dips.admin.api.entity.SysDictValue;
import com.cloud.dips.admin.api.vo.DictValueVO;
import com.cloud.gds.gms.service.SysDictValueService;
import com.cloud.gds.gms.mapper.SysDictValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-01
 */
@Service
public class SysDictValueServiceImpl extends ServiceImpl<SysDictValueMapper, SysDictValue> implements SysDictValueService {

	private final SysDictValueMapper mapper;

	@Autowired
	public SysDictValueServiceImpl(SysDictValueMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<DictValueVO> selectDictValueVo(Integer dId) {
		return mapper.selectDictValueVo(dId);
	}
}
