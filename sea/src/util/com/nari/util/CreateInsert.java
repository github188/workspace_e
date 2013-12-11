package com.nari.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.sql.DataSource;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import oracle.sql.ROWID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.property.Getter;
import org.hibernate.util.ReflectHelper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.runman.dutylog.DefaultGenerateKey;
import com.nari.runman.dutylog.IGenerateKey;
import com.nari.runman.dutylog.LUserLogEntry;

/*****
 *会自动把java.util.Date转化为java.sql.Date的格式
 *未对文件上传，流文件的保存进行支持。
 *通过generateKey来定义生成主键的方式<br>
 *initId 是否将生成的主键返回。如果为真，原来的<br>
 *save(obj)中的obj相应的主键字段会被填充.<br>
 *对在主表中插入一条信息，然后再从表中加入n条信息<br>
 *这样的操作，只有对这个类进行适当的封装就可以完成<br>
 * NamedCreateInsert则是一套根据命名参数的插入<br>
 *20100422加入一条规则，注解GeneratedValue在不含id的列中<br>
 表示直接插入generator的值，不管当初对象的值,作用?<br>
 *如你想插入sysdate,在字段上面加入注解GeneratedValue generator="sysdate"<br>
 *<br> 解决一个对Date类型插入数据的一个bug(时分秒截取)<br>
 * @author huangxuan 此类能自动生成插入语句<br>
 *         1为一个简单实现，使用ejb注释 2没有考虑很复杂的情况，只能按照一定的规律来写bean 3 只支持oracle 4未考虑多id插入
 ******/
/**
 * @author huangxuan
 * 
 */

public class CreateInsert extends JdbcBaseDAOImpl {
	private String sql = "";
	private String columnId;
	/**
	 * 
	 */
	private Object id;

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	// 是否对id进行初始化
	private boolean initId = false;
	private String tableName;
	@SuppressWarnings("unchecked")
	private List args = new ArrayList();

	public String getColumnId() {
		return columnId;
	}

	private Field fid;
	private boolean nullIsDefault = true;
	/****
	 * 
	 * 设置主键生成策略，默认有一个实现 *
	 ***/
	private IGenerateKey generateKey = new DefaultGenerateKey();

	public boolean isInitId() {
		return initId;
	}

	public void setInitId(boolean initId) {
		this.initId = initId;
	}

	/**** 是否将null处理为default ******/
	public boolean isNullIsDefault() {
		return nullIsDefault;
	}

	public void setNullIsDefault(boolean nullIsDefault) {
		this.nullIsDefault = nullIsDefault;
	}

	// 一个id的列表，在oracle只表示的是ROWID=
	@SuppressWarnings("unchecked")
	private Map rowId;

	@SuppressWarnings("unchecked")
	public Map getRowId() {
		return rowId;
	}

	public void setRowId(Map rowId) {
		this.rowId = rowId;
	}

	public CreateInsert(boolean initId) {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(ServletActionContext
						.getServletContext());
		// this
		this.setDataSource((DataSource) wac.getBean("dataSource"));
		this.initId = initId;
	}

	public CreateInsert() {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(ServletActionContext
						.getServletContext());
		// this
		this.setDataSource((DataSource) wac.getBean("dataSource"));
	}

	/****
	 * 指定一个datasource *
	 **/
	public CreateInsert(String dataSourceBean) {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(ServletActionContext
						.getServletContext());
		this.setDataSource((DataSource) wac.getBean(dataSourceBean));
	}

