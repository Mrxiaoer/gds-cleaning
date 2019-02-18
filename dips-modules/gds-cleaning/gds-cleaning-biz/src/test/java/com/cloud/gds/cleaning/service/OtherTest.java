package com.cloud.gds.cleaning.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;
import org.junit.Test;

/**
 * 零碎的测试
 *
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2018-12-19
 */
public class OtherTest {

	@Test
	public void JSONObjRemoveTest() {
		String str = "{\"name\": \"222\",\"length\": 8,\"age\": 9,\"city\": \"中国浙江\"}";
		JSONObject jsonObj = JSONUtil.parseObj(str);
		jsonObj.remove("name");
		System.out.println(jsonObj);
	}

	@Test
	public void JSONOTest() {
		// System.out.println(com.alibaba.fastjson.JSONObject.parseObject("2"));
		System.out.println(JSONUtil.isJsonObj(""));
		System.out.println(JSONUtil.isJsonObj("{'name':2}"));
		// JSONUtil.parseObj("",false);
	}

	@Test
	public void timeTest1() {
		long start = System.currentTimeMillis();
		long xx = 0;
		for (int i = 0; i < 99999; i++) {
			xx = System.currentTimeMillis();
		}
		long end = System.currentTimeMillis();
		System.out.println("==>" + (end - start) + "====" + xx);
	}

	@Test
	public void timeTest2() {
		long start = System.currentTimeMillis();
		LocalTime xx = null;
		for (int i = 0; i < 99999; i++) {
			xx = LocalTime.now();
		}
		long end = System.currentTimeMillis();
		System.out.println("==>" + (end - start) + "====" + xx);
	}

	@Test
	public void Test() {
		Integer a = new Integer(200);
		Integer b = new Integer(200);
		Integer c = 200;
		Integer e = 200;
		int d = 200;

		System.out.println("两个new出来的对象    ==判断" + (a == b));
		System.out.println("两个new出来的对象    equal判断" + a.equals(b));
		System.out.println("new出的对象和用int赋值的Integer   ==判断" + (a == c));
		System.out.println("new出的对象和用int赋值的Integer   equal判断" + (a.equals(c)));
		System.out.println("两个用int赋值的Integer    ==判断" + (c == e));
		System.out.println("两个用int赋值的Integer    equal判断" + (c.equals(e)));
		System.out.println("基本类型和new出的对象   ==判断" + (d == a));
		System.out.println("基本类型和new出的对象   equal判断" + (a.equals(d)));
		System.out.println("基本类型和自动装箱的对象   ==判断" + (d == c));
		System.out.println("基本类型和自动装箱的对象   equal判断" + (c.equals(d)));
	}

	@Test
	public void random() {
		Random r = new Random(1000);
		for (int i = 1; i <= 4; i++) {
			System.out.println("第" + i + "次:" + r.nextInt());
		}
	}

