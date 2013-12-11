package com.nari.baseapp.planpowerconsume.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.springframework.jdbc.core.RowMapper;

/***
 * 如果返回的结果是map对象，可以使用这个类来处理结果集的映射 支持通过简单的类似json格式来定义的key和value
 * 形式如"{key1=value1,key2=value2,key3=value3}"将被转化为一个map
 * 形式如{key1,key2=value2,key3} key1和key3等效于key1=key1
 * 形式"[key1,key2,key3,key4]"将被转化为一个String []对象
 * 不要使用引号.不要使用复杂格式，因为实现里面并没有对格式进行更正处理 对resultMeta对象操作可以实现类似"[*]" 转化所有字段的操作
 * 不然认为是json格式的 仅仅只是类json格式 [*]的四种种写法 [*] 按照数据库中的默认方式转化名称 ， 如AbcDe 转化为AbcDe <br>
 * [*,l] AbcDe转化为abcde<br>
 * [*,u] AbcDe转化为ABCDE<br>
 * [*,c] 转化为骆驼命名法格式 如abc_cda abcCda<br>
 * 
 * @author 黄轩 *
 **/
public class MapResultMapper implements RowMapper {
	public MapResultMapper() {
	}

	private Map addMap = new HashMap();
	private Collection<String> c;
	private String[] keys;

	/***
	 * 通过此构造方法对map进行扩展，在以后可以对map进行扩展 增加需要添加的字段的作用,
	 * 
	 * @param map
	 *            记录key和value 通过key的相应的value从ResultSet中取值
	 * **/
	public MapResultMapper(Map map) {
		this.addMap = map;
	}

	/****
	 * 如果确认key和ResultSet取值的key相同 ，使用collection对象扩展 *
	 **/
	public MapResultMapper(Collection<String> c) {
		this.c = c;
	}

	/***
	 * 
	 * 如果确认key和ResultSet取值的key相同 ，使用数组对象扩展 *
	 ***/
	public MapResultMapper(String[] keys) {
		this.keys = keys;
	}

	/***
	 * 如果返回的结果是map对象，可以使用这个类来处理结果集的映射 支持通过简单的类似json格式来定义的key和value
	 * 形式如"{key1=value1,key2=value2,key3=value3}"将被转化为一个map
	 * 形式如{key1,key2=value2,key3} key1和key3等效于key1=key1
	 * 形式"[key1,key2,key3,key4]"将被转化为一个String []对象
	 * 不要使用引号.不要使用复杂格式，因为实现里面并没有对格式进行更正处理 对resultMeta对象操作可以实现类似"[*]" 转化所有字段的操作
	 * 不然认为是json格式的 仅仅只是类json格式 [*]的四种种写法 [*] 按照数据库中的默认方式转化名称 ， 如AbcDe 转化为AbcDe <br>
	 * [*,l] AbcDe转化为abcde<br>
	 * [*,u] AbcDe转化为ABCDE<br>
	 * [*,c] 转化为骆驼命名法格式 如abc_cda abcCda<br>
	 * 
	 * @author 黄轩 *
	 **/
	public MapResultMapper(String simpleStr) {
		parseSimpleJson(simpleStr);
	}

