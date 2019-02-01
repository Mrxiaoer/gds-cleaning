package com.cloud.gds.cleaning.service;

import com.alibaba.fastjson.JSON;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import javax.sql.DataSource;
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
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://118.31.60.34:3306/dips_cloud_gov2";

	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "root";
	static final String PASS = "Gov20130528";

	@Autowired
	private DataFieldValueService dataFieldValueService;
	@Autowired
	private ExecutorService analysisThreadPool;

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
		System.out.println("OVER!");
	}

	public void MultiThreadLabel() throws Exception {

		MyDataSource myDataSource = new MyDataSource();
		//查询未打标签的ids
		Connection conn = null;
		try {
			conn = myDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//todo jdbc操作
		List<Long> ids = new ArrayList<>();
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

			//标签jdbc存储
			//todo jdbc操作
			myDataSource.releaseConnection(connect);
		});
	}

	@Data
	private static class GouceEntity {

		private Integer id;

		private String title;

		private Integer is_deleted;

	}

	public class MyDataSource implements DataSource {

		// 链表 --- 实现 栈结构 、队列 结构
		private LinkedList<Connection> dataSources = new LinkedList<Connection>();

		public MyDataSource() {
			// 一次性创建10个连接
			for (int i = 0; i < 10; i++) {
				try {
					Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
					// 将连接加入连接池中
					dataSources.add(conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public Connection getConnection() throws SQLException {
			// 取出连接池中一个连接
			final Connection conn = dataSources.removeFirst(); // 删除第一个连接返回
			System.out.println("取出一个连接剩余 " + dataSources.size() + "个连接！");
			// 将目标Connection对象进行增强
			return (Connection) Proxy
				.newProxyInstance(conn.getClass().getClassLoader(), conn.getClass().getInterfaces(),
					new InvocationHandler() {
						// 执行代理对象任何方法 都将执行 invoke
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if (method.getName().equals("close")) {
								// 需要加强的方法
								// 不将连接真正关闭，将连接放回连接池
								releaseConnection(conn);
								return null;
							} else {
								// 不需要加强的方法
								return method.invoke(conn, args); // 调用真实对象方法
							}
						}
					});
		}

		@Override
		public Connection getConnection(String username, String password) throws SQLException {
			return null;
		}

		// 将连接放回连接池
		private void releaseConnection(Connection conn) {
			dataSources.add(conn);
			System.out.println("将连接 放回到连接池中 数量:" + dataSources.size());
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return null;
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return false;
		}

		@Override
		public PrintWriter getLogWriter() throws SQLException {
			return null;
		}

		@Override
		public void setLogWriter(PrintWriter out) throws SQLException {

		}

		@Override
		public int getLoginTimeout() throws SQLException {
			return 0;
		}

		@Override
		public void setLoginTimeout(int seconds) throws SQLException {

		}

		@Override
		public Logger getParentLogger() throws SQLFeatureNotSupportedException {
			return null;
		}

	}

}
