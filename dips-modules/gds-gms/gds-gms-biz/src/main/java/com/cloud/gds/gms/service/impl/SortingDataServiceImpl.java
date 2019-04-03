package com.cloud.gds.gms.service.impl;

import com.cloud.gds.gms.service.SortingDataService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : yaonuan
 * @Email : 806039077@qq.com
 * @Date : 2019-04-01
 */
@Service
public class SortingDataServiceImpl implements SortingDataService {

	@Override
	public String replaceSpecialSign(Object titleSObject) {
		String string = titleSObject.toString();

		String regEx = "[\\〈 \\〉\\(\\)\\（\\）\\[\\]\\【\\】\\{\\}\\“\\”\\<\\>\\《\\》\\‘\\’\\——\\-\\,\\，\\、]+";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(string);
		String replace = matcher.replaceAll(" ").trim();

		Pattern pat = Pattern.compile("\\s+");
		Matcher mat = pat.matcher(replace);
		String newReplace = mat.replaceAll(" +");

		StringBuilder builder = new StringBuilder(newReplace);
		builder.insert(0, "+");
		String buildString = builder.toString();
		return buildString;
	}
}
