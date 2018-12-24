package com.cloud.dips.common.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 特殊字符处理工具
 * @author ZB
 *
 */
public class SpecialStringUtil {
	
	/**
	 * 去除特殊字符
	 * @param str
	 * @return
	 */
	public static String filterStr(String str){
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？_]";
		Pattern pat = Pattern.compile(regEx);     
        Matcher mat = pat.matcher(str);    
        return mat.replaceAll("").trim();  
	}
	/**
	 * 转义特殊字符
	 * @param keyword
	 * @return
	 */
	public static String escapeExprSpecialWord(String keyword) {
        if (StringUtils.isNotEmpty(keyword)) {
            String[] fbsArr = { "\\","$","(",")","*","+",".","[", "]","?","^","{","}","|","'","%" };
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }
}
