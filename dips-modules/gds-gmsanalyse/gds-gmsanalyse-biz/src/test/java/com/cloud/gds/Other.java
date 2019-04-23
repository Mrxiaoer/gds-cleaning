package com.cloud.gds;

import com.cloud.gds.gms.api.entity.GovPolicyGeneral;
import com.cloud.gds.gms.api.fegin.RemoteGovPolicyGeneralService;
import com.cloud.gds.gmsanalyse.GdsGmsAnalyseApplication;
import com.cloud.gds.gmsanalyse.config.MyDataSource;
import com.cloud.gds.gmsanalyse.dto.PolicyDeconstructionDto;
import com.cloud.gds.gmsanalyse.entity.PolicyDeconstruction;
import com.cloud.gds.gmsanalyse.mapper.PolicyDeconstructionMapper;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				String title  = rs.getString("title");
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

	@Data
	public class Policy{
		private String title;
		private String text;
		private Long id;
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
	public void gainPolicInfo(){
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
		ids.add(238720L);
		List<PolicyDeconstruction> deconstructions = deconstructionMapper.selectByPolicyIds(ids);
//		PolicyDeconstruction deconstruction = deconstructionMapper.selectByPolicyId(238719L);
		List<PolicyDeconstructionDto> deconstructionDtos = new ArrayList<>();
		for (PolicyDeconstruction deconstruction : deconstructions){

			PolicyDeconstructionDto deconstructionDto = new PolicyDeconstructionDto();
			BeanUtils.copyProperties(deconstruction,deconstructionDto );
			deconstructionDto.setVerbsList((List<String>) SerializeUtils.deserializeObject(deconstruction.getVerbs()));
			deconstructionDtos.add(deconstructionDto);
		}
		System.out.println(deconstructionDtos);

		List<String> one = new ArrayList<>();
		List<Long> two = new ArrayList<>();
		Map<String,String> map = new HashMap<>();
		for (PolicyDeconstructionDto dto : deconstructionDtos){
			map.put(String.valueOf(dto.getPolicyId()),dto.getPolicyTitle() );
			for (String string : dto.getVerbsList()){
				two.add(dto.getPolicyId());
				String substring = string.substring(1, string.length() - 1);
				String s = substring.split(",")[1];
				one.add(s);
			}
		}
		System.out.println(one.size());
		System.out.println(two.size());
		System.out.println(map);

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

}
