package com.nari.util;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.property.Getter;
import org.hibernate.util.ReflectHelper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nari.runman.dutylog.LUserLogEntry;

/*****
 * 此实现找到插入的顺序的关系是基于一个特殊的算法<br>
 * 此算法中会额外的进行一次数据库的查询以确定插入字段的顺序<br>
 * 
 * @author huangxuan 此类能自动生成插入语句<br>
 *         1为一个简单实现，兼容ejb注释 2没有考虑很复杂的情况，只能按照一定的规律来写bean 3 只支持oracle 4未考虑多id插入
 ******/
public class NamedCreateInsert extends NamedParameterJdbcDaoSupport {
	private String sql = "";
	private String columnId;
	private String tableName;
	private String rowId;

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public Map getMs() {
		return ms;
	}

	public void setMs(Map ms) {
		this.ms = ms;
	}

	private Map ms = new HashMap();

	public String getColumnId() {
		return columnId;
	}

	private boolean nullIsDefault = true;

	/**** 是否将null处理为default ******/
	public boolean isNullIsDefault() {
		return nullIsDefault;
	}

	public void setNullIsDefault(boolean nullIsDefault) {
		this.nullIsDefault = nullIsDefault;
	}

	private Object id;

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public NamedCreateInsert() {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(ServletActionContext
						.getServletContext());
		// this
		this.setDataSource((DataSource) wac.getBean("dataSource"));
	}

	/****
	 * 指定一个datasource *
	 **/
	public NamedCreateInsert(String dataSourceBean) {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(ServletActionContext
						.getServletContext());
		this.setDataSource((DataSource) wac.getBean(dataSourceBean));
	}

	private void initConfig() {
		setRowId(null);
		this.tableName = null;
		ms.clear();
		setSql("");
	}

	/*****
	 * 通过注解来自动的生产语句和 参数列表 *
	 ******/
	@SuppressWarnings("unchecked")
	public void buildSql(Object obj) {
		initConfig();
		Class cls = obj.getClass();
		int len = 0;// 记录生成的数组的大小
		StringBuilder sb = new StringBuilder("insert into ");
		StringBuilder sp = new StringBuilder("(");
		// 通过类来确定插入的表
		Table tb = (Table) cls.getAnnotation(Table.class);
		if (tb == null) {
			throw new RuntimeException("请输入表名");
		}
		String tbName = tb.name();
		sb.append(tbName + "(");
		Field[] fs = cls.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			Column c = f.getAnnotation(Column.class);
			if (c == null) {
				continue;
			}
			String columnName = c.name();
			Id id = f.getAnnotation(Id.class);
			GeneratedValue gv = f.getAnnotation(GeneratedValue.class);
			if (id != null && gv != null) {
				// 得到id列
				columnId = columnName;
				String gen = gv.generator();
				// 在这里可以加入回调来灵活的生成主键
				if (gen == null || gen.trim().equals("")) {
					throw new RuntimeException("请填写序列");
				}
				sb.append(columnName + ",");
				sp.append(gen + ".nextval,");
				continue;
			}
			if (columnName.trim().equals("")) {
				continue;
			}
			Getter g = null;
			try {
				g = ReflectHelper.getGetter(cls, f.getName());
			} catch (Exception e) {
				continue;
			}
			Object value = g.get(obj);
			if (value == null && isNullIsDefault()) {
				sb.append(columnName + ",");
				sp.append("default,");
			} else if (value == null && !isNullIsDefault()) {
				continue;
			} else {
				sb.append(columnName + ",");
				sp.append(":" + f.getName() + ",");
				if (f.getType().equals(Date.class)) {
					Date d = (Date) value;
					Timestamp t = new Timestamp(d.getTime());
					ms.put(f.getName(), t);
				} else {
					ms.put(f.getName(), value);
				}
			}
		}
		sb.replace(sb.length() - 1, sb.length(), ")");
		sp.replace(sp.length() - 1, sp.length(), ")");
		this.sql = sb.append(" values").append(sp).toString();
	}

	public int save(Object obj) {
		buildSql(obj);
		SqlParameterSource sm = new MapSqlParameterSource(ms);
		KeyHolder kh = new GeneratedKeyHolder();
		int i = getNamedParameterJdbcTemplate().update(sql, sm, kh);
		id = kh.getKeys();
		return i;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	/*** 通过一个对象来得到一个map ***/
	@SuppressWarnings("unchecked")
	public static Map mapFromBean(Object bean) {
		Map hm = new HashMap();
		Class<?> c = bean.getClass();
		Field[] fs = c.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			Getter g = null;
			try {
				g = ReflectHelper.getGetter(c, f.getName());
			} catch (Exception e) {
				continue;
			}
			Object value=g.get(bean);
			if(value==null){
				continue;
			}
			hm.put(f.getName(), g.get(bean));
		}
		return hm;
	}

	public static void main(String[] args) {
		LUserLogEntry l = new LUserLogEntry();
		l.setEmpNo("test");
		l.setLogId(9);
		l.setCounterpartNo("bbb");
		System.out.println(Introspector.decapitalize("Abc"));
		String abc="abcd";
		Map mm=NamedCreateInsert.mapFromBean(l);
		System.out.println(mm);
		char[] cs = abc.toCharArray();
		cs[0]=Character.toUpperCase(cs[0]);
		System.out.println(new String(cs));
		
		NamedCreateInsert c = new NamedCreateInsert();
		c.buildSql(l);
		System.out.println(c.getSql());
	}
}
