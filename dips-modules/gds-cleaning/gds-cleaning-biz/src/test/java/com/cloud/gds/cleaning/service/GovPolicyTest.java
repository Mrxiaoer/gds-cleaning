package com.cloud.gds.cleaning.service;

import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.config.MyDataSource;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 国策特殊清洗处理
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-15
 */
@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class GovPolicyTest {


	@Autowired
	private MyDataSource myDataSource;

	/**
	 * gain title and id list
	 *
	 * @param sql
	 * @return
	 */
	public List<TitleAndId> gainTitleList(String sql) {
		Connection conn = null;
		Statement stmt = null;
		List<TitleAndId> list = new ArrayList<>();
		try {
			// 打开链接
			conn = myDataSource.getConnection();

			// 执行查询
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				Long id = rs.getLong("id");
				String title = rs.getString("title");
				TitleAndId writeTitle = new TitleAndId();
				writeTitle.setId(id);
				writeTitle.setTitle(title);
				list.add(writeTitle);
			}
			// 完成后关闭
			myDataSource.releaseConnection(conn);
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		}
		System.out.println("gain titltAndId list OVER!" + list.size());
		return list;
	}

	/**
	 * gain title set
	 *
	 * @return
	 */
	public Set<String> gainTitleSet(String sql) {
		Connection conn = null;
		Statement stmt = null;
		Set<String> set = new HashSet<>();
		try {
			// 打开链接
			conn = myDataSource.getConnection();

			// 执行查询
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				String title = rs.getString("title");
				set.add(title);
			}
			// 完成后关闭
			myDataSource.releaseConnection(conn);
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		}
		System.out.println("获取titltAndId set OVER!");
		return set;
	}

	/**
	 * 新爬取的与正式表查重,清除正式表中数据获取ids
	 */
	public List<Long> gainRealOnNewSpiderDataIds() {
		//14w new spider data
		String policySql = "SELECT id,title,is_deleted FROM scrapy_gov_policy_general WHERE is_deleted = 0";
		Set<String> set = gainTitleSet(policySql);
		System.out.println("爬取的有效信息个数：" + set.size());
		//18w real spider data
		String sql = "SELECT id,title,is_deleted FROM gov_policy_general WHERE is_deleted = 0";
		List<TitleAndId> oldTitle = gainTitleList(sql);

		String realSql = "SELECT id,title,is_deleted FROM gov_policy_general WHERE is_deleted = 0";
		Set<String> set1 = gainTitleSet(realSql);
		System.out.println("真实表有效信息个数：" + set1.size());
		// gain the same title spider data title
		set1.retainAll(set);
		System.out.println("重复个数：" + set1.size());
		// 需去重的id数组
		List<Long> list = new ArrayList<>();

		for (TitleAndId titleAndId : oldTitle) {
			if (set1.contains(titleAndId.getTitle())) {
				list.add(titleAndId.getId());
			}
		}
		System.out.println("查询到ids的个数:" + list.size());
		return list;
	}

	/**
	 * 去除真实表中与新爬取的数据,删除真实表中与新爬取的数据有重复部分(逻辑删除)
	 */
	@Test
	public void updateRealInNewSpider() {
		Connection conn = null;
		Statement stmt = null;
		List<Long> list = gainRealOnNewSpiderDataIds();
		String idStr = list.toString();
		String ids = idStr.substring(1, idStr.length() - 1);
		try {
			// 打开链接
			System.out.println("连接数据库...");
			conn = myDataSource.getConnection();

			// 执行查询
			System.out.println(" 实例化Statement对象...");
			stmt = conn.createStatement();
			String sql;
			sql = "UPDATE gov_policy_general SET is_deleted = 4 WHERE id IN";

			String sqlcount;
			sqlcount = sql + "(" + ids + ")";
			System.out.println(sqlcount);

			stmt.execute(sqlcount);
			// stmt.close();
			myDataSource.releaseConnection(conn);
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
		System.out.println("policy去重成功 OVER!");
	}

	/**
	 * 通用update is_deleted
	 */
	public void updateIsDeleted(String sql,List<Long> list) {
		Connection conn = null;
		Statement stmt = null;
		String idStr = list.toString();
		String ids = idStr.substring(1, idStr.length() - 1);
		try {
			// 打开链接
			System.out.println("连接数据库...");
			conn = myDataSource.getConnection();

			// 执行查询
			System.out.println(" 实例化Statement对象...");
			stmt = conn.createStatement();

			String sqlcount;
			sqlcount = sql + "(" + ids + ")";
			System.out.println(sqlcount);

			stmt.execute(sqlcount);
			// stmt.close();
			myDataSource.releaseConnection(conn);
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
		System.out.println("update is_delete OVER!");
	}


	/**
	 * 爬虫预处理去除查询ids
	 */
	public List<Long> identicalPolicy(String sql) {
		List<TitleAndId> list = gainTitleList(sql);
		Set<Object> set = new HashSet<>();
		long l = System.currentTimeMillis();
		List<Long> ids = new ArrayList<>();
		for (TitleAndId titleAndId : list) {
			if (set.contains(titleAndId.getTitle())) {
				ids.add(titleAndId.getId());
			} else {
				set.add(titleAndId.getTitle());
			}
		}
		long t = System.currentTimeMillis();
		System.out.println(t - l);
		System.out.println(ids.size());
		System.out.println(ids);
		return ids;
	}

	/**
	 * 去除爬虫中重复的数据,修改其is_deleted = 4
	 */
	@Test
	public void updatePolicy() {
		Connection conn = null;
		Statement stmt = null;
		String sqlT = "SELECT id,title FROM scrapy_gov_policy_general WHERE title IN(\n" +
			"SELECT a.title FROM (\n" +
			"SELECT DISTINCT\n" +
			"\ttitle,\n" +
			"\tCOUNT(title) AS titlenum\n" +
			"FROM\n" +
			"\tscrapy_gov_policy_general\n" +
			"WHERE\n" +
			" is_deleted = 0\n" +
			"GROUP BY\n" +
			" title)a WHERE a.titlenum > 1)";
		List<Long> list = identicalPolicy(sqlT);
		String idStr = list.toString();
		String ids = idStr.substring(1, idStr.length() - 1);
		try {
			// 打开链接
			System.out.println("连接数据库...");
			conn = myDataSource.getConnection();

			// 执行查询
			System.out.println(" 实例化Statement对象...");
			stmt = conn.createStatement();
			String sql;
			sql = "UPDATE scrapy_gov_policy_general SET is_deleted = 4 WHERE id IN";

			String sqlcount;
			sqlcount = sql + "(" + ids + ")";
			System.out.println(sqlcount);

			stmt.execute(sqlcount);
			// stmt.close();
			myDataSource.releaseConnection(conn);
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
		System.out.println("policy去重成功 OVER!");
	}

	@Test
	public void select2158() {
		String sql2158 = "SELECT id,title,is_deleted FROM gov_policy_general WHERE is_deleted = 4 AND examine_user_id <> 2158";
		Set<String> set = gainTitleSet(sql2158);

		String sql = "SELECT id,title,is_deleted FROM scrapy_gov_policy_general WHERE is_deleted = 0";
		List<TitleAndId> list = gainTitleList(sql);

		List<Long> ids = new ArrayList<>();
		for (TitleAndId titleAndId : list) {
			if (set.contains(titleAndId.getTitle())) {
				ids.add(titleAndId.getId());
			}
		}
		String updateSql = "UPDATE scrapy_gov_policy_general SET is_deleted = 4 WHERE id IN";
		updateIsDeleted(updateSql, ids);

	}

	@Data
	private class TitleAndId {

		private Long id;
		private String title;
		private Long is_deleted;

	}


}
