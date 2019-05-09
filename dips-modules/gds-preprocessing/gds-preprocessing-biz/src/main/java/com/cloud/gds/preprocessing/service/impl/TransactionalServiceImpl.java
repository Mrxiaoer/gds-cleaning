package com.cloud.gds.preprocessing.service.impl;

import com.cloud.gds.preprocessing.entity.GovPolicyGeneral;
import com.cloud.gds.preprocessing.mapper.*;
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

	private final InvalidDeclareMapper invalidDeclareMapper;
	private final GovPolicyDeclareMapper govPolicyDeclareMapper;
	private final InvalidExplainMapper invalidExplain;
	private final InvalidInformationMapper invalidInformation;
	private final GovPolicyInformationMapper govPolicyInformationMapper;
	private final GovPolicyGeneralMapper govMapper;
	private final GovPolicyExplainMapper explainMapper;
	@Autowired
	private InvalidPolicyMapper invalidPolicyMapper;

	@Autowired
	public TransactionalServiceImpl(GovPolicyGeneralMapper govMapper, GovPolicyExplainMapper explainMapper, InvalidInformationMapper invalidInformation, GovPolicyInformationMapper govPolicyInformationMapper, InvalidExplainMapper invalidExplain, InvalidDeclareMapper invalidDeclareMapper, GovPolicyDeclareMapper govPolicyDeclareMapper) {
		this.govMapper = govMapper;
		this.explainMapper = explainMapper;
		this.invalidInformation = invalidInformation;
		this.govPolicyInformationMapper = govPolicyInformationMapper;
		this.invalidExplain = invalidExplain;
		this.invalidDeclareMapper = invalidDeclareMapper;
		this.govPolicyDeclareMapper = govPolicyDeclareMapper;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void bathCutSurface(List<GovPolicyGeneral> list) {
		List<Long> ids = new ArrayList<>();
		for (GovPolicyGeneral policyGeneral : list) {
			ids.add(policyGeneral.getScrapyId());
		}
		govMapper.insertBatch(list);
		invalidPolicyMapper.updateByIdsAndIsDeleted(2L, ids);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void bathCutSurfaceExplain(List<GovPolicyGeneral> list) {
		List<Long> ids = new ArrayList<>();
		for (GovPolicyGeneral policyGeneral : list) {
			ids.add(policyGeneral.getScrapyId());
		}
		explainMapper.insertBatch(list);
		invalidExplain.updateByIdsAndIsDeleted(7L, ids);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void bathCutSurfaceInformation(List<GovPolicyGeneral> list) {
		List<Long> ids = new ArrayList<>();
		for (GovPolicyGeneral policyGeneral : list) {
			ids.add(policyGeneral.getScrapyId());
		}
		govPolicyInformationMapper.insertBatch(list);
		invalidInformation.updateByIdsAndIsDeleted(7L, ids);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void bathCutSurfaceDeclare(List<GovPolicyGeneral> list) {
		List<Long> ids = new ArrayList<>();
		for (GovPolicyGeneral policyGeneral : list) {
			ids.add(policyGeneral.getScrapyId());
		}
		govPolicyDeclareMapper.insertBatch(list);
		invalidDeclareMapper.updateByIdsAndIsDeleted(7L, ids);
	}
}
