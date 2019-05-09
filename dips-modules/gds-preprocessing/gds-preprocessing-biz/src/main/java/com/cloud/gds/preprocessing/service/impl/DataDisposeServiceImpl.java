package com.cloud.gds.preprocessing.service.impl;

import com.cloud.gds.preprocessing.entity.GovPolicyGeneral;
import com.cloud.gds.preprocessing.entity.ScrapyGovPolicyGeneral;
import com.cloud.gds.preprocessing.mapper.*;
import com.cloud.gds.preprocessing.service.DataDisposeService;
import com.cloud.gds.preprocessing.service.InvalidInformationService;
import com.cloud.gds.preprocessing.service.TransactionalService;
import com.cloud.gds.preprocessing.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DataDisposeServiceImpl implements DataDisposeService  {

	@Autowired
	private InvalidPolicyMapper invalidPolicyMapper;
	private final InvalidExplainMapper invalidExplainMapper;
	private final InvalidDeclareMapper invalidDeclareMapper;
	private final InvalidInformationMapper invalidInformation;
	private final GovPolicyGeneralMapper govMapper;
	private final TransactionalService transactionalService;

	@Autowired
	public DataDisposeServiceImpl(GovPolicyGeneralMapper govMapper, TransactionalService transactionalService, InvalidInformationMapper invalidInformation, InvalidDeclareMapper invalidDeclareMapper, InvalidExplainMapper invalidExplainMapper) {
		this.govMapper = govMapper;
		this.transactionalService = transactionalService;
		this.invalidInformation = invalidInformation;
		this.invalidDeclareMapper = invalidDeclareMapper;
		this.invalidExplainMapper = invalidExplainMapper;
	}

	@Override
	public boolean dataMigrationSurface(Long examineUserId) {
		// gain scrapy data is is_deleted = 0
		List<ScrapyGovPolicyGeneral> generals = invalidPolicyMapper.ScrapyPolicyGeneral();

		// transfer data from ScrapyGovPolicyGeneral to govPolicyGeneral in addition new formation ids
		List<GovPolicyGeneral> list = new ArrayList<>();
		for (ScrapyGovPolicyGeneral scrapyGovPolicyGeneral : generals) {
			GovPolicyGeneral general = migrateDataSplicing(scrapyGovPolicyGeneral, examineUserId);
			list.add(general);
		}
//		System.out.println(list.size());
		// implementing multi-table atomicity with cut-in data
		List<List<GovPolicyGeneral>> data = cutBatchData(list, 200);

		for (List<GovPolicyGeneral> list1 : data) {
			transactionalService.bathCutSurface(list1);
		}
		return true;
	}

	@Override
	public boolean dataMigrationSurfaceExplain(Long examineUserId) {
		// gain explain scrapy data is is_deleted = 0
		List<ScrapyGovPolicyGeneral> generals = invalidExplainMapper.gainExplainScrapyPolicy();

		// transfer data from ScrapyGovPolicyGeneral to govPolicyGeneral in addition new formation ids
		List<GovPolicyGeneral> list = new ArrayList<>();
		for (ScrapyGovPolicyGeneral scrapyGovPolicyGeneral : generals) {
			GovPolicyGeneral general = migrateDataSplicing(scrapyGovPolicyGeneral, examineUserId);
			list.add(general);
		}
//		System.out.println(list.size());
		// implementing multi-table atomicity with cut-in data
		List<List<GovPolicyGeneral>> data = cutBatchData(list, 200);

		for (List<GovPolicyGeneral> list1 : data) {
			transactionalService.bathCutSurfaceExplain(list1);
		}
		return true;
	}

	@Override
	public boolean dataMigrationSurfaceDeclare(Long examineUserId) {
		// gain explain scrapy data is is_deleted = 0
		List<ScrapyGovPolicyGeneral> generals = invalidDeclareMapper.invalidInformationPolicy();

		// transfer data from ScrapyGovPolicyGeneral to govPolicyGeneral in addition new formation ids
		List<GovPolicyGeneral> list = new ArrayList<>();
		for (ScrapyGovPolicyGeneral scrapyGovPolicyGeneral : generals) {
			GovPolicyGeneral general = migrateDataSplicing(scrapyGovPolicyGeneral, examineUserId);
			list.add(general);
		}
		// implementing multi-table atomicity with cut-in data
		List<List<GovPolicyGeneral>> data = cutBatchData(list, 200);

		for (List<GovPolicyGeneral> list1 : data) {
			transactionalService.bathCutSurfaceDeclare(list1);
		}
		return true;
	}

	@Override
	public boolean dataMigrationSurfaceInformation(Long examineUserId) {
		// gain information scrapy data is is_deleted = 0
		List<ScrapyGovPolicyGeneral> generals = invalidInformation.invalidInformationPolicy();

		// transfer data from ScrapyGovPolicyGeneral to govPolicyGeneral in addition new formation ids
		List<GovPolicyGeneral> list = new ArrayList<>();
		for (ScrapyGovPolicyGeneral scrapyGovPolicyGeneral : generals) {
			GovPolicyGeneral general = migrateInformationSplicing(scrapyGovPolicyGeneral, examineUserId);
			list.add(general);
		}
//		System.out.println(list.size());
		// implementing multi-table atomicity with cut-in data
		List<List<GovPolicyGeneral>> data = cutBatchData(list, 200);

		for (List<GovPolicyGeneral> list1 : data) {
			transactionalService.bathCutSurfaceInformation(list1);
		}
		return true;
	}

	private GovPolicyGeneral migrateDataSplicing(ScrapyGovPolicyGeneral scrapy, Long examineUserId) {
		GovPolicyGeneral policyGeneral = new GovPolicyGeneral();
		// assignment BeanUtils.copyProperties in scrapy.id is no policyGeneral.id so use this low method
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
		policyGeneral.setExamineStatus(0);
		return policyGeneral;
	}

	private GovPolicyGeneral migrateInformationSplicing(ScrapyGovPolicyGeneral scrapy, Long examineUserId) {
		GovPolicyGeneral policyGeneral = new GovPolicyGeneral();
		// assignment BeanUtils.copyProperties in scrapy.id is no policyGeneral.id so use this low method
		policyGeneral.setTitle(scrapy.getTitle());
		policyGeneral.setSource(scrapy.getSource());
		policyGeneral.setPublishTime(scrapy.getPublishTime());
		policyGeneral.setText(scrapy.getText());
		policyGeneral.setUrl(scrapy.getUrl());
		policyGeneral.setRegion(scrapy.getRegion());
		policyGeneral.setCreatorId(scrapy.getCreatorId());
		policyGeneral.setFile(scrapy.getFile());
		policyGeneral.setCreateTime(scrapy.getCreateTime());
		policyGeneral.setScrapyId(scrapy.getId());
		policyGeneral.setExamineUserId(examineUserId);
		policyGeneral.setExamineStatus(0);
		return policyGeneral;
	}

	private <T> List<List<T>> cutBatchData(List<T> generals, int cutLength) {

		List<List<T>> list = new ArrayList<>();
		boolean flag = true;
		int currentNum = 0;
		while (flag) {
			if (generals.size() > cutLength * (currentNum + 1)) {
				list.add(generals.subList(currentNum * cutLength, (currentNum + 1) * cutLength));
			} else {
				list.add(generals.subList(currentNum * cutLength, generals.size()));
				flag = false;
			}
			currentNum++;
		}
		return list;
	}

}
