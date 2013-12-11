package com.nari.util;

import java.util.Map;
import java.util.Set;

/**
 * SQL相关处理类
 * 
 */
public class SQLutil {

	/**
	 * 生成insert sql语句
	 * 
	 * @param destinationTabel
	 * @param columnNames
	 * @param primaryKeyName
	 * @return insertSql
	 */
	public static String genInsertSql(String destinationTabel,
			Set<String> columnNames, String primaryKeyName) {
		StringBuffer buf = new StringBuffer();
		buf.append("insert into ").append(destinationTabel).append("(");
		for (String col : columnNames) {
			if (col.equalsIgnoreCase(primaryKeyName)) {
				primaryKeyName = col;
			} else {
				buf.append(col).append(",");
			}
		}
		buf.append(primaryKeyName).append(",");
		buf.setCharAt(buf.length() - 1, ')');
		buf.append(" values (");
		final int colLength = columnNames.size();
		for (int i = 0; i < colLength; i++) {
			buf.append("?,");
		}
		buf.setCharAt(buf.length() - 1, ')');
		return buf.toString();
	}
	
	/**
	 * 生成updata sql语句
	 * 
	 * @param destinationTabel
	 * @param columnNames
	 * @param primaryKeyName
	 * @return updata sql
	 */
	public static String genUpdateSql(String destinationTabel,
			Set<String> columnNames, String primaryKeyName) {
		StringBuffer buf = new StringBuffer();
		buf.append("update ").append(destinationTabel).append(" set ");
		for (String col : columnNames) {
			if (!col.equalsIgnoreCase(primaryKeyName)) {
				buf.append(col).append("=?,");
			}
		}
		buf.setCharAt(buf.length() - 1, ' ');
		buf.append("where ").append(primaryKeyName).append("=?");
		return buf.toString();
	}

	/**
	 * 生成更新的一行记录数组
	 * 
	 * @param map
	 * @param columnNames
	 * @param primaryKeyName
	 * @return 记录数组
	 */
	public static Object[] genUpdateData(Map<?,?> map, Set<String> columnNames,
			String primaryKeyName) {
		int index = 0;
		Object[] data = new Object[columnNames.size()];
		for (String col : columnNames) {
			if (col.equalsIgnoreCase(primaryKeyName)) {
				primaryKeyName = col;
			} else {
				data[index++] = map.get(col);
			}
		}
		// 将主键放入最后
		data[index] = map.get(primaryKeyName); 
		return data;
	}
	
}