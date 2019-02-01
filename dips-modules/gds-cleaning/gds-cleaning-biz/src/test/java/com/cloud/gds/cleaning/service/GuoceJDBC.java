package com.cloud.gds.cleaning.service;

import com.alibaba.fastjson.JSON;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.config.MyDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2019-02-01
 */
@SpringBootTest(classes = GdsCleaningApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class GuoceJDBC {

	// JDBC 驱动名及数据库 URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL =
		"jdbc:mysql://118.31.60.34:3306/dips_cloud_gov2?useUnicode=true" + "&characterEncoding=UTF-8";

	// 数据库的用户名与密码，需要根据自己的设置
	private static final String USER = "root";
	private static final String PASS = "Gov20130528";

	@Autowired
	private DataFieldValueService dataFieldValueService;
	@Autowired
	private ExecutorService analysisThreadPool;
	@Autowired
	private DoAnalysisService doAnalysisService;
	@Autowired
	private MyDataSource myDataSource;

	@Test
	public void guoceClean() {
		List<Long> ids = doAnalysisService.getNoExactlySameDataIds(97L);
		List<List<Long>> idLists = cutIds(ids);

		for (List<Long> idList : idLists) {
			analysisThreadPool.execute(() -> {
				this.move(idList);
			});
		}

		// Connection conn;
		// PreparedStatement stmt;
		// try {
		// 	conn = myDataSource.getConnection();
		// 	String sql = "SELECT id,title,is_deleted FROM scrapy_gov_policy_general WHERE is_deleted != 1 ORDER BY
		// is_deleted"
		// 		+ " DESC ";
		// 	stmt = conn.prepareStatement(sql);
		// 	ResultSet rs = stmt.executeQuery();
		// 	stmt.close();
		// 	myDataSource.releaseConnection(conn);
		// } catch (SQLException e) {
		// 	e.printStackTrace();
		// }

	}

	@Test
	public void gouceInsert() {
		Connection conn = null;
		Statement stmt = null;
		try {

			// 打开链接
			System.out.println("连接数据库...");
			conn = myDataSource.getConnection();

			// 执行查询
			System.out.println(" 实例化Statement对象...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id,title,is_deleted FROM scrapy_gov_policy_general WHERE is_deleted != 1 ORDER BY is_deleted"
				+ " DESC ";
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
			myDataSource.releaseConnection(conn);
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

	public void move(List<Long> list) {
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
			String sql;
			sql = "INSERT INTO gov_policy_general (title,reference,issue,style,`level`,write_time,publish_time,"
				+ "effect_time,text,url,creator_id,scrapy_id,examine_status,examine_user_id,processor_id,"
				+ "examine_date)"
				+ "SELECT title,reference,issue,(CASE style WHEN \"通知\" THEN 1 WHEN \"公告\" THEN 2 WHEN \"报告\" THEN"
				+ " 3 WHEN \"意见\" THEN 4 WHEN \"办法\" THEN 5 WHEN \"通报\" THEN 6 WHEN \"其他\" THEN 7 ELSE 0 END)AS "
				+ "style,(CASE level WHEN \"国家级\" THEN 1 WHEN \"省级\" THEN 2 WHEN \"市级\" THEN 3 WHEN \"区级（县级）\" "
				+ "THEN 4 ELSE 0 END)AS `level`,write_time,publish_time,effect_time,text,url,creator_id,id AS "
				+ "scrapy_id,3 as examine_status,2112 AS examine_user_id,2112 AS processor_id,CURRENT_TIME() AS "
				+ "examine_date FROM scrapy_gov_policy_general where id in ";

			String text;
			text = "SELECT title,reference,issue,(CASE style WHEN \"通知\" THEN 1 WHEN \"公告\" THEN 2 WHEN \"报告\" THEN 3 "
				+ "WHEN \"意见\" THEN 4 WHEN \"办法\" THEN 5 WHEN \"通报\" THEN 6 WHEN \"其他\" THEN 7 ELSE 0 END)AS style,"
				+ "(CASE level WHEN \"国家级\" THEN 1 WHEN \"省级\" THEN 2 WHEN \"市级\" THEN 3 WHEN \"区级（县级）\" THEN 4 ELSE 0"
				+ " END)AS `level`,write_time,publish_time,effect_time,text,url,creator_id,id AS scrapy_id,3 as "
				+ "examine_status,2112 AS examine_user_id,2112 AS processor_id,CURRENT_TIME() AS examine_date FROM "
				+ "scrapy_gov_policy_general where id in ";

			String sqlcount;
			sqlcount = sql + "(" + ids + ")";
			System.out.println(sqlcount);

			stmt.execute(sqlcount);
			stmt.close();
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
		System.out.println("OVER!");
	}

	@Test
	public void test(){

		List<Long> ids = new ArrayList<>();
		ids.add(1813L);
		ids.add(15528L);
		ids.add(15530L);
		ids.add(1814L);
		batchSave(ids, 2);
	}

	public boolean batchSave(List<Long> list, int oneSize) {
		boolean flag = true;
		List<Long> subList;
		int currentNum = 0;
		while (flag) {
			if (list.size() > oneSize * (currentNum + 1)) {
				subList = list.subList(currentNum * oneSize, oneSize * (currentNum + 1));
			} else {
				subList = list.subList(currentNum * oneSize, list.size());
				flag = false;
			}
			move(subList);
			currentNum++;
		}
		return true;
	}

	//	INSERT INTO gov_policy_general (title,reference,issue,style,`level`,write_time,publish_time,effect_time,text,
	// url,creator_id,scrapy_id,examine_status,examine_user_id,processor_id,examine_date
	//									)
	//	SELECT title,reference,issue,style,`level`,write_time,publish_time,effect_time,text,url,creator_id,id AS
	// scrapy_id,3 as examine_status,2112 AS examine_user_id,2112 AS processor_id,CURRENT_TIME() AS examine_date
	//	FROM scrapy_gov_policy_general where id in (15528,15530)

	public void MultiThreadLabel() throws Exception {

		//查询未打标签的ids
		Connection conn = null;
		try {
			conn = myDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//todo jdbc操作 未打标签的ids
		List<Long> ids = selectNoTagId();
		myDataSource.releaseConnection(conn);
		int oneSize = 100;
		AtomicInteger currNum = new AtomicInteger();
		analysisThreadPool.execute(() -> {
			//根据ids分块查询数据
			Connection connect = null;
			try {
				connect = myDataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			List<Long> idList = ids.subList(oneSize * currNum.get(), oneSize * (currNum.get() + 1));
			currNum.getAndIncrement();
			//todo jdbc操作

			//打标签
			//todo
			TagRelation tagRelation = new TagRelation();
			// 固定
			tagRelation.setNode("gov_general_policy");
			tagRelation.setType_id(4L);



			//标签jdbc存储
			//todo jdbc操作
			myDataSource.releaseConnection(connect);
		});
	}

	public List<List<Long>> cutIds(List<Long> ids) {
		int oneSize = 100;
		int currNum = 0;
		List<List<Long>> lll = new ArrayList<>();
		boolean flag = true;

		while (flag) {
			if (ids.size() > oneSize * (currNum + 1)) {
				//非最后一块
				lll.add(ids.subList(currNum * oneSize, oneSize * (currNum + 1)));
			} else {
				//最后一块
				lll.add(ids.subList(currNum * oneSize, ids.size()));
				flag = false;
			}
			currNum++;
		}
		return lll;
	}

	@Data
	private static class GouceEntity {
		private Integer id;
		private String title;
		private Integer is_deleted;
	}

	@Data
	public class TagRelation{
		private String node;
		private Long tag_id;
		private Long relation_id;
		private Long type_id;
	}


	public List<Long> selectNoTagId() {
		Connection conn = null;
		Statement stmt = null;
		List<Long> ids = new ArrayList<>();
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
			sql = "SELECT a.id FROM gov_policy_general a LEFT JOIN gov_tag_relation b ON a.id = b.relation_id WHERE b.relation_id IS NULL AND a.examine_user_id = 2112 ";
			ResultSet rs = stmt.executeQuery(sql);

			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				Long id = rs.getLong("id");
				ids.add(id);

				// 输出数据
//				System.out.print("ID: " + id);
			}
			// 完成后关闭
			rs.close();
			stmt.close();
			conn.close();
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
		System.out.println("selectNoTagId OVER!");
		return ids;
	}
}
