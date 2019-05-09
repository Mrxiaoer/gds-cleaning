package com.cloud.gds.preprocessing.service.impl;

import com.cloud.gds.preprocessing.entity.GovPolicyGeneral;
import com.cloud.gds.preprocessing.mapper.GovPolicyExplainMapper;
import com.cloud.gds.preprocessing.mapper.GovPolicyGeneralMapper;
import com.cloud.gds.preprocessing.mapper.ScrapyGovPolicyExplainMapper;
import com.cloud.gds.preprocessing.mapper.ScrapyGovPolicyGeneralMapper;
import com.cloud.gds.preprocessing.service.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 事务回滚操作
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-22
 */
@Service
public class TransactionalServiceImpl implements TransactionalService {

	private final ScrapyGovPolicyGeneralMapper scrapyMapper;

	private final GovPolicyGeneralMapper govMapper;

	@Autowired
	private ScrapyGovPolicyExplainMapper scrapyExplainMapper;

	@Autowired
	private GovPolicyExplainMapper explainMapper;


	@Autowired
	public TransactionalServiceImpl(ScrapyGovPolicyGeneralMapper scrapyMapper, GovPolicyGeneralMapper govMapper) {
		this.scrapyMapper = scrapyMapper;
		this.govMapper = govMapper;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void bathCutSurface(List<GovPolicyGeneral> list) {
		List<Long> ids = new ArrayList<>();
		for (GovPolicyGeneral policyGeneral : list) {
			ids.add(policyGeneral.getScrapyId());
		}
		govMapper.insertBatch(list);
		scrapyMapper.updateByIdsAndIsDeleted(2L, ids);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void bathCutSurfaceExplain(List<GovPolicyGeneral> list) {
		List<Long> ids = new ArrayList<>();
		for (GovPolicyGeneral policyGeneral : list) {
			ids.add(policyGeneral.getScrapyId());
		}
		explainMapper.insertBatch(list);
		scrapyExplainMapper.updateByIdsAndIsDeleted(7L, ids);
	}
}
