package com.nari.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class StringUtil {

	public static final boolean isEmptyString(String s) {
		return  s == null || s.trim().equals("") || "NULL".equalsIgnoreCase(s);
	}	
	/**
	 * 解析字符串，将相同的去掉
	 * @param cs
	 * 字符串
	 * @param param
	 * 间隔所使用的符号
	 * @return
	 */
	public static String getMerger(String cs, String param) {
		String clr = null;
		Set<String> st = new HashSet<String>();
		String resClr = cs == null ? "" : cs;
		String[] resClrs = resClr.split(param);
		for (int i = 0; i < resClrs.length; i++) {
			if (resClrs[i] != null && !resClrs[i].equals(""))
				st.add(resClrs[i]);// 放入HashSet中
		}
		Iterator<String> it = st.iterator();
		while (it.hasNext()) {
			String rs = (String) it.next();
			clr = (clr == null || clr.equals("null")) ? "" : (clr + param);
			clr = clr + rs;
		}
		return clr;
	}	
	/**
	 * 将数字转换成字符串
	 * @param value
	 * 想要转换的数字
	 * @return
	 */
	public static String convertNumberToString(double value) {
		DecimalFormat formatter = new DecimalFormat("0.000#");
		formatter.setGroupingUsed(false);
		String result = formatter.format(value);
		return result;
	}	
	public static int indexOfArray(String[] strArray, String compString) {
		int result = -1;
		for (int i = 0; strArray != null && i < strArray.length; i++) {
			if (!isEmptyString(strArray[i]) && strArray[i].equals(compString)) {
				result = i;
				break;
			}
		}
		return result;
	}	
	/**
	 * 将Map转换成String
	 * @param params
	 * @return
	 */
	public static String convertMapToString(Map<?,?> params) {
		StringBuffer result = new StringBuffer();
		if(params == null) return "";
		Iterator<?> it = params.keySet().iterator();
		while(it.hasNext()) {
			String key = (String)it.next();
			String value = (String)params.get(key);
			result.append(key).append("{,}").append(value).append("{;}");
		}
		return result.toString();
	}	
	public static Map<String,String> convertStringToMap(String params) {
		Map<String,String> result = new HashMap<String,String>();
		String [] rows = params.split("\\{;\\}");
		if(rows != null) {
			for(int i=0;i<rows.length;i++) {
				String [] cols = rows[i].split("\\{,\\}");
				if(cols != null && cols.length > 1) {
					result.put(cols[0],cols[1]);
				}
			}
		}
		return result;
	}	
	public static boolean isDigital(String toCompareString){
		if(toCompareString == null || toCompareString.equals("")) {
			return false;
		}
		boolean isDigit = true;
		for (int i = 0; i < toCompareString.length(); i++) {
			if (!Character.isDigit(toCompareString.charAt(i))) {
				isDigit = false;
				break;
			}
		}
		return isDigit;
	}
	/**
	 * 将字符串转成gb2312的encode编码格式
	 * @param str
	 * @return s
	 * @throws Exception
	 */
	public static String setGBKUrl(String str) throws Exception {
		String s = "";
		if (str != null && !str.equals("")) {
			s = java.net.URLEncoder.encode(str, "gb2312");
		}
		return s;
	}
	/**
	 * 将字符串转成utf-8的encode编码格式
	 * @param str
	 * @return s
	 * @throws Exception
	 */
	public static String setUtf8Url(String str) throws Exception {
		String s = "";
		if (str != null && !str.equals("")) {
			s = java.net.URLEncoder.encode(str, "utf-8");
		}
		return s;
	}
	/**
	 * 将字符串转成GBK编码格式
	 * @param str
	 * @return 
	 * @throws Exception
	 */
	public static String convertStrToGBK(String s) {
		try {
			return new String(s.getBytes("iso-8859-1"), "GBK");
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 将字符串转成iso-8859-1编码格式
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String convertStrToISO(String s) {
		try {
			return new String(s.getBytes("GBK"), "iso-8859-1");
		} catch (Exception e) {
			return null;
		}

	}
	/**
	 * 将字符串转成utf-8编码格式
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String toUtf8Code(String input) {
		try {
			if (null != input && input.indexOf("null") < 0) {
				byte[] bytes = input.getBytes("ISO8859_1");
				input = new String(bytes, "utf-8");
			} else {
				input = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return input;
	}
	/**
	 * 将对象转换成字符串，如果对象为null,则返回""
	 * @param o
	 * @return
	 */
	public static String removeNull(Object o) {
		return removeNull(o, "");
	}
	/**
	 * 返回页面字符串对象，如果对象为空或者是"",返回&nbsp;
	 * @param o
	 * @return
	 */
	public static String removeNullInHtml(Object o) {
		if (o == null || o.toString().trim().equals("")) {
			return "&nbsp;";
		}
		return o.toString().trim();
	}
	/**
	 * 预置的返回判空返回对象，如果对象为空，返回s
	 * @param o
	 * @param s
	 * @return
	 */
	public static String removeNull(Object o, String s) {
		if (o == null) {
			return s;
		}
		return o.toString().trim();
	}
	/**
	 * 消除字符串中的html代码
	 * @param str
	 * @return
	 */
	public static String htmcode(Object str) {
		String code = str != null ? str.toString() : "";
		try {
			while (code.indexOf("<") != -1 && code.indexOf(">") != -1) {
				StringBuffer sb = new StringBuffer(code);
				code = sb.replace(code.indexOf("<"), code.indexOf(">") + 1, "")
						.toString();
			}
			return code;
		} catch (Exception e) {
			System.out.print("htmcode.err");
			return code;
		}
	}	
	/**
	 * 将以某个字符隔开的字符串拆成n个对象数组
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static Object convertStringSplit(String str1, String str2) {
		if ("".equals(StringUtil.removeNull(str1))) {
			return new String[] { "" };
		} else {
			if (str1.length() - 1 == str1.lastIndexOf(str2)) {
				str1 = str1.substring(0, str1.length() - 1);
			}
			return str1.split(str2);
		}
	}
    /**
	 * 将字符串格式转化为int型
     * @param str1
     * @return
     */
	public static int convertStringToInt(String str1) {
		if ("".equals(StringUtil.removeNull(str1))) {
			return 0;
		} else {
			return Integer.parseInt(str1);
		}
	}
	/**
	 * 将字符串格式转化为double型
	 * @return
	 */
	public static double convertStringToDouble(String str1) {
		if ("".equals(StringUtil.removeNull(str1))) {
			return 0;
		} else {
			return Double.parseDouble(str1);
		}
	}
	/**
	 * 获得request中指定名称的参数值,默认utf-8编码的
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name) {
		return getParameter(request, name, "utf-8");
	}
	/**
	 * 获得request中指定名称的参数值
	 * @param request
	 * @param name
	 * @param enc
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name,
			String enc) {
		return getParameter(request, name, enc, "GET".equals(request.getMethod()));
	}
	/**
	 * 获得request中指定名称的参数值,默认utf-8编码的
	 * @param request
	 * @param name
	 * @param isGetMethod
	 * 指定是否是get方法
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name,
			boolean isGetMethod) {
		return getParameter(request, name, "utf-8", isGetMethod);
	}
	/**
	 * 获得request中指定名称的参数值
	 * @param request
	 * @param name
	 * @param isGetMethod
	 * 指定是否是get方法
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name,
			String enc, boolean isGetMethod) {
		if (isGetMethod) {
			try {
				return new String(StringUtil.removeNull(request.getParameter(name)).getBytes("iso8859-1"), enc);
			} catch (UnsupportedEncodingException ignored) {
				return "";
			}
		} else {
			return StringUtil.removeNull(request.getParameter(name));
		}
	}	
	/**
	 * 截取字符串
	 * @param str
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public static String subStr(String str,int beginIndex,int endIndex){
		if(str != null){
			if(beginIndex <= str.length() && endIndex <= str.length()){
				return str.substring(beginIndex,endIndex);
			}else{
				return str;
			}
		}else{
			return "";
		}
	}
}