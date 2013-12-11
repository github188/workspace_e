package com.nari.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/****
 * 对形如{key=name,key1=2}的字符串进行处理的工具类 value 格式只能是a-zA-Z_这几种
 * 
 * @author huangxuan *
 ***/
public class ParseUtils {
	/**
	 * 
	 * 查找中某一个项的值
	 * 
	 * @param 要取值的字符串
	 * @param 要取值的key
	 * @return 返回所有找到的key的数组(也就是说input中可能有重复的key) 返回空数组说明没有匹配到任何的项 *
	 **/
	public static String[] get(String input, String key) {
		Pattern p = Pattern.compile("[\\{\\,\\s]" + key + "\\=([\\w\u2E80-\u9FFF\\-]+)");
		List<String> rs = new ArrayList<String>();
		Matcher m = p.matcher(input);
		while (m.find()) {
			rs.add(m.group(1));
		}
		return rs.toArray(new String[0]);
	}

	/*****
	 * 查找匹配到的项的第一个
	 * 
	 * @return 如果没有匹配项返回null *
	 ****/
	public static String getFirst(String input, String key) {
		Pattern p = Pattern.compile("[\\{\\,\\s]" + key + "\\=([\\w\u2E80-\u9FFF\\-]+)");
		Matcher m = p.matcher(input);
		if (m.find()) {
			return m.group(1);
		}
		return null;
		
	}

	/***
	 * 
	 * 更新某个字段的值，如果这个值在字符串中没有 不进行任何操作 *
	 **/
	public static String update(String input, String key, String value) {
		if(exists(input, key)){
			Pattern p = Pattern.compile("[\\{\\,\\s]" + key + "\\=([\\w\u2E80-\u9FFF\\-]+)");
			Matcher m = p.matcher(input);
			m.find();
			input=input.replace(m.group(), m.group().startsWith("{")?"{":","+key+"="+value);
		}
		return input;
	}

	/*****
	 * merge 有就更新，没有就增加 *
	 ****/
	public static String merge(String input, String key, String value) {
		return null;
	}

	/****
	 * 
	 * 增加某个key和value, 如果input中key不为空，直接返回原始字符串
	 * 
	 * *
	 ****/
	public static String add(String input, String key, String value) {
		if (exists(input, key)) {
			return input;
		}
		return addDirect(input, key, value);
	}

	/****
	 * 不管有没有直接插入 *
	 ***/
	public static String addDirect(String input, String key, String value) {
		StringBuilder sb = new StringBuilder(input);
		sb.replace(input.length() - 1, input.length(), "," + key + "=" + value)
				.append("}");
		return sb.toString();
	}

	/***
	 * 删除某个key,如果不存在，原样子返回 *
	 ***/
	public static String delete(String input, String key) {
		return null;
	}

	/******
	 * 判断有没有某个key *
	 ****/
	public static Boolean exists(String input, String key) {
		Pattern p = Pattern.compile("[\\{\\,\\s]" + key + "\\=([\\w\u2E80-\u9FFF\\-]+)");
		Matcher m = p.matcher(input);
		return m.find();
	}

	/***
	 * 
	 * 将input转化为一个HashMap,重复值自动去掉 *
	 **/
	public static Map convertToMap(String input) {
		Map m = new HashMap();
		Pattern p = Pattern.compile("([\\w\u2E80-\u9FFF]+)=([\\w\u2E80-\u9FFF\\-]+)");
		Matcher mc = p.matcher(input);
		while (mc.find()) {
			m.put(mc.group(1), mc.group(2));
		}
		return m;
	}

	public static void main(String[] args) {
		String input = "{a=b黄轩,a=bb,c=d,aa=bb,a=3,tt=34}";
		String[] strs = null;
		try {

			strs = ParseUtils.get(input, "a");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(ParseUtils.exists("{a=3黄轩,aa=b}", "aa"));
		System.out.println("test"+ParseUtils.getFirst(input, "a"));
		System.out.println(Arrays.toString(strs));
		System.out.println(ParseUtils.addDirect(input, "a", "hx"));
		List<String> l = new ArrayList();
		l.add("a");
		l.add("a1");
		l.add("a2");
		l.add("a3");
		for (String str : l) {
			str = "3";
		}
		System.out.println(ParseUtils.update(input, "aa", "黄轩"));
		System.out.println(l);
		System.out.println(ParseUtils.convertToMap(input));
	}
}
