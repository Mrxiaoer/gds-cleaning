package com.cloud.dips.common.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * 导出模板工具类
 * 
 * 将模板文件放在resources包下面
 * 通过如下方法获取InputStream传入该方法
 * InputStream in = this.getClass().getResourceAsStream("/models/models.xlsx"); 
 * 
 * @author RCG
 *
 */
public class TemplateUtils {
	
	public static byte[] toByteArray(InputStream input) throws IOException{
		ByteArrayOutputStream output = new ByteArrayOutputStream(); 
    	byte[] buffer = new byte[4096]; 
    	int n = 0; 
    	while (-1 != (n = input.read(buffer))) { 
    	output.write(buffer, 0, n); 
    	} 
    	return output.toByteArray(); 
	}

}
