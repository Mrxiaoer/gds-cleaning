package com.cloud.gds.cleaning.service;

import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.config.InnovateDataSource;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import scala.Char;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * 迁移
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-02-15
 */
@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TransferTest {

	@Autowired
	private InnovateDataSource innovateDataSource;

	@Test
	public void gainTestMysql() {
		Connection conn = null;
		Statement stmt = null;
		List<GeneralEntity> list = new ArrayList<>();
		try {

			// 打开链接
			conn = innovateDataSource.getConnection();

			// 执行查询
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM scrapy_gov_policy_general WHERE s_general_id < 571346";
			ResultSet rs = stmt.executeQuery(sql);

			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				int id = rs.getInt("s_general_id");
				String title = rs.getString("s_general_title");
				String summary = rs.getString("s_general_abstract");
				String source = rs.getString("s_general_source");
				String reference = rs.getString("s_general_reference");
				String issue = rs.getString("s_general_issue");
				String style = rs.getString("s_general_style");
				String level = rs.getString("s_general_level");
				String timeliness = rs.getString("s_general_timeliness");
				String stage = rs.getString("s_general_stage");
				String formality = rs.getString("s_general_formality");
				String effective = rs.getString("s_general_effective");
				Time write_time = rs.getTime("s_general_write_time");
				Time publish_time = rs.getTime("s_general_publish_time");
				Time effect_time = rs.getTime("s_general_effect_time");
				Time invalid_time = rs.getTime("s_general_invalid_time");
				String text = rs.getString("s_general_text");
				String tag = rs.getString("s_general_tag");
				String target = rs.getString("s_general_target");
				String theme = rs.getString("s_general_theme");
				String fund = rs.getString("s_general_fund");
				String industry = rs.getString("s_general_industry");
				String attachment = rs.getString("s_general_attachment");
				String file = rs.getString("s_general_file");
				String url = rs.getString("s_general_url");
				Integer is_deleted = rs.getInt("del_flag");
				Long creator_id = rs.getLong("s_creator_id");
//				String region = rs.getString("s_general_region");
				System.out.println(id);
				System.out.println(title);
				GeneralEntity general = new GeneralEntity();
				general.setTitle(title);
				general.setSummary(summary);
				general.setSource(source);
				general.setReference(reference);
				general.setIssue(issue);
				general.setStyle(style);
				general.setLevel(level);
				general.setTimeliness(timeliness);
				general.setStage(stage);
				general.setFormality(formality);
				general.setEffective(effective);
				general.setWrite_time(write_time);
				general.setPublish_time(publish_time);
				general.setEffective(effective);
				general.setInvalid_time(invalid_time);
				general.setText(text);
				general.setTag(tag);
				general.setTarget(target);
				general.setTheme(theme);
				general.setFund(fund);
				general.setIndustry(industry);
				general.setAttachment(attachment);
				general.setFile(file);
				general.setUrl(url);
				general.setIs_deleted(is_deleted);
				general.setCreator_id(creator_id);

			}
			// 完成后关闭
			// rs.close();
			// stmt.close();
			innovateDataSource.releaseConnection(conn);
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		}
		System.out.println("OVER!");
	}

	@Data
	public class GeneralEntity {
		private Long id;
		private String title;
		private String summary;
		private String source;
		private String reference;
		private String issue;
		private String style;
		private String level;
		private String timeliness;
		private String stage;
		private String formality;
		private String effective;
		private Time write_time;
		private Time publish_time;
		private Time effect_time;
		private Time invalid_time;
		private String text;
		private String tag;
		private String target;
		private String theme;
		private String fund;
		private String industry;
		private String attachment;
		private String file;
		private String url;
		private Integer is_deleted;
		private Long creator_id;
		//		private Long examine_status;
//		private Long examine_user_id;
//		private Time examine_date;
//		private Long retreat_count;
//		private Long retreat_user;
		private String region;
//		private Long processor_id;
//		private String retreat_content;

	}

}
