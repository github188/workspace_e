package com.nari.util;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.property.Getter;
import org.hibernate.util.ReflectHelper;

import com.nari.orderlypower.TTmnlCtrlParam;
import com.nari.util.exception.DBAccessException;

import oracle.jdbc.OraclePreparedStatement;

/**
 *对oracle jdbc中的命名参数特性进行封装
 * 
 * @author huangxuan *
 **/
public class NamedParamUtils {
	/***
	 * 通过一个map对oraclepreparedment进行设置
	 * 
	 * @throws SQLException
	 *             *
	 **/
	public static void namedSetMap(Map map, OraclePreparedStatement op) {
		Iterator<String> it = map.keySet().iterator();
		try {
			while (it.hasNext()) {
				String key = it.next();
				op.setObjectAtName(key, map.get(key));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 通过一个对象对oraclepreparedment进行设置
	 * 
	 * @throws SQLException
	 *             *
	 **/
	public static void namedSetObject(Object obj, OraclePreparedStatement op) {
		// 先将对象转化为map形式
		Map map = convertToMap(obj);
	
		try {
			namedSetMap(map, op);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	};

	/****
	 * 将对象转化为map的形式 *
	 ***/
	public static Map convertToMap(Object obj) {
		Map map = new HashMap();
		// 得到对象中所有的字段
		Field[] fs = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
		//	fs[i].setAccessible(true);
			String fieldName = fs[i].getName();
			Getter getter = null;
			// 得到他的get方法
			try {
				getter = ReflectHelper.getGetter(obj.getClass(), fieldName);
			} catch (Exception e) {
				getter = null;
			}
			if (null != getter) {
				map.put(fieldName, getter.get(obj));
			}
		}
		// ReflectUtils.
		return map;
	}
	public static void main(String[] args) {
		System.out.println(NamedParamUtils.convertToMap(new TTmnlCtrlParam()));
	}
}
