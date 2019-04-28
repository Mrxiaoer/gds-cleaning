package com.cloud.gds.gmsanalyse.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cloud.gds.gms.api.fegin.RemoteGovPolicyGeneralService;
import com.cloud.gds.gmsanalyse.bo.DeconstructionListBo;
import com.cloud.gds.gmsanalyse.dto.PolicyDeconstructionDto;
import com.cloud.gds.gmsanalyse.entity.PolicyDeconstruction;
import com.cloud.gds.gmsanalyse.mapper.PolicyDeconstructionMapper;
import com.cloud.gds.gmsanalyse.service.PolicyDeconstructionService;
import com.cloud.gds.gmsanalyse.utils.SerializeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 政策解构
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-19
 */
@Service
public class PolicyDeconstructionServiceImpl extends ServiceImpl<PolicyDeconstructionMapper, PolicyDeconstruction> implements PolicyDeconstructionService {

	private final PolicyDeconstructionMapper deconstructionMapper;

	@Autowired
	public PolicyDeconstructionServiceImpl(PolicyDeconstructionMapper deconstructionMapper) {
		this.deconstructionMapper = deconstructionMapper;
	}

	@Override
	public DeconstructionListBo gainMaterials(List<Long> ids) throws IOException, ClassNotFoundException {
		// 解构库中获取解构信息
		List<PolicyDeconstruction> deconstructions = deconstructionMapper.selectByPolicyIds(ids);
		List<PolicyDeconstructionDto> deconstructionDtos = new ArrayList<>();
		// 序列化信息反序列化
		for (PolicyDeconstruction deconstruction : deconstructions) {

			PolicyDeconstructionDto deconstructionDto = new PolicyDeconstructionDto();
			BeanUtils.copyProperties(deconstruction, deconstructionDto);
			deconstructionDto.setVerbsList((List<String>) SerializeUtils.deserializeObject(deconstruction.getVerbs()));
			deconstructionDtos.add(deconstructionDto);
		}
		List<String> one = new ArrayList<>();
		List<Long> two = new ArrayList<>();
		Map<Long, String> map = new HashMap<>();
		// 获取政策动词
		for (PolicyDeconstructionDto dto : deconstructionDtos) {
			map.put(dto.getPolicyId(), dto.getPolicyTitle());
			for (String string : dto.getVerbsList()) {
				two.add(dto.getPolicyId());
				String substring = string.substring(1, string.length() - 1);
				String s = substring.split(",")[0]+substring.split(",")[1];
				one.add(s);
			}
		}
		DeconstructionListBo bo = new DeconstructionListBo();
		// TODO ONE:解构完的动词  TWO：动词对应的政策id  MAP：政策id对应的title
		bo.setOne(one);
		bo.setTwo(two);
		bo.setMap(map);
		return bo;
	}

	@Override
	public List<Long> deconstructionNonExistent(List<Long> ids) {
		//组装插入的list 转变成Set
		Set<Long> one = new HashSet<>();
		one.addAll(ids);
		List<Long> list = deconstructionMapper.selectIdByPolicyIds(ids);
		Set<Long> two = new HashSet<>();
		two.addAll(list);
		one.removeAll(two);
		// 查询未解构的数据 one是未解构政策
		List<Long> result = new ArrayList<>();
		result.addAll(one);
		return result;
	}


}
