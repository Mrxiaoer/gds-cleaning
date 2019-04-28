package com.cloud.gds.gmsanalyse.service.impl;

import com.cloud.gds.gms.api.entity.GovPolicyGeneral;
import com.cloud.gds.gms.api.fegin.RemoteGovPolicyGeneralService;
import com.cloud.gds.gmsanalyse.bo.DeconstructionListBo;
import com.cloud.gds.gmsanalyse.constant.AnalyseConstant;
import com.cloud.gds.gmsanalyse.dto.GovPolicyDto;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyse;
import com.cloud.gds.gmsanalyse.entity.PolicyAnalyseFeature;
import com.cloud.gds.gmsanalyse.entity.PolicyDeconstruction;
import com.cloud.gds.gmsanalyse.service.*;
import com.cloud.gds.gmsanalyse.utils.SerializeUtils;
import com.cloud.gds.gmsanalyse.vo.Analyse;
import com.cloud.gds.gmsanalyse.vo.Child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-04
 */
@Service
public class GovAnalyseServiceImpl implements GovAnalyseService {

	private final PolicyAnalyseService policyAnalyseService;
	private final PolicyAnalyseFeatureService policyAnalyseFeatureService;
	private final PolicyDeconstructionService policyDeconstructionService;
	private final AnalyseDeconstruction analyseDeconstruction;
	private final AnalyseAlgorithmService analyseAlgorithmService;
	private final RemoteGovPolicyGeneralService remoteGovPolicyGeneralService;

	@Autowired
	public GovAnalyseServiceImpl(PolicyAnalyseService policyAnalyseService, PolicyAnalyseFeatureService policyAnalyseFeatureService, RemoteGovPolicyGeneralService remoteGovPolicyGeneralService, PolicyDeconstructionService policyDeconstructionService, AnalyseDeconstruction analyseDeconstruction, AnalyseAlgorithmService analyseAlgorithmService) {
		this.policyAnalyseService = policyAnalyseService;
		this.policyAnalyseFeatureService = policyAnalyseFeatureService;
		this.remoteGovPolicyGeneralService = remoteGovPolicyGeneralService;
		this.policyDeconstructionService = policyDeconstructionService;
		this.analyseDeconstruction = analyseDeconstruction;
		this.analyseAlgorithmService = analyseAlgorithmService;
	}

	@Override
	public void govAnalyse(GovPolicyDto govPolicyDto) throws IOException, ClassNotFoundException {
		// 保存分析信息
		PolicyAnalyse policyAnalyse = policyAnalyseService.save(govPolicyDto);
		// 调用gms模块获取查询条件的政策数据
		List<Long> ids = remoteGovPolicyGeneralService.gainList(govPolicyDto.getParams());
		// 查询未解构的数据
		List<Long> longList = policyDeconstructionService.deconstructionNonExistent(ids);
		// 查询需解构的数据
		if (longList.size() > 0) {
			List<GovPolicyGeneral> generals = remoteGovPolicyGeneralService.selectByIds(longList);
			//	这里方法需要修改 TODO 2019-4-25 9:19:16
			for (GovPolicyGeneral general : generals) {
				byte[] bytes = new byte[0];
				ArrayList<String> list = analyseDeconstruction.paragraph_analyse(general.getText());
				try {
					bytes = SerializeUtils.serializeObject(list);
				} catch (IOException e) {
					e.printStackTrace();
				}
				PolicyDeconstruction deconstruction = new PolicyDeconstruction();
				deconstruction.setVerbs(bytes);
				deconstruction.setPolicyId(Long.valueOf(general.getId()));
				deconstruction.setPolicyTitle(general.getTitle());
				policyDeconstructionService.insert(deconstruction);
			}
		}
		// 获取需分析的东西 2个数组+1个map
		DeconstructionListBo bo = policyDeconstructionService.gainMaterials(ids);
		// 政策分析
		Analyse wonk = analyseAlgorithmService.policyWonk(bo, govPolicyDto.getFeatureNum());
//		System.out.println(wonk);

		// 分析状态进行处理
		PolicyAnalyse analyse = new PolicyAnalyse();
		analyse.setId(policyAnalyse.getId());
		if (wonk != null) {
			// 分析成功
			boolean b = updatePolicyAndFeature(policyAnalyse.getId(), wonk, ids.size());
			if (b) {
				analyse.setAnalyseState(AnalyseConstant.TRUE);
			} else {
				analyse.setAnalyseState(AnalyseConstant.TWO);
			}
		} else {
			// 分析失败
			analyse.setAnalyseState(AnalyseConstant.TWO);
		}
		// 更新分析状态
		policyAnalyseService.updateById(analyse);
	}

	/**
	 * 更新特征表与政策总结词
	 * @param analyseId 分析池中id
	 * @param wonk      分析政策的结果
	 * @param total     分析政策数
	 * @return
	 */
	private boolean updatePolicyAndFeature(Long analyseId, Analyse wonk, Integer total) {
		// 先更特征表
		List<PolicyAnalyseFeature> list = spellFeatureData(analyseId, wonk);
		// 再更新分析表
		PolicyAnalyse updatePolicy = new PolicyAnalyse();
		updatePolicy.setId(analyseId);
		updatePolicy.setAnalyseSummary(wonk.getSummary());
		updatePolicy.setPolicyNum(total);
		return policyAnalyseFeatureService.batchInsert(list) && policyAnalyseService.updateById(updatePolicy);
	}

	/**
	 * 拼装特征实体集
	 *
	 * @param analyseId 分析id
	 * @param wonk      分析结果
	 * @return
	 */
	private List<PolicyAnalyseFeature> spellFeatureData(Long analyseId, Analyse wonk) {
		List<PolicyAnalyseFeature> list = new ArrayList<>();
		for (Child child : wonk.getList()) {
			PolicyAnalyseFeature feature = new PolicyAnalyseFeature();
			feature.setFeatureNum(child.getFeatureNum());
			feature.setFeatureName(child.getFeatureName());
			feature.setAnalyseId(analyseId);
			list.add(feature);
		}
		return list;
	}
}