	@Test
	public void HTMLEncoding() {
		String url = "https://hei-ha.oss-cn-beijing.aliyuncs" + ".com/file/20190104/%E5%9C%A8%E7%BA%BF%E9%A2%84%E8%A7%88%E6%B5%8B"
			+ "%E8%AF%95.docx?Expires=1546570089&OSSAccessKeyId=TMP"
			+ ".AQFjEfOj9O8mY87VRqHzv1cgAgKuqufDJlhZPL9iHKhrr9nXvFnITdPHJf1-ADAtAhQOvg9AHXubg_YgYD"
			+ "-3huq7fAFsBgIVAOdM3jwMH_Kl5XOc9bnpuY89bqlE&Signature=%2FBexsDyAnbuE49vO07DC1iZKpjE%3D";

		String result = "https://view.officeapps.live.com/op/view.aspx?src=";

		try {
			result += URLEncoder.encode(url, "ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		System.out.println(result);
	}

	@Test
	public void smileTest1() {
		Person xx = new Person();
		xx.setName("wyn");
		xxxxx(xx);
		System.out.println(xx.toString());
	}

	private void xxxxx(Object xx) {
		// xx=new Person();
		((Person) xx).setAge(18);
	}

	@Test
	public void cleanBlank() {
		String str = "<p style=\"text-align: center;\">转发住房和城乡建设部办公厅关于建设工程企业资质专家审查意见的公示</p><p><br/></p><p style=\"box-sizing: "
			+ "border-box; margin-top: 0px; margin-bottom: 10px;\">各相关企业：</p><p style=\"box-sizing: border-box; "
			+ "margin-top: 0px; margin-bottom: 10px;" + "\">　　现将住房和城乡建设部办公厅《关于建设工程企业资质专家审查意见的公示》（建办受理函〔2019〕2号）转发你们，具体公示内容可登录http://www.mohurd.gov"
			+ ".cn/wjfb/201901/t20190131_239393" + ".html查询。可以申报单位的名义、按如下要求于2019年2月15日前将申述书面材料及相关证明材料报送我厅对外办事窗口，由我厅统一报送住房城乡建设部，逾期不予受理。</p><p "
			+ "style=\"box-sizing: border-box; margin-top: 0px; margin-bottom: 10px;"
			+ "\">　　一、书面材料及相关证明材料统一用纸质资料袋包装，并按统一格式填写封面（格式附后）。</p><p style=\"box-sizing: border-box; margin-top: "
			+ "0px; margin-bottom: 10px;\">　　二、电子化申报资质企业的陈述意见，应先在住房城乡建设部电子申报系统中填写陈述申请表，并上传相关证明材料。</p><p "
			+ "style=\"box-sizing: border-box; margin-top: 0px; margin-bottom: 10px;"
			+ "\">　　陈述单位应将打印的陈述申请表一式两份、陈述报告一份，连同所有陈述材料的电子文件（U盘或光盘，电子版后缀格式为ZBB）统一装袋后报送我厅对外办事窗口。</p><p "
			+ "style=\"box-sizing: border-box; margin-top: 0px; margin-bottom: 10px;"
			+ "\">　　三、未实施电子化申报资质企业的陈述意见，需提交陈述报告一式两份，书面材料一份，统一装袋后报送我厅对外办事窗口。</p><p style=\"box-sizing: border-box; "
			+ "margin-top: 0px; margin-bottom: 10px;\">&nbsp;</p><p style=\"box-sizing: border-box; margin-top: "
			+ "0px; margin-bottom: 10px;\">　　报送地址：广州市东风中路481号粤财大厦2楼省住房城乡建设厅对外办事窗口。</p><p style=\"box-sizing: "
			+ "border-box; margin-top: 0px; margin-bottom: 10px;\">&nbsp;</p><p style=\"box-sizing: border-box; "
			+ "margin-top: 0px; margin-bottom: 10px;\">　　附：封面格式。</p><p style=\"box-sizing: border-box; margin-top:"
			+ " 0px; margin-bottom: 10px;\">&nbsp;</p><p style=\"box-sizing: border-box; margin-top: 0px; "
			+ "margin-bottom: 10px; text-align: right;\">广东省住房和城乡建设厅行政许可管理处</p><p style=\"box-sizing: border-box; "
			+ "margin-top: 0px; margin-bottom: 10px; text-align: right;\">2019年2月1日　　　　　</p><p>责任编辑：江定倍</p><h1 "
			+ "style=\"box-sizing: border-box; font-size: 18px; margin: 0px; font-family: inherit; line-height: "
			+ "24px; color: rgb(35, 90, 132); padding: 0px 0px 5px; border-bottom: 1px solid rgb(35, 90, 132); "
			+ "float: left;\"><span class=\"lnr lnr-download\" style=\"box-sizing: border-box; font-family: "
			+ "Linearicons-Free; speak: none; font-weight: normal; font-variant-numeric: normal; "
			+ "font-variant-east-asian: normal; line-height: 0.5; -webkit-font-smoothing: antialiased;"
			+ "\"></span>&nbsp;附件下载</h1><ul class=\"text-list list-paddingleft-2\" style=\"box-sizing: border-box;"
			+ " padding: 0px;\"><li><p><a href=\"http://www.gdcic.gov"
			+ ".cn/UploadMessageAttachFiles/20190201/20190201041014136.doc\" target=\"_blank\" style=\"box-sizing:"
			+ " border-box; background-color: transparent; color: rgb(0, 136, 204); text-decoration-line: none;"
			+ "\">封面格式</a><span style=\"box-sizing: border-box; color: #999999; display: inline-block; "
			+ "margin-left: 10px;\">doc&nbsp;&nbsp;</span></p></li></ul><p><br/></p>";
		String pattern1 = "<p[^>]*?>((?!<p>|</p>).)*?(\\d+?)((?!<p>|</p>|[\u4e00-\u9fa5]|\\d).)*?年(" + "(?!<p>|</p>|[\u4e00-\u9fa5]).)*?"
			+ "(\\d+?)((?!<p>|</p>|[\u4e00-\u9fa5]|\\d).)*?月((?!<p>|</p>|[\u4e00-\u9fa5]).)*?(\\d+?)("
			+ "(?!<p>|</p>|[\u4e00-\u9fa5]|\\d).)*?日((?!<p>|</p>|[\u4e00-\u9fa5]|。|\\.).)*?</p>";
		Pattern p1 = Pattern.compile(pattern1);
		Matcher m1 = p1.matcher(str);

		SimpleDateFormat sdfP2 = new SimpleDateFormat("yyyy年MM月dd日");

		if (m1.find()) {

			// Date date = sdfP2.parse(StrUtil.cleanBlank(m1.group(1)));
			// System.out.println(date);

			for (int i = 0; i < 11; i++) {
				System.out.println("===>" + m1.group(i));
			}

			System.out.println("===>" + m1.group(2));
			System.out.println("===>" + m1.group(5));
			System.out.println("===>" + m1.group(8));
		}

	}

	@Test
	public void hanlp() {
		String document = "<font>所属主题：法哈哈</font><font>发文日期：2015-02-02</font><font>公开责任部门：省政府法制办公室</font></p>"
			+ "<font>所属主题：法制</font><font>发文日期：2016-02-02</font><font>公开责任部门：省政府法制办公室</font></p>"
			+ "<font>所属主题：法哈哈</font><font>发文日期：2017年02月02日</font><font>公开责任部门：省政府法制办公室</font></p>";
		//定义HTML标签的正则表达式，去除标签，只提取文字内容
		// String htmlRegex="<[^>]+>";
		// String pattern2 = ".*[^\u4e00-\u9fa5]([\u4e00-\u9fa5]+?(厅|部|办公室))[\\s\\S]*?(发文日期|生成日期)?[\\s\\S]?[\\s]*?(\\d+[-|年]\\d+[-|月]\\d+[日]?)";
		String pattern2 = ".*(?![-|\\dA-z]).(\\d+?)-(\\d+?)-(\\d+?)(?![-|\\d])";
		Pattern p2 = Pattern.compile(pattern2);
		Matcher m2 = p2.matcher(document);
		SimpleDateFormat sdfF1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfP2 = new SimpleDateFormat("yyyy年MM月dd日");
		if (m2.find()) {
			System.out.println(m2.group(1));
			try {
				Date date = sdfF1.parse(StrUtil.trim(m2.group(1)+"-"+m2.group(2)+"-"+m2.group(3) + " 00:00:11"));
				System.out.println("时间：" + sdfP2.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// System.out.println(document.replaceAll(htmlRegex, "\n"));
		// List<String> sentenceList = HanLP.extractSummary(document.replaceAll(htmlRegex, "\n"), 1);
		// System.out.println(sentenceList);
	}

	@Test
	public void wbtj() {
		String str = "!!！？？!!!!%*）%￥！KTV去\'符 &gt; 号\'标号！！当然;；《~·、：”‘？@#￥%……&*》,，\"。!!..**半\"角";
		System.out.println(str);
		String str1 = str.replaceAll("&#?[a-zA-Z0-9]{1,10};", "");
		System.out.println("str1:" + str1);

	}

	@Data
	class Person {

		int age;
		String name;
		int sex;

	}

}
