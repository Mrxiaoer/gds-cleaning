package com.cloud.gds.cleaning.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hankcs.hanlp.suggest.Suggester;
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
		String url = "https://hei-ha.oss-cn-beijing.aliyuncs"
			+ ".com/file/20190104/%E5%9C%A8%E7%BA%BF%E9%A2%84%E8%A7%88%E6%B5%8B"
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
		String str =
			"<p style=\"text-align: center;\">转发住房和城乡建设部办公厅关于建设工程企业资质专家审查意见的公示</p><p><br/></p><p style=\"box-sizing: "
				+ "border-box; margin-top: 0px; margin-bottom: 10px;\">各相关企业：</p><p style=\"box-sizing: border-box; "
				+ "margin-top: 0px; margin-bottom: 10px;"
				+ "\">　　现将住房和城乡建设部办公厅《关于建设工程企业资质专家审查意见的公示》（建办受理函〔2019〕2号）转发你们，具体公示内容可登录http://www.mohurd.gov"
				+ ".cn/wjfb/201901/t20190131_239393"
				+ ".html查询。可以申报单位的名义、按如下要求于2019年2月15日前将申述书面材料及相关证明材料报送我厅对外办事窗口，由我厅统一报送住房城乡建设部，逾期不予受理。</p><p "
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
		String pattern1 = "<p[^>]*?>((?!<p>|</p>).)*?(\\d+?)((?!<p>|</p>|[\u4e00-\u9fa5]|\\d).)*?年("
			+ "(?!<p>|</p>|[\u4e00-\u9fa5]).)*?"
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
		String document =
			"<p>     关于将2013年3月三大粮食作物制种纳入中央财政农业保险</p><p>保险费补贴目录有关事项的通知</p><p " + "class=\"ql-align-center\">     "
				+ "   "
				+ "              财金〔2018〕91号</p><p " + "class=\"ql-align-justify"
				+
				"\">1994-2-24"
				+ "各省、自治区、直辖市、计划单列市财政厅（局）、农业（农牧、农村经济）厅（局、委、办）、银保监局，新疆生产建设兵团财政局，黑龙江省农垦总局，广东省农垦总局，中国储备粮管理总公司，中国农业发展集团有限公司：</p><p class=\"ql-align-justify\">为贯彻落实党中央、国务院有关要求，促进我国制种行业长期可持续发展，稳定主要粮食作物种子供给，保障国家粮食安全，现将三大粮食作物制种纳入中央财政农业保险保险费补贴目录。有关事项通知如下：</p><p class=\"ql-align-justify\">一、农户、种子生产合作社和种子企业等开展的符合规定的三大粮食作物制种，对其投保农业保险应缴纳的保费，纳入中央财政农业保险保险费补贴目录，补贴比例执行《财政部关于印发&lt;中央财政农业保险保险费补贴管理办法&gt;的通知》（财金〔2016〕123号，以下简称《补贴管理办法》）关于种植业有关规定。符合规定的三大粮食作物制种，指符合《种子法》规定、按种子生产经营许可证规定或经当地农业部门备案开展的水稻、玉米、小麦制种，包括扩繁和商品化生产等种子生产环节。</p><p class=\"ql-align-justify\">二、保险经办机构应使用保险监管部门统一发布的示范性条款，保险责任应涵盖制种面临的自然灾害、病虫害以及其他风险等导致的产量损失或质量损失，保险金额应为保险标的生长期内所发生的直接物化成本。投保人和被保险人应为实际土地经营者，如实际土地经营者的制种生产风险已完全转移给种子生产组织的，种子生产组织也可作为投保人和被保险人。</p><p class=\"ql-align-justify\">三、符合规定的三大粮食作物制种的种子品种，由各级农业及种子主管部门负责以适当方式向保险经办机构提供。各级农业及种子主管部门应协助保险经办机构做好保险标的审核确认，以及灾因鉴定、损失评估等工作。各级保险监督管理机构要指导保险经办机构做好保险产品条款设计、费率厘定、承保理赔服务等工作。</p><p class=\"ql-align-justify\">四、三大粮食作物制种纳入中央财政农业保险保险费补贴目录涉及的预算管理、机构管理、监督检查等事宜，适用《补贴管理办法》有关规定。</p><p class=\"ql-align-justify\">五、本通知自印发之日起施行。2018年投保农业保险的三大粮食作物制种，可按本通知规定申请中央财政农业保险保险费补贴。各地应结合本通知要求，制定具体实施细则，执行中有关情况请及时报告。</p><p class=\"ql-align-right\">财政部&nbsp;农业农村部&nbsp;银保监会</p><p class=\"ql-align-right\">2018年7月30日</p><p><br></p>";
		//定义HTML标签的正则表达式，去除标签，只提取文字内容
		// String htmlRegex="<[^>]+>";
		String pattern2 = "(\\d+)年(\\d+)月(\\d+)日";
		Pattern p2 = Pattern.compile(pattern2);
		Matcher m2 = p2.matcher(document);
		SimpleDateFormat sdfF1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfP2 = new SimpleDateFormat("yyyy年MM月dd日");
		if (m2.find()) {
			try {
				Date date = sdfF1
					.parse(StrUtil.trim(m2.group(1) + "-" + m2.group(2) + "-" + m2.group(3) + " 11:11:11"));
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
		String str = "!!！？？!!!!%*）%￥！KTV去\'符号\'标号！！当然;；《~·、：”‘？@#￥%……&*》,，\"。!!..**半\"角";
		System.out.println(str);
		String str1 = str.replaceAll("[\\pP\\p{Punct}]", "");
		System.out.println("str1:" + str1);


		String str2 = str.replaceAll("[\\pP]", "");
		System.out.println("str2:" + str2);


		String str3 = str.replaceAll("[\\p{P}]", "");
		System.out.println("str3:" + str3);
	}

	@Data
	class Person {

		int age;
		String name;
		int sex;

	}

}
