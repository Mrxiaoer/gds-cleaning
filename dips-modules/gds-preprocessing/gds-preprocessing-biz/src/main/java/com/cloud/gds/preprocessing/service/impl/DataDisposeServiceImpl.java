package com.cloud.gds.preprocessing.service.impl;

import com.cloud.gds.preprocessing.entity.GovPolicyGeneral;
import com.cloud.gds.preprocessing.entity.ScrapyGovPolicyGeneral;
import com.cloud.gds.preprocessing.mapper.GovPolicyGeneralMapper;
import com.cloud.gds.preprocessing.mapper.ScrapyGovPolicyGeneralMapper;
import com.cloud.gds.preprocessing.service.DataDisposeService;
import com.cloud.gds.preprocessing.utils.ConversionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 国策数据转移实现类
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-21
 */
@Service
public class DataDisposeServiceImpl implements DataDisposeService {

	private final ScrapyGovPolicyGeneralMapper scrapyMapper;

	private final GovPolicyGeneralMapper govMapper;

	@Autowired
	public DataDisposeServiceImpl(ScrapyGovPolicyGeneralMapper scrapyMapper, GovPolicyGeneralMapper govMapper) {
		this.scrapyMapper = scrapyMapper;
		this.govMapper = govMapper;
	}

	@Override
	public boolean dataTransfer(Long examineUserId) {
		// gain scrapy data
		List<ScrapyGovPolicyGeneral> generals = scrapyMapper.gainScrapyPolicy();

		// transfer data from ScrapyGovPolicyGeneral to govPolicyGeneral in addition new formation ids
		List<GovPolicyGeneral> list = new ArrayList<>();
		List<Long> ids = new ArrayList<>();
		for (ScrapyGovPolicyGeneral scrapyGovPolicyGeneral : generals) {
			GovPolicyGeneral general = migrate(scrapyGovPolicyGeneral, examineUserId);
			list.add(general);
			// todo 2019-3-21 17:33:37
			ids.add(scrapyGovPolicyGeneral.getId());
		}
		System.out.println(list.size());
		return false;
	}

	private GovPolicyGeneral migrate(ScrapyGovPolicyGeneral scrapy,Long examineUserId) {
		GovPolicyGeneral policyGeneral = new GovPolicyGeneral();
		// assignment BeanUtils.copyProperties in scrapy.id is no policyGeneral.id
		policyGeneral.setTitle(scrapy.getTitle());
		policyGeneral.setSource(scrapy.getSource());
		policyGeneral.setReference(scrapy.getReference());
		policyGeneral.setIssue(scrapy.getIssue());
		policyGeneral.setStyle(scrapy.getStyle() != null ? ConversionUtils.styleS2I(scrapy.getStyle()) : null);
		policyGeneral.setLevel(scrapy.getLevel() != null ? ConversionUtils.levelSTI(scrapy.getLevel()) : null);
		policyGeneral.setPublishTime(scrapy.getPublishTime());
		policyGeneral.setText(scrapy.getText());
		policyGeneral.setAttachment(scrapy.getAttachment());
		policyGeneral.setUrl(scrapy.getUrl());
		policyGeneral.setRegion(scrapy.getRegion());
		policyGeneral.setCreatorId(scrapy.getCreatorId());
		policyGeneral.setCreateTime(scrapy.getCreateTime());
		policyGeneral.setScrapyId(scrapy.getId());
		policyGeneral.setExamineUserId(examineUserId);
		return policyGeneral;
	}

}
