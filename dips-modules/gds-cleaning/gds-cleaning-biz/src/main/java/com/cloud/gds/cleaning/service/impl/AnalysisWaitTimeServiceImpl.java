package com.cloud.gds.cleaning.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.gds.cleaning.api.entity.DataField;
import com.cloud.gds.cleaning.mapper.DataFieldMapper;
import com.cloud.gds.cleaning.service.AnalysisWaitTimeService;
import org.springframework.stereotype.Service;

/**
 * 分析等待时间接口实现
 *
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2018-12-12
 */
@Service
public class AnalysisWaitTimeServiceImpl extends ServiceImpl<DataFieldMapper, DataField> implements
	AnalysisWaitTimeService {

	@Override
	public double[] analysisWaitStart(Integer dimension, Integer number) {
		//@todo 简单估测时间，待改进
		double sec = dimension * dimension * number * 0.03 / 1000;
		double waitTime = Math.ceil(sec);
		return new double[]{waitTime, Math.ceil(waitTime * 0.6)};
	}

	@Override
	public double isNeedWait(Long id, double lastTime) {
		DataField df = new DataField();
		df.setId(id);
		df = baseMapper.selectById(df);
		double nextWaitTime = Math.ceil(lastTime * 0.6);
		Integer ok = 2;
		double minTime = 2;
		return ok.equals(df.getAnalyseState()) ? 0 : (nextWaitTime < minTime ? minTime : nextWaitTime);
	}

}