	// 初始化状态
	private void initConfig() {
		setRowId(null);
		this.tableName = null;
		args.clear();
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
		this.tableName = tbName;
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
			if (id != null) {
				// 得到id列
				fid = f;
				columnId = columnName;

				if (null != generateKey) {
					sb.append(columnName + ",");
					String getStr = generateKey.createKey(f, args);
					if (getStr == null) {
						if (f.isAnnotationPresent(GeneratedValue.class)) {
							GeneratedValue gv = f
									.getAnnotation(GeneratedValue.class);
							String idSql = "select " + gv.generator()
									+ ".nextval from dual";
							try {
								Object insertId = getJdbcTemplate()
										.queryForLong(idSql);
								setId(insertId);
								sp.append("?,");
								args.add(insertId);
								continue;
							} catch (Exception e) {
								throw new RuntimeException("一般为generator中的序列在数据库中不存在");
							}
						} else {
							throw new RuntimeException("没有含有GeneratedValue注解");
						}
						
						// throw new RuntimeException("生成主键键值字符串不能为空");
					}
					sp.append(getStr);
					sp.append(",");
					continue;
				}
			}
			if (columnName.trim().equals("")) {
				continue;
			}
			Getter g = null;
			// 20100422加入一条规则，注解GeneratedValue在不含id的列中
			// 表示直接插入generator的值，不管当初对象的值
			if (f.isAnnotationPresent(GeneratedValue.class)
					&& !f.isAnnotationPresent(Id.class)) {
				sb.append(columnName + ",");
				GeneratedValue gv = f.getAnnotation(GeneratedValue.class);
				sp.append(gv.generator() + ",");
				continue;
			}
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
				sp.append("?,");
				if (f.getType().equals(Date.class)) {
					Date d = (Date) value;
					Timestamp t = new Timestamp(d.getTime());
					args.add(t);
					// args.add(new java.sql.Date(((Date) value).getTime()));
				} else {
					args.add(value);
				}
			}
		}
		sb.replace(sb.length() - 1, sb.length(), ")");
		sp.replace(sp.length() - 1, sp.length(), ")");
		this.sql = sb.append(" values").append(sp).toString();
	}

	public IGenerateKey getGenerateKey() {
		return generateKey;
	}

	public void setGenerateKey(IGenerateKey generateKey) {
		this.generateKey = generateKey;
	}

	public List getArgs() {
		return args;
	}

	public void setArgs(List args) {
		this.args = args;
	}

	public int save(Object obj) {
		buildSql(obj);
		final String sql = getSql();
		GeneratedKeyHolder gen = new GeneratedKeyHolder();
		int j = 0;
		j = getJdbcTemplate().update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pre) throws SQLException {
				List list = getArgs();
				for (int i = 0; i < list.size(); i++) {
					Object l = list.get(i);
					pre.setObject(i + 1, l);
				}
			}
		});
		// j = getJdbcTemplate().update(new PreparedStatementCreator() {
		//
		// @Override
		// public PreparedStatement createPreparedStatement(Connection conn)
		// throws SQLException {
		// PreparedStatement pre = conn.prepareStatement(sql,
		// Statement.RETURN_GENERATED_KEYS);
		// List list = getArgs();
		// for (int i = 0; i < list.size(); i++) {
		// Object l = list.get(i);
		// pre.setObject(i + 1, l);
		// }
		// return pre;
		// }
		// }, gen);
		// Simplejd
		// setRowId(gen.getKeys());
		if (this.initId && fid != null) {
			// if (fid.isAnnotationPresent(GeneratedValue.class)) {
			// GeneratedValue g = fid.getAnnotation(GeneratedValue.class);
			// // String
			// String findIdSql = "select " + columnId + " from " + tableName
			// + " where rowId=?";
			// Object key = getJdbcTemplate().execute(findIdSql,
			// new PreparedStatementCallback() {
			//
			// public Object doInPreparedStatement(
			// PreparedStatement pre) throws SQLException,
			// DataAccessException {
			// pre.setObject(1, "");
			// ResultSet rs = pre.executeQuery();
			//
			// if (rs.next()) {
			// return rs.getObject(1);
			// }
			// return null;
			// }
			// });
			// }
			// final ROWID rowId = (ROWID) gen.getKeys().values().toArray()[0];
			// String findIdSql = "select " + columnId + " from " + tableName
			// + " where rowId=?";
			// Object key = getJdbcTemplate().execute(findIdSql,
			// new PreparedStatementCallback() {
			//
			// public Object doInPreparedStatement(
			// PreparedStatement pre) throws SQLException,
			// DataAccessException {
			// pre.setObject(1, rowId.stringValue());
			// ResultSet rs = pre.executeQuery();
			//
			// if (rs.next()) {
			// return rs.getObject(1);
			// }
			// return null;
			// }
			// });
			// setId(key);
			try {
				BeanUtils.setProperty(obj, fid.getName(), ConvertUtils.convert(
						id.toString(), fid.getClass()));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return j;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	/*******/
	public static void main(String[] args) {
		System.out.println();
		LUserLogEntry l = new LUserLogEntry();
		l.setEmpNo("test");
		l.setLogId(9);
		FastClass f = FastClass.create(LUserLogEntry.class);
		FastMethod fm = f.getMethod("getLogId", new Class[] {});
		System.out.println(ConvertUtils.convert("11", int.class));
		Enhancer en = new Enhancer();
		en.setSuperclass(LUserLogEntry.class);
		en.setCallback(new MethodInterceptor() {

			public Object intercept(Object o, Method m, Object[] args,
					MethodProxy proxy) throws Throwable {
				System.out.println(m.getName());
				proxy.invokeSuper(o, args);
				return "靠";
			}
		});

		LUserLogEntry ll = (LUserLogEntry) en.create();
		System.out.println(ll.getOrgNo());
		l.setCounterpartNo("bbb");
		CreateInsert c = new CreateInsert();
		c.buildSql(l);
		System.out.println(c.getSql());
	}
}
