package com.cloud.gds.gmsanalyse.config;

import org.springframework.stereotype.Component;

import javax.sql.CommonDataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2019-02-01
 */
@Component
public class MyDataSource implements CommonDataSource, Wrapper {

	// JDBC 驱动名及数据库 URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL =
		"jdbc:mysql://118.31.60.34:3306/dips_cloud_gov?useUnicode=true&useSSL=false" + "&characterEncoding=UTF-8";

	// 数据库的用户名与密码，需要根据自己的设置
	private static final String USER = "root";
	private static final String PASS = "Gov20130528";
	// 链表 --- 实现 栈结构 、队列 结构
	private final LinkedList<Connection> dataSources = new LinkedList<>();

	MyDataSource() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 一次性创建10个连接
		for (int i = 0; i < 16; i++) {
			try {
				Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				// 将连接加入连接池中
				dataSources.add(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Connection getConnection() throws SQLException {
		// 取出连接池中一个连接,删除第一个连接返回
		Connection conn;
		synchronized (dataSources) {
			conn = dataSources.removeFirst();
			// System.out.println("取出一个连接剩余 " + dataSources.size() + "个连接！");
		};
		// 将目标Connection对象进行增强
		return conn;

			// (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), conn.getClass().getInterfaces(),
			// new InvocationHandler() {
			// 	// 执行代理对象任何方法 都将执行 invoke
			// 	@Override
			// 	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// 		if (method.getName().equals("close")) {
			// 			// 需要加强的方法
			// 			// 不将连接真正关闭，将连接放回连接池
			// 			// releaseConnection(conn);
			// 			return null;
			// 		} else {
			// 			// 不需要加强的方法,调用真实对象方法
			// 			return method.invoke(conn, args);
			// 		}
			// 	}
			// });
	}

	// 将连接放回连接池
	public void releaseConnection(Connection conn) {
		synchronized (dataSources) {
			dataSources.add(conn);
			// System.out.println("将连接 放回到连接池中 数量:" + dataSources.size());
		};
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
