package com.nari.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ParseString
 * 
 * @author zhangzhw
 * @describe 转换 String 到 short int long float double date
 */
public class ParseString {

	/**
	 * str2int
	 * 
	 * @param str
	 * @return string 转换为 int
	 */
	public static Integer str2int(String str) {
		if (str == null || "".equals(str))
			return null;
		return Integer.valueOf(str);
	}

	/**
	 * str2long
	 * 
	 * @param str
	 * @return string 转换为long
	 */
	public static Long str2long(String str) {
		if (str == null || "".equals(str))
			return null;
		return Long.valueOf(str);
	}

	/**
	 * str2float
	 * 
	 * @param str
	 * @return string 转换为 float （不使用）
	 */
	public static Float str2float(String str) {
		if (str == null || "".equals(str))
			return null;
		return Float.valueOf(str);
	}

	/**
	 * str2double
	 * 
	 * @param str
	 * @return string 转换为 double
	 */
	public static Double str2double(String str) {
		if (str == null || "".equals(str))
			return null;
		return Double.valueOf(str);
	}

	/**
	 * str2short
	 * 
	 * @param str
	 * @return string 转换为 short
	 */
	public static Short str2short(String str) {
		if (str == null || "".equals(str))
			return null;
		return Short.valueOf(str);
	}

	/**
	 * str2byte
	 * 
	 * @param str
	 * @return string 转换为 byte
	 */
	public static Byte str2byte(String str) {
		if (str == null || "".equals(str))
			return null;
		return Byte.valueOf(str);
	}

	/**
	 * str2Date
	 * 
	 * @param str
	 * @return string 转换为Date 默认格式
	 */
	public static Date str2Date(String str) {
		if (str == null || "".equals(str))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date date = sdf.parse(str);
			return date;
		} catch (ParseException pe) {
			return null;
		}

	}

	/**
	 * str2Date
	 * 
	 * @param str
	 * @param format
	 *            格式 默认 yyyy-MM-dd
	 * @return string 转换为Date
	 */
	public static Date str2Date(String str, String format) {
		if (str == null || "".equals(str))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(str);
			return date;
		} catch (ParseException pe) {
			return null;
		}
	}

	/**
	 * str2cut
	 * 
	 * @param str
	 * @return 以空格为分隔符取字符串第一段
	 */
	public static String str2cut(String str) {
		if (str == null || "".equals(str))
			return null;

		if (str.indexOf("-") > 0) {
			String[] s1 = str.split("-");
			if (s1.length > 0)
				return s1[0];
		} else {
			String[] s = str.split(" ");
			if (s.length >0)
				return s[0];
		}

		return null;
	}

}
