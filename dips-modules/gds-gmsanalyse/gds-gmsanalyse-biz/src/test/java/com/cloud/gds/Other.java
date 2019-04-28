package com.cloud.gds;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cloud.gds.gms.api.entity.GovPolicyGeneral;
import com.cloud.gds.gms.api.fegin.RemoteGovPolicyGeneralService;
import com.cloud.gds.gmsanalyse.GdsGmsAnalyseApplication;
import com.cloud.gds.gmsanalyse.config.MyDataSource;
import com.cloud.gds.gmsanalyse.dto.PolicyDeconstructionDto;
import com.cloud.gds.gmsanalyse.entity.PolicyDeconstruction;
import com.cloud.gds.gmsanalyse.mapper.PolicyDeconstructionMapper;
import com.cloud.gds.gmsanalyse.service.PolicyDeconstructionService;
import com.cloud.gds.gmsanalyse.service.impl.AnalyseDeconstruction;
import com.cloud.gds.gmsanalyse.utils.SerializeUtils;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-15
 */
@SpringBootTest(classes = GdsGmsAnalyseApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class Other {

	@Autowired
	private MyDataSource myDataSource;
	@Autowired
	private AnalyseDeconstruction analyseDeconstruction;

	@Autowired
	private PolicyDeconstructionMapper deconstructionMapper;

	@Autowired
	private RemoteGovPolicyGeneralService remoteGovPolicyGeneralService;

	@Autowired
	private PolicyDeconstructionService policyDeconstructionService;

	/**
	 * gain title and id list
	 *
	 * @param sql
	 * @return
	 */
	public Policy gainTitleList(String sql) {
		Connection conn = null;
		Statement stmt = null;
		Policy policy = new Policy();
		try {
			// 打开链接
			conn = myDataSource.getConnection();

			// 执行查询
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				String text = rs.getString("text");
				policy.setText(text);
				String title = rs.getString("title");
				policy.setTitle(title);
				Long id = rs.getLong("id");
				policy.setId(id);

			}
			// 完成后关闭
			myDataSource.releaseConnection(conn);
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		}
		return policy;
	}

	@Test
	public void test() {
		String sql = "SELECT * FROM gov_policy_general WHERE id = 238778";
		Policy policy = gainTitleList(sql);
//		System.out.println(text);
		byte[] bytes = new byte[0];
		ArrayList<String> list = analyseDeconstruction.paragraph_analyse(policy.getText());
		try {
			bytes = SerializeUtils.serializeObject(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PolicyDeconstruction deconstruction = new PolicyDeconstruction();
		deconstruction.setVerbs(bytes);
		deconstruction.setPolicyId(policy.getId());
		deconstruction.setPolicyTitle(policy.getTitle());
		deconstructionMapper.insert(deconstruction);
	}

	@Test
	public void gainPolicInfo() {
		GovPolicyGeneral policy = remoteGovPolicyGeneralService.info(238768L);
		byte[] bytes = new byte[0];
		ArrayList<String> list = analyseDeconstruction.paragraph_analyse(policy.getText());
		try {
			bytes = SerializeUtils.serializeObject(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PolicyDeconstruction deconstruction = new PolicyDeconstruction();
		deconstruction.setVerbs(bytes);
		deconstruction.setPolicyId(Long.valueOf(policy.getId()));
		deconstruction.setPolicyTitle(policy.getTitle());
		deconstructionMapper.insert(deconstruction);

	}

	@Test
	public void des() throws IOException, ClassNotFoundException {

		List<Long> ids = new ArrayList<>();
		ids.add(238719L);
		ids.add(238768L);
		ids.add(238760L);
		ids.add(238720L);
		List<PolicyDeconstruction> deconstructions = deconstructionMapper.selectList(new EntityWrapper<>());
//		PolicyDeconstruction deconstruction = deconstructionMapper.selectByPolicyId(238719L);
		List<PolicyDeconstructionDto> deconstructionDtos = new ArrayList<>();
		for (PolicyDeconstruction deconstruction : deconstructions) {

			PolicyDeconstructionDto deconstructionDto = new PolicyDeconstructionDto();
			BeanUtils.copyProperties(deconstruction, deconstructionDto);
			deconstructionDto.setVerbsList((List<String>) SerializeUtils.deserializeObject(deconstruction.getVerbs()));
			deconstructionDtos.add(deconstructionDto);
		}
//		System.out.println(deconstructionDtos);

		List<String> one = new ArrayList<>();
		List<Long> two = new ArrayList<>();
		Map<Long, String> titleMap = new HashMap<>();
		for (PolicyDeconstructionDto dto : deconstructionDtos) {
			titleMap.put(dto.getPolicyId(), dto.getPolicyTitle());
			for (String string : dto.getVerbsList()) {
				two.add(dto.getPolicyId());
				String substring = string.substring(1, string.length() - 1);
				String s = substring.split(",")[0] + substring.split(",")[1];
				one.add(s);
			}
		}
//		System.out.println(one.size());
//		System.out.println(two.size());
//		System.out.println(titleMap);

		Map<Object, Integer> map = new TreeMap<Object, Integer>();
		Set uniqueSet = new HashSet(one);
		for (Object temp : uniqueSet) {
//			System.out.println(temp + ": " + Collections.frequency(one, temp));
			map.put(temp, Collections.frequency(one, temp));
		}
//		System.out.println(map);

		// 排序 倒序
		ArrayList<Map.Entry<Object, Integer>> list1 = new ArrayList<>(map.entrySet());

		Collections.sort(list1, new Comparator<Map.Entry<Object, Integer>>() {
			public int compare(Map.Entry<Object, Integer> o1, Map.Entry<Object, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
//		System.out.println(list1);
		Integer featureNum = 20;
		List<Map.Entry<Object, Integer>> subList = list1.subList(0, featureNum);

		Map<String, String> stringMap = new HashMap<>();
		for (Map.Entry<Object, Integer> entry : subList) {
			String s = String.valueOf(entry.getKey());
			List<Long> oneList = new ArrayList<>();
			for (int i = 0; i < one.size(); i++) {
				if (one.get(i).equals(s)) {
					System.out.println(i);
					oneList.add(two.get(i));
				}
			}

			// todo 统计个数以及次数
			Map<Object, Integer> oneListMap = new TreeMap<Object, Integer>();
			Set oneListSet = new HashSet(oneList);
			for (Object temp : oneListSet) {
				oneListMap.put(temp, Collections.frequency(oneList, temp));
			}
			// 排序 倒序
			ArrayList<Map.Entry<Object, Integer>> tempList = new ArrayList<>(oneListMap.entrySet());
			Collections.sort(tempList, new Comparator<Map.Entry<Object, Integer>>() {
				@Override
				public int compare(Map.Entry<Object, Integer> o1, Map.Entry<Object, Integer> o2) {
					return o2.getValue().compareTo(o1.getValue());
				}
			});

			String s1 = "提到 " + s + " 行为的政策有";
			// todo 只取前5个政策
			List<Map.Entry<Object, Integer>> tempListBeing;
			if (tempList.size() >= 5) {
				tempListBeing = tempList.subList(0, 5);
			} else {
				tempListBeing = tempList.subList(0, tempList.size());
			}
			for (Map.Entry<Object, Integer> temp : tempListBeing) {
				System.out.println(temp.getKey());
				s1 += "《" + titleMap.get(temp.getKey()) + "》";
			}
			s1 += "等" + tempList.size() + "篇政策,其中《" + titleMap.get(tempList.get(0).getKey()) + "》" + "出现的次数最多，共出现" + tempList.get(0).getValue() + "次";
			System.out.println(s1);

		}

	}

	public List<String> splitList(List<String> list) {
		List<String> newList = new ArrayList<>();
		for (String string : list) {
			String substring = string.substring(1, string.length() - 1);
			String s = substring.split(",")[1];
			newList.add(s);
		}
		return newList;
	}

	@Test
	public void serializeTest() {
		List<String> strings = new ArrayList<>();
		strings.add("2019年农产品质量安全工作");
		strings.add("了《2019年上海市农产品质量安全工作要点》");
		strings.add("乡村振兴战略, 四个严要求, 绿色优质农产品供给");
		System.out.println(strings.toString());

	}

	@Test
	public void gainSetDiff() {
		List<Long> ids = new ArrayList<>();
		ids.add(1L);
		ids.add(238720L);
		ids.add(238760L);
		policyDeconstructionService.deconstructionNonExistent(ids);
	}

	@Data
	public class Policy {
		private String title;
		private String text;
		private Long id;
	}

}
