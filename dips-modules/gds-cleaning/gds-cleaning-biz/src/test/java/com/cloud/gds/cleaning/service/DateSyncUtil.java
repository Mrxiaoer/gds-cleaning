package com.cloud.gds.cleaning.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 同步时间转换根据
 *
 * @Author : lolilijve
 * @Email : lolilijve@gmail.com
 * @Date : 2019-02-16
 */
public class DateSyncUtil {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String format(Date date)throws ParseException {
		synchronized(sdf){
			return sdf.format(date);
		}
	}

	public static Date parse(String strDate) throws ParseException{
		synchronized(sdf){
			return sdf.parse(strDate);
		}
	}
}
