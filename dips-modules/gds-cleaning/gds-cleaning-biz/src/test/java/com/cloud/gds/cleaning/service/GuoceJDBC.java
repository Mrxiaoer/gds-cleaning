package com.cloud.gds.cleaning.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.cloud.gds.cleaning.GdsCleaningApplication;
import com.cloud.gds.cleaning.api.entity.DataFieldValue;
import com.cloud.gds.cleaning.config.MyDataSource;
import com.hankcs.hanlp.HanLP;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

	@Autowired
	private DataFieldValueService dataFieldValueService;
	@Autowired
	private ExecutorService analysisThreadPool;
	@Autowired
	private DoAnalysisService doAnalysisService;
	@Autowired
	private MyDataSource myDataSource;

	/**
	 * 100%相似度的set方式清洗
	 */
	@Test
	public void guoceClean() {
		List<Long> ids = doAnalysisService.getNoExactlySameDataIds(98L);
		List<List<Long>> idLists = cutIds(ids, 500);

		AtomicInteger allNum = new AtomicInteger(idLists.size());
		for (List<Long> idList : idLists) {
			analysisThreadPool.execute(() -> {
				this.move(idList);
				allNum.getAndDecrement();
			});
		}

		while (allNum.get() > 0) {
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void gouceInsert() {
		Connection conn = null;
		Statement stmt = null;
		try {

			// 打开链接
			conn = myDataSource.getConnection();

			// 执行查询
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id,title,is_deleted FROM scrapy_gov_policy_general WHERE is_deleted != 1 ORDER BY is_deleted" + " DESC ";
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

				GouceEntity gouceEntity = new GouceEntity();
				gouceEntity.setId(id);
				gouceEntity.setTitle(name);
				gouceEntity.setIs_deleted(is_deleted);
				fieldValue.setFieldValue(JSON.toJSONString(gouceEntity));
				fieldValue.setCreateUser(0);
				list.add(fieldValue);

			}
			dataFieldValueService.batchSave(list, 1000);
			// 完成后关闭
			// rs.close();
			// stmt.close();
			myDataSource.releaseConnection(conn);
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		}
		System.out.println("OVER!");
	}

	/**
	 * 表间移动数据
	 *
	 * @param list
	 */
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
				+ "effect_time,text,url,creator_id,scrapy_id,examine_status,examine_user_id,processor_id," + "examine_date)"
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
		System.out.println("OVER!");
	}

	/**
	 * 多线程打标签
	 *
	 * @throws Exception
	 */
	public void MultiThreadLabel() throws Exception {

		//查询未打标签的ids
		List<Long> ids = selectIds("SELECT a.id FROM gov_policy_general a LEFT JOIN gov_tag_relation b ON a.id = b.relation_id WHERE b"
			+ ".relation_id IS NULL AND a.examine_user_id = 2112");
		List<List<Long>> idLists = cutIds(ids, 100);

		for (List<Long> idList : idLists) {
			analysisThreadPool.execute(() -> {
				//根据ids分块查询数据
				Connection conn = null;
				Statement stmt;
				ResultSet rs = null;
				try {
					conn = myDataSource.getConnection();
					stmt = conn.createStatement();
					//todo 请输入正确的sql
					String sql = "SELECT * FROM x";
					rs = stmt.executeQuery(sql);
					while (rs.next()) {
						//todo 请设定内部类TagBeforeData的字段并填充数据
						TagBeforeData tagBeforeData = new TagBeforeData();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				//todo 打标签
				// TagRelation tagRelation = new TagRelation();
				// // 固定
				// tagRelation.setNode("gov_general_policy");
				// tagRelation.setType_id(4L);

				//todo 对标签进行存储

				myDataSource.releaseConnection(conn);
			});
		}
	}

	/**
	 * 多线程更新时间
	 *
	 * @throws Exception
	 */
	@Test
	public void MultiThreadGetTime() throws Exception {
		// List<Long> ids = selectNoTagId();
		// publish_time < "1900-01-01 00:00:05" or
		List<Long> ids = selectIds(
			"SELECT id FROM gov_policy_general WHERE ( publish_time like '%11:11:11') and examine_user_id " + "= 2158 and " + "examine_status = 3");

		// AtomicBoolean flag = new AtomicBoolean(false);
		AtomicInteger doNum = new AtomicInteger(ids.size());

		SimpleDateFormat sdfP1 = new SimpleDateFormat("yyyy-MM-dd");
		Date minDate = sdfP1.parse("1800-01-01");

		for (Long id : ids) {
			try {
				// analysisThreadPool.execute(() -> {
					// 按指定模式在字符串查找
					String text = this.selectText("select text from gov_policy_general where id = " + id);

					// 创建 Pattern 对象
					String pattern = "<td[\\s\\S]*?>(发文日期|生成日期)</td>[\\s\\S]*?<td>[^<>]*?(\\d+?-\\d+?-\\d+?)" + "[^<>]*?</td>";
					String pattern4 = "(发文日期|生成日期)[\\s\\S][\\s]*?(\\d+)[-|年](\\d+)[-|月](\\d+)[日]?";
					String pattern1 = "<p[^>]*?>((?!<p>|</p>).)*?(\\d+?)((?!<p>|</p>|[\u4e00-\u9fa5]|\\d).)*?年(" + "(?!<p>|</p>|[\u4e00-\u9fa5]).)*?"
						+ "(\\d+?)((?!<p>|</p>|[\u4e00-\u9fa5]|\\d).)*?月((?!<p>|</p>|[\u4e00-\u9fa5]).)*?(\\d+?)("
						+ "(?!<p>|</p>|[\u4e00-\u9fa5]|\\d).)*?日((?!<p>|</p>|[\u4e00-\u9fa5]|[\\pP]).)*?</p>";
					String pattern2 = ".*[^\\d](\\d+)年(\\d+)月(\\d+)日";
					String pattern3 = ".*(?![-|\\dA-z]).(\\d+?)-(\\d+?)-(\\d+?)(?![-|\\d])";

					Pattern p = Pattern.compile(pattern);

					// 创建 matcher 对象
					Matcher m = p.matcher(text);
					Connection conn = null;
					Statement stmt;
					try {
						conn = myDataSource.getConnection();

						stmt = conn.createStatement();
						String sql;
						if (m.find()) {
							Date date = DateSyncUtil.parse(StrUtil.trim(m.group(2))+" 00:00:00");
							System.out.println("m处理：" + id + ";====>时间：" + DateSyncUtil.format(date));
							sql = getTimeSql(id, date, minDate);
						} else {
							Pattern p4 = Pattern.compile(pattern4);
							Matcher m4 = p4.matcher(text);
							if (m4.find()) {
								Date date = DateSyncUtil.parse(StrUtil.trim(m4.group(2) + "-" + m4.group(3) + "-" + m4.group(4)+" 00:00:00"));
								System.out.println("m4处理：" + id + ";====>时间：" + DateSyncUtil.format(date));
								sql = getTimeSql(id, date, minDate);
							} else {
								Pattern p1 = Pattern.compile(pattern1);
								Matcher m1 = p1.matcher(text);
								if (m1.find()) {
									Date date = DateSyncUtil.parse(StrUtil.cleanBlank(m1.group(2) + "-" + m1.group(5) + "-" + m1.group(8)) + " 00:00:00");
									System.out.println("m1处理：" + id + ";====>时间：" + DateSyncUtil.format(date));
									sql = getTimeSql(id, date, minDate);
								} else {
									Pattern p2 = Pattern.compile(pattern2);
									Matcher m2 = p2.matcher(text);
									if (m2.find()) {
										System.out.println(id + "======" + m2.group(1) + "-" + m2.group(2) + "-" + m2.group(3));
										int year = Integer.parseInt(m2.group(1));
										Date date = DateSyncUtil.parse(StrUtil.trim(
											(year > 50 ? year > 100 ? year : (year + 1900) : (2000 + year)) + "-" + m2.group(2) + "-" + m2.group(3)
												+ " 11:11:12"));
										System.out.println("m2处理：" + id + ";====>时间：" + DateSyncUtil.format(date));
										sql = getTimeSql(id, date, minDate);
									} else {
										Pattern p3 = Pattern.compile(pattern3);
										Matcher m3 = p3.matcher(text);
										if (m3.find()) {
											Date date = DateSyncUtil.parse(StrUtil.trim(m3.group(1) + "-" + m3.group(2) + "-" + m3.group(3) + " 11:11:12"));
											System.out.println("m3处理：" + id + ";====>时间：" + DateSyncUtil.format(date));
											sql = getTimeSql(id, date, minDate);
										} else {
											// System.out.println("NO MATCH");
											sql = "UPDATE gov_policy_general SET publish_time = \"1900-01-01 00:00:05\" WHERE id = " + id;
										}
									}
								}
							}
						}
						stmt.execute(sql);

					} catch (ParseException | SQLException e) {
						e.printStackTrace();
					}

					if (conn != null) {
						myDataSource.releaseConnection(conn);
					}

					doNum.getAndDecrement();
				// });
			} catch (Exception e) {
				e.printStackTrace();
				doNum.getAndDecrement();
				// break;
			}
		}

		while (doNum.get() > 0) {
			try {
				Thread.sleep(1000L);
				System.out.println("剩余数量 =====> " + doNum.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 多线程更新时间
	 *
	 * @throws Exception
	 */
	@Test
	public void MultiThreadGetDepart() throws Exception {
		List<Long> ids = selectIds(
			"SELECT id FROM gov_policy_general WHERE publish_time < \"1900-01-01 00:00:05\" " + "and examine_user_id = 2158 and examine_status = 3");

		AtomicInteger doNum = new AtomicInteger(ids.size());

		for (Long id : ids) {
			try {
				analysisThreadPool.execute(() -> {
					// 按指定模式在字符串查找
					String text = this.selectText("select text from gov_policy_general where id = " + id);

					String patternBM =
						".*[^\u4e00-\u9fa5]([\u4e00-\u9fa5]+?(机构|委|署|局|厅|处|部|室|委员会|行|院|台|中心|报|司|办|府))[^\u4e00-\u9fa5]*?(发文日期|生成日期)?[\\s\\S]?[\\s]*?"
							+ "(\\d+[-|年]\\d+[-|月]\\d+[日]?)";
					Pattern pbm1 = Pattern.compile(patternBM);
					Matcher mbm = pbm1.matcher(text);

					Connection conn = null;
					Statement stmt;
					try {
						conn = myDataSource.getConnection();

						stmt = conn.createStatement();
						String sql;
						if (mbm.find()) {
							String depart = mbm.group(1);
							System.out.println("mbm处理：" + id + ";====>部门：" + depart);
							sql = "UPDATE gov_policy_general SET publish_time = \"1900-01-01 00:00:04\" WHERE id = '" + id + "'";
						} else {
							sql = "UPDATE gov_policy_general SET publish_time = \"1900-01-01 00:00:04\" WHERE id = '" + id + "'";
						}
						// stmt.execute(sql);
						System.out.println(mbm.group(0));
					} catch (SQLException e) {
						e.printStackTrace();
					}

					if (conn != null) {
						myDataSource.releaseConnection(conn);
					}

					doNum.getAndDecrement();
				});
			} catch (Exception e) {
				e.printStackTrace();
				doNum.getAndDecrement();
				break;
			}
		}

		while (doNum.get() > 0) {
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private String getTimeSql(long id, Date date, Date minDate) {
		String sql = null;
		if (date.before(new Date()) && date.after(minDate)) {
			try {
				sql = "UPDATE gov_policy_general SET publish_time = \"" + DateSyncUtil.format(date) + "\" WHERE id = '" + id + "'";
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			sql = "UPDATE gov_policy_general SET publish_time = \"1900-01-01 00:00:05\" WHERE id = '" + id + "'";
		}
		return sql;
	}

	/**
	 * 切分ids
	 *
	 * @param ids
	 * @return
	 */
	public List<List<Long>> cutIds(List<Long> ids, int oneSize) {
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

	public List<Long> selectIds(String sql) {
		List<Long> ids = new ArrayList<>();
		Connection conn;
		Statement stmt;
		try {
			conn = myDataSource.getConnection();
			stmt = conn.createStatement();
			// 执行查询
			ResultSet rs = stmt.executeQuery(sql);
			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				Long id = rs.getLong("id");
				ids.add(id);
			}
			// 完成后归还连接
			myDataSource.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}

	private String selectText(String sql) {
		Connection conn;
		Statement stmt;
		ResultSet rs;
		String str = "";
		try {
			conn = myDataSource.getConnection();
			stmt = conn.createStatement();
			// 执行查询
			rs = stmt.executeQuery(sql);
			// 展开结果集数据库
			while (rs.next()) {
				str = rs.getString("text");
			}
			// 完成后归还连接
			myDataSource.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

	@Test
	public void extractSummary() {
		List<Long> ids = selectIds("SELECT a.id FROM gov_policy_general a WHERE a.examine_user_id = 2158 and a.summary like '%&%'");

		// AtomicBoolean flag = new AtomicBoolean(false);
		AtomicInteger doNum = new AtomicInteger(ids.size());

		for (Long id : ids) {
			try {
				analysisThreadPool.execute(() -> {
					// 按指定模式在字符串查找
					String document = this.selectText("select text from gov_policy_general where id = " + id);
					String htmlRegex = "<[^>]+>";
					String regexSpecial = "&#?[a-zA-Z0-9]{1,10};";
					List<String> sentenceList = HanLP.extractSummary(document.replaceAll(htmlRegex, "\n").replaceAll(regexSpecial, ""), 1);
					// System.out.println(id + " ====> " + sentenceList);
					Connection conn = null;
					Statement stmt;
					if (sentenceList.size() > 0) {
						try {
							conn = myDataSource.getConnection();
							stmt = conn.createStatement();

							String summary = sentenceList.get(0).replaceAll("\"", "\\\\\"");
							System.out.println(id + " ====> " + summary);
							String sql = "UPDATE gov_policy_general SET summary = \"" + summary + "\" WHERE id = " + id;
							stmt.execute(sql);

						} catch (SQLException e) {
							// flag.set(true);
							e.printStackTrace();
						}
					}

					if (conn != null) {
						myDataSource.releaseConnection(conn);
					}

					doNum.getAndDecrement();
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		while (doNum.get() > 0) {
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void removeShortData() {
		List<Long> ids = selectIds("SELECT id FROM gov_policy_general WHERE examine_user_id = 2158 and examine_status" + " = 3");

		AtomicBoolean flag = new AtomicBoolean(false);
		AtomicInteger doNum = new AtomicInteger(ids.size());

		for (Long id : ids) {
			try {
				analysisThreadPool.execute(() -> {
					// 按指定模式在字符串查找
					String document = this.selectText("select text from gov_policy_general where id = " + id);
					String htmlRegex = "<[^>]+>";
					String htmlRegex1 = "&#13;|\\d";

					document = StrUtil.cleanBlank(document.replaceAll(htmlRegex, "").replaceAll(htmlRegex1, ""));
					Connection conn = null;
					Statement stmt;
					try {
						conn = myDataSource.getConnection();
						stmt = conn.createStatement();
						String sql;
						if (document.length() > 64) {
							// sql = "UPDATE gov_policy_general SET examine_user_id = 2158 WHERE id = " + id;
						} else {
							System.out.println("===>" + document);
							sql = "UPDATE gov_policy_general SET examine_status = 4 WHERE id = " + id;
							stmt.execute(sql);
						}

					} catch (SQLException e) {
						flag.set(true);
						e.printStackTrace();
					}

					if (conn != null) {
						myDataSource.releaseConnection(conn);
					}

					doNum.getAndDecrement();
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		while (doNum.get() > 0 || flag.get()) {
			try {
				Thread.sleep(250L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Data
	private static class GouceEntity {

		private Integer id;
		private String title;
		private Integer is_deleted;

	}

	@Data
	private class TagRelation {

		private String node;
		private Long tag_id;
		private Long relation_id;
		private Long type_id;

	}

	@Data
	private class TagBeforeData {

	}

}