	@SuppressWarnings("unchecked")
	public Object mapRow(ResultSet rs, int i) throws SQLException {
		try {
			Map m = new HashMap();
			if (keys != null && keys[0].equals("*")) {
				if (keys.length >= 2) {
					String flat = keys[1];
					ResultSetMetaData meta = rs.getMetaData();
					int len = meta.getColumnCount();
					for (int i1 = 1; i1 <= len; i1++) {
						String key = meta.getColumnLabel(i1);
						if ("l".equalsIgnoreCase(flat)) {
							key = key.toLowerCase();
						} else if ("u".equalsIgnoreCase(flat)) {
							key = key.toUpperCase();
						} else if ("c".equalsIgnoreCase(flat)) {
							key = key.toLowerCase();
							key = parseC(key);
						}
						if (rs.getObject(i1) instanceof Date) {
							//SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							Calendar c=Calendar.getInstance();
							c.setTimeInMillis(rs.getTimestamp(i1).getTime());
							m.put(key,c.getTime() );
						} else {
							m.put(key, rs.getObject(i1));
						}
					}
				}
				// parseStar(rs, m,lower);
				return m;
			}
			// 必须的参数
			if (addMap != null && addMap.size() != 0) {
				Iterator it = addMap.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next().toString();
					m.put(key, rs.getObject(addMap.get(key).toString()));
				}
				return m;
			}
			if (c != null && c.size() == 0) {
				Iterator<String> it = c.iterator();
				while (it.hasNext()) {
					String str = it.next();
					m.put(str, rs.getObject(str));
				}
				return m;
			}
			if (keys != null && keys.length != 0) {
				for (int j = 0; j < keys.length; j++) {
					m.put(keys[j], rs.getObject(keys[j]));
				}
				return m;
			}

			return m;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void parseSimpleJson(String input) {
		if (input.indexOf("{") >= 0) {
			input = clearAll(input);
			String[] inputArr = input.split(",");
			for (int i = 0; i < inputArr.length; i++) {
				String s = inputArr[i];
				String[] b = s.split("=");
				if (b.length == 1) {
					addMap.put(b[0], b[0]);
				} else if (b.length == 2) {
					addMap.put(b[0], b[1]);
				} else {
					throw new RuntimeException("格式错误,请参考注释");
				}
			}
		} else if (input.indexOf("[") >= 0) {
			input = clearAll(input);
			keys = input.split(",");
		} else {
			throw new RuntimeException("输入的字符串格式不合法，请参考注释");
		}
	}

	/***
	 * 清除掉所有的无用字符串
	 * **/
	private String clearAll(String input) {
		Pattern p = Pattern.compile("[\\[\\]\\{\\}\\s]");
		return p.matcher(input).replaceAll("");
	}

	// 判断一个字符串是不是只含有字母
	public boolean onlyZimu(String input) {
		Pattern p = Pattern.compile("^[a-zA-Z]+$");
		return p.matcher(input).find();
	}

	// 对[*]进行的处理
	// private void parseStar(ResultSet rs, Map map, Boolean lower)
	// throws SQLException {
	// ResultSetMetaData meta = rs.getMetaData();
	// int len = meta.getColumnCount();
	// for (int i = 1; i <= len; i++) {
	// String key = meta.getColumnLabel(i);
	// if (lower != null) {
	// if (lower) {
	// key = key.toLowerCase();
	// } else {
	// key = key.toUpperCase();
	// }
	// }
	// map.put(key, rs.getObject(i));
	// }
	// }
	/**
	 * 将一个字符串转换为满足骆驼命名法的格式 *
	 **/
	private String parseC(String input) {
		Assert.assertNotNull(input);
		input = input.replaceAll("_", "-");
		StringBuilder sb = new StringBuilder(input);
		Pattern p = Pattern.compile("\\b[a-zA-Z0-9\u4E00-\u9FFF]");
		Matcher m = p.matcher(sb);
		// 不包含第一个
		m.find();
		while (m.find()) {
			sb.replace(m.start(), m.end(), m.group().toUpperCase());
		}
		return sb.toString().replaceAll("[^\u4E00-\u9FFF&&[\\W]]", "");
	}

	public static void main(String[] args) {
		// test
		System.out.println("1".split("=").length);
		// MapResultMapper m = new MapResultMapper("[a,b,c,d]");
		MapResultMapper m = new MapResultMapper("[*]");
		System.out.println(Arrays.toString(m.keys));
		// 骆驼命名法测试
		String str = "one-two-黄three_four";
		Pattern p = Pattern.compile("\\b[a-zA-Z0-9\u4E00-\u9FFF]");
		StringBuilder sb = new StringBuilder(str);
		Matcher m1 = p.matcher(sb);
		while (m1.find()) {
			sb.replace(m1.start(), m1.end(), m1.group().toUpperCase());
			System.out.println(m1.group());
		}
		System.out.println(sb);
		System.out.println(m.parseC("adfd  dfaf -a黄dfaf_dfaf@er") + "  :::");
	}
}
