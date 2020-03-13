package com.cloud.gds.cleaning.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableId;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.config.MyDataSource;
import com.cloud.gds.cleaning.config.OneOfficeDataSource;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 一网通办数据迁移
 *
 * @author : yaonuan
 * @email : 806039077@qq.com
 * @date : 2020/2/27
 */
@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class GeneralOfficeTest {


	@Autowired
	private OneOfficeDataSource oneOfficeDataSource;

	public void gouceInsert() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// 打开链接
			conn = oneOfficeDataSource.getConnection();

			// 执行查询
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id,title,is_deleted FROM scrapy_gov_policy_general WHERE is_deleted != 1 ORDER BY is_deleted DESC ";
			ResultSet rs = stmt.executeQuery(sql);

			List<DataFieldValue> list = new ArrayList<>();
			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				int id = rs.getInt("id");
				String name = rs.getString("title");
				int is_deleted = rs.getInt("is_deleted");

				DataFieldValue fieldValue = new DataFieldValue();
				fieldValue.setFieldId(97L);

				fieldValue.setCreateUser(0);
				list.add(fieldValue);

			}
			// 完成后关闭
			// rs.close();
			// stmt.close();
			oneOfficeDataSource.releaseConnection(conn);
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		}
		System.out.println("OVER!");
	}

	@Test
	public void xxInsert(){
		OneOffice office = new OneOffice("123");
		Connection conn = null;
		Statement stmt = null;
		try {
			// 打开链接
			conn = oneOfficeDataSource.getConnection();

			// 执行查询
			stmt = conn.createStatement();
			String sql;
			sql = "insert into repo_policy (title) value (" + office.getTitle() +")";
			ResultSet rs = stmt.executeQuery(sql);

			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				int id = rs.getInt("id");
				String name = rs.getString("title");
				int is_deleted = rs.getInt("is_deleted");

			}
			// 完成后关闭
			// rs.close();
			// stmt.close();
			oneOfficeDataSource.releaseConnection(conn);
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		}
		System.out.println("OVER!");
	}


	@Data
	public class OneOffice{
		@TableId
		private Integer id;
		private String title;
		public OneOffice(String title){
			this.title = title;
		}
	}


}
