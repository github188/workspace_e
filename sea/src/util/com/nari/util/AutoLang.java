package com.nari.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/*****
 * 加入一些常用的与拼sql有关的工具
 * 
 * @author huangxuan *
 ***/
public class AutoLang {

	/****
	 * 自动的将一个collection对象转化为in对象，如果size太大，自动处理 (in(?,?,?) or in(?,?,?)) *
	 **/
	public static CharSequence autoIn(String field, Collection c, int size) {
		Iterator it = c.iterator();
		StringBuilder sb = new StringBuilder(field + " in(");
		int index = 0;
		while (it.hasNext()) {
			index++;
			it.next();
			if (index < size) {
				if (it.hasNext()) {
					sb.append("?,");
				} else {
					sb.append("?)");
				}
			} else {
				if (it.hasNext()) {

					sb.append("?) or " + field + " in(");
				} else {
					sb.append("?)");
				}
				index = 0;
			}
		}

		return sb;
	}

	/**
	 * 通过一个obj对象生成一个map 对于基本数据类型的属性请使用封装类
	 * 
	 * @param obj
	 * @param empty
	 *            是否忽略空值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map fromBean(Object obj, boolean empty) {
		Class<? extends Object> cls = obj.getClass();
		Field[] fs = cls.getDeclaredFields();
		Map mp = new HashMap();
		try {
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				Method g = getter(f.getName(), cls);
				Object value = g.invoke(obj, null);
				if (empty && (null == value || value.toString().equals(""))) {
					continue;
				}
				mp.put(f.getName(), value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mp;
	}

	public static Method getter(String name, Class cls) {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(name, cls);
			return pd.getReadMethod();
		} catch (Exception e) {
			return null;
		}
	}

	public static Method setter(String name, Class cls) {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(name, cls);
			return pd.getWriteMethod();
		} catch (Exception e) {
			return null;
		}
	}

	// 一个获得整个系统的web容器内的数据源的方法
	public static DataSource getDataSource(String... args) {

		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(ServletActionContext
						.getServletContext());
		if (args.length == 0) {
			return (DataSource) wac.getBean("dataSource");
		}
		for (int i = 0; i < args.length; i++) {
			Object result = wac.getBean(args[i]);
			if (null != result) {
				return (DataSource) result;
			}
		}
		throw new RuntimeException("没有检测到数据源");
	}

	public static void main(String[] args) {
		getDataSource();
	}
}
