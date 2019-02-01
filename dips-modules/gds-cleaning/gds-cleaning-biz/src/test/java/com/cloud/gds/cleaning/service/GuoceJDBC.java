package com.cloud.gds.cleaning.service;

import com.alibaba.fastjson.JSON;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2019-02-01
 */
@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class GuoceJDBC {

	// JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://118.31.60.34:3306/dips_cloud_gov2?useUnicode=true&characterEncoding=UTF-8";

	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "root";
	static final String PASS = "Gov20130528";

	@Autowired
	private DataFieldValueService dataFieldValueService;

	@Test
	public void gouceInsert() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// 注册 JDBC 驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 打开链接
			System.out.println("连接数据库...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// 执行查询
			System.out.println(" 实例化Statement对象...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id,title,is_deleted FROM scrapy_gov_policy_general WHERE is_deleted != 1 order by is_deleted desc ";
			ResultSet rs = stmt.executeQuery(sql);

			List<DataFieldValue> list = new ArrayList<>();
			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				int id = rs.getInt("id");
				String name = rs.getString("title");
				int is_deleted = rs.getInt("is_deleted");

//				// 输出数据
//				System.out.print("ID: " + id);
//				System.out.print(", 站点名称: " + name);
//				System.out.print("\n");
				DataFieldValue fieldValue = new DataFieldValue();
				fieldValue.setFieldId(97L);

				GouceEntity gouceEntity = new GouceEntity();
				gouceEntity.setId(id);
				gouceEntity.setTitle(name);
				gouceEntity.setIs_deleted(is_deleted);
//				System.out.println(rs.getRow());
//				System.out.println(JSON.toJSONString(gouceEntity));
				fieldValue.setFieldValue(JSON.toJSONString(gouceEntity));
				fieldValue.setCreateUser(0);
				list.add(fieldValue);

			}
			dataFieldValueService.batchSave(list, 1000);
			// 完成后关闭
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// 处理 JDBC 错误
			se.printStackTrace();
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException se2) {
				// 什么都不做
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("OVER!");
	}

	@Test
	public void move() {
		Connection conn = null;
		Statement stmt = null;
		String ids = "15528,15530";
		try {
			// 注册 JDBC 驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 打开链接
			System.out.println("连接数据库...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// 执行查询
			System.out.println(" 实例化Statement对象...");
			stmt = conn.createStatement();
			String sql;
			sql = "INSERT INTO gov_policy_general (title,reference,issue,style,`level`,write_time,publish_time,effect_time,text,url,creator_id,scrapy_id,examine_status,examine_user_id,processor_id,examine_date)" +
				"SELECT title,reference,issue,(CASE style WHEN \"通知\" THEN 1 WHEN \"公告\" THEN 2 WHEN \"报告\" THEN 3 WHEN \"意见\" THEN 4 WHEN \"办法\" THEN 5 WHEN \"通报\" THEN 6 WHEN \"其他\" THEN 7 ELSE 0 END)AS style,(CASE level WHEN \"国家级\" THEN 1 WHEN \"省级\" THEN 2 WHEN \"市级\" THEN 3 WHEN \"区级（县级）\" THEN 4 ELSE 0 END)AS `level`,write_time,publish_time,effect_time,text,url,creator_id,id AS scrapy_id,3 as examine_status,2112 AS examine_user_id,2112 AS processor_id,CURRENT_TIME() AS examine_date FROM scrapy_gov_policy_general where id in ";

			String text;
			text = "SELECT title,reference,issue,(CASE style WHEN \"通知\" THEN 1 WHEN \"公告\" THEN 2 WHEN \"报告\" THEN 3 WHEN \"意见\" THEN 4 WHEN \"办法\" THEN 5 WHEN \"通报\" THEN 6 WHEN \"其他\" THEN 7 ELSE 0 END)AS style,(CASE level WHEN \"国家级\" THEN 1 WHEN \"省级\" THEN 2 WHEN \"市级\" THEN 3 WHEN \"区级（县级）\" THEN 4 ELSE 0 END)AS `level`,write_time,publish_time,effect_time,text,url,creator_id,id AS scrapy_id,3 as examine_status,2112 AS examine_user_id,2112 AS processor_id,CURRENT_TIME() AS examine_date FROM scrapy_gov_policy_general where id in ";

			String sqlcount;
			sqlcount = sql + "(" + ids + ")";
			System.out.println(sqlcount);

//			ResultSet rs = stmt.executeQuery(sqlcount);
			stmt.execute(sqlcount);
//			while (rs.next()) {
//				int id = rs.getInt("style");
//				String name = rs.getString("level");
//
//				System.out.println(id);
//				System.out.println(name);
//
//			}

			// 完成后关闭
//			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// 处理 JDBC 错误
			se.printStackTrace();
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException se2) {
				// 什么都不做
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("OVER!");
	}

//	INSERT INTO gov_policy_general (title,reference,issue,style,`level`,write_time,publish_time,effect_time,text,url,creator_id,scrapy_id,examine_status,examine_user_id,processor_id,examine_date
//									)
//	SELECT title,reference,issue,style,`level`,write_time,publish_time,effect_time,text,url,creator_id,id AS scrapy_id,3 as examine_status,2112 AS examine_user_id,2112 AS processor_id,CURRENT_TIME() AS examine_date
//	FROM scrapy_gov_policy_general where id in (15528,15530)

	@Data
	private static class GouceEntity {

		private Integer id;

		private String title;

		private Integer is_deleted;


	}

}
