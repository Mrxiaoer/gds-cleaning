package com.cloud.gds.preprocessing.utils;

/**
 * 转化utils
 *
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-03-21
 */
public class ConversionUtils {

	public static Integer styleS2I(String style) {
		Integer styleI;
		switch (style) {
			case "通知":
				styleI = 1;
				break;
			case "公告":
				styleI = 2;
				break;
			case "报告":
				styleI = 3;
				break;
			case "意见":
				styleI = 4;
				break;
			case "办法":
				styleI = 5;
				break;
			case "通报":
				styleI = 6;
				break;
			default:
				styleI = 7;
		}
		return styleI;
	}

	public static Integer levelSTI(String level) {
		Integer levelI;
		switch (level) {
			case "国家级":
				levelI = 1;
				break;
			case "省级":
				levelI = 2;
				break;
			case "市级":
				levelI = 3;
				break;
			case "区级（县级)":
				levelI = 4;
				break;
			case "区级":
				levelI = 4;
				break;
			case "县级":
				levelI = 4;
				break;
			default:
				levelI = 0;
		}
		return levelI;
	}

}
