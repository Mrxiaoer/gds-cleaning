package com.cloud.dips.common.core.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 地址工具类
 * @author BigPan
 *
 */
public class AddressUtils {
	private static final int TEMPLENGTH = 3;
	private static String REGULAR="\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>";
	/**
	* 获取本机的内网ip地址
	* @return
	* @throws SocketException
	*/
	public String getInnetIp() throws SocketException {
			// 本地IP，如果没有配置外网IP则返回它
	        String localip = null;
	        // 外网IP
	        String netip = null;
	        Enumeration<NetworkInterface> netInterfaces;
	netInterfaces = NetworkInterface.getNetworkInterfaces();
	        InetAddress ip = null;
	        // 是否找到外网IP
	        boolean finded = false;
	        while (netInterfaces.hasMoreElements() && !finded) {
	          NetworkInterface ni = netInterfaces.nextElement();
	          Enumeration<InetAddress> address = ni.getInetAddresses();
	            while (address.hasMoreElements()) {
	                ip = address.nextElement();
	              if (!ip.isSiteLocalAddress() 
	                        && !ip.isLoopbackAddress() 
	                        && ip.getHostAddress().indexOf(":") == -1) {
	                    netip = ip.getHostAddress();
	                    finded = true;
	                              break;
	            } else if (ip.isSiteLocalAddress() 
	                     && !ip.isLoopbackAddress() 
	             && ip.getHostAddress().indexOf(":") == -1) {
	                    localip = ip.getHostAddress();
	                }
	            }
	        }
	        if (netip != null && !"".equals(netip)) {
	            return netip;
	        } else {
	            return localip;
	        }
	}
	

	/**
	* 获取本机的外网ip地址
	* @return
	*/
	public String getV4IP(){
	String ip = "";
	String chinaz = "http://ip.chinaz.com";

	StringBuilder inputLine = new StringBuilder();
	String read = "";
	URL url = null;
	HttpURLConnection urlConnection = null;
	BufferedReader in = null;
	try {
	url = new URL(chinaz);
	urlConnection = (HttpURLConnection) url.openConnection();
	   in = new BufferedReader( new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
	while((read=in.readLine())!=null){
	inputLine.append(read+"\r\n");
	}

	} catch (MalformedURLException e) {
	e.printStackTrace();
	} catch (IOException e) {
	e.printStackTrace();
	}finally{
	if(in!=null){
	try {
	in.close();
	} catch (IOException e) {

	e.printStackTrace();
	}
	}
	}
	Pattern p = Pattern.compile(REGULAR);
	Matcher m = p.matcher(inputLine.toString());
	if(m.find()){
	String ipstr = m.group(1);
	ip = ipstr;

	}
	return ip;
	}

	/**
	* 解析ip地址
	* 
	* 设置访问地址为http://ip.taobao.com/service/getIpInfo.php
	* 设置请求参数为ip=[已经获得的ip地址]
	* 设置解码方式为UTF-8
	* 
	* @param content  请求的参数 格式为：ip=192.168.1.101
	* @param encoding 服务器端请求编码。如GBK,UTF-8等
	* @return
	* @throws UnsupportedEncodingException
	*/
	public String getAddresses(String content, String encoding) throws UnsupportedEncodingException {
	//设置访问地址
	String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
	// 从http://whois.pconline.com.cn取得IP所在的省市区信息
	String returnStr = this.getResult(urlStr, content, encoding);
	if (returnStr != null) {
	// 处理返回的省市区信息

	String[] temp = returnStr.split(",");
	if (temp.length < TEMPLENGTH) {
		// 无效IP，局域网测试
		return "0";
	}

	//国家
	String country = ""; 
	//地区
	String area = ""; 
	//省份
	String region = ""; 
	//市区
	String city = "";
	//地区
	String county = ""; 
	//ISP公司
	String isp = ""; 
	for (int i = 0; i < temp.length; i++) {
	switch (i) {
	case 2:
	country = (temp[i].split(":"))[1].replaceAll("\"", "");
	country = URLDecoder.decode(country, encoding);
	break;
	case 3:
	area = (temp[i].split(":"))[1].replaceAll("\"", "");
	area = URLDecoder.decode(area, encoding);
	break;
	case 4:
	region = (temp[i].split(":"))[1].replaceAll("\"", "");
	region = URLDecoder.decode(region, encoding);
	break;
	case 5:
	city = (temp[i].split(":"))[1].replaceAll("\"", "");
	city = URLDecoder.decode(city, encoding);
	if("内网IP".equals(city)) {
	return "地址为：内网IP";
	}
	break;
	case 6:
	county = (temp[i].split(":"))[1].replaceAll("\"", "");
	county = URLDecoder.decode(county, encoding);
	break;
	case 7:
	isp = (temp[i].split(":"))[1].replaceAll("\"", "");
	isp = URLDecoder.decode(isp, encoding);
	break;
	default:
	}
	}


	return new StringBuffer("地址为："+country+",").append(region+"省,").append(city+"市,").append(county+",").append("ISP公司："+isp)
	.toString();
	}
	return null;
	}

	
	/**
	* 访问目标地址并获取返回值
	* @param urlStr 请求的地址
	* @param content 请求的参数 格式为：ip=192.168.1.101
	* @param encoding 服务器端请求编码。如GBK,UTF-8等
	* @return
	*/
	private String getResult(String urlStr, String content, String encoding) {
	URL url = null;
	HttpURLConnection connection = null;
	try {
	url = new URL(urlStr);
	// 新建连接实例
	connection = (HttpURLConnection) url.openConnection();
	// 设置连接超时时间，单位毫秒
	connection.setConnectTimeout(2000);
	// 设置读取数据超时时间，单位毫秒
	connection.setReadTimeout(33000);
	// 是否打开输出流 true|false
	connection.setDoOutput(true);
	// 是否打开输入流true|false
	connection.setDoInput(true);
	// 提交方法POST|GET
	connection.setRequestMethod("POST");
	// 是否缓存true|false
	connection.setUseCaches(false);
	// 打开连接端口
	connection.connect();
	// 打开输出流往对端服务器写数据
	DataOutputStream out = new DataOutputStream(connection.getOutputStream());
	// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
	out.writeBytes(content);
	// 刷新
	out.flush();
	out.close();
	// 往对端写完数据对端服务器返回数据
	BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
	// ,以BufferedReader流来读取
	StringBuffer buffer = new StringBuffer();
	String line = "";
	while ((line = reader.readLine()) != null) {
	buffer.append(line);
	}
	reader.close();
	return buffer.toString();
	} catch (IOException e) {
	e.printStackTrace();
	} finally {
	if (connection != null) {
	connection.disconnect();
	}
	}
	return null;
	}
}
