package com.cloud.gds.cleaning.service;

import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.config.InnovateDataSource;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
				System.out.println(id);
				System.out.println(title);

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
	public class GeneralEntity{
		private Long id;

	}

}
