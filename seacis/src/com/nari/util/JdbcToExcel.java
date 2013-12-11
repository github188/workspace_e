package com.nari.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

@SuppressWarnings("unchecked")
public class JdbcToExcel{
	
	/**
	 * 将jdbc查询的结果集封装到List中
	 * @param rs
	 * @return List
	 */
	public static List getListByResultSet(ResultSet rs){
		List list=new ArrayList();
		if(rs==null)return Collections.EMPTY_LIST;
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
		int ColumnCount=rsmd.getColumnCount();
		Map hm;
		while(rs.next()){
			hm=new HashMap();
			for(int i=1;i<=ColumnCount;i++){
				hm.put(rsmd.getColumnName(i), rs.getObject(i));
			}
			list.add(hm);
		}
		} catch (SQLException e) {
			System.out.println("sql error %%% "+e.getMessage());
			return Collections.EMPTY_LIST;
		}
		return list;
	}
	
	/**
	 * 将jdbc查询的结果集封装到List中---Spring集成jdbc的处理方式
	 * @param rs--SQLROWSET
	 * @return List
	 */
	public static List getListBySqlRowSet(SqlRowSet rs){
		List list=new ArrayList();
		if(rs==null)return Collections.EMPTY_LIST;
		SqlRowSetMetaData rsmd;
		rsmd = rs.getMetaData();
		int ColumnCount=rsmd.getColumnCount();
		Map hm;
		while(rs.next()){
			hm=new HashMap();
			for(int i=1;i<=ColumnCount;i++){
				hm.put(rsmd.getColumnName(i), rs.getObject(i));
			}
			list.add(hm);
		}
		return list;
	}
	
	/**
	 * 将单行结果集封装到HashMap中
	 *  @param rs
	 *  @return HashMap
	 */
	public static HashMap getHashMapByResultSet(ResultSet rs){
		HashMap hm=new HashMap();
		if(rs==null)return null;
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();	
			int ColumnCount=rsmd.getColumnCount();
			while(rs.next()){
				hm=new HashMap();
				for(int i=1;i<=ColumnCount;i++){
					hm.put(rsmd.getColumnName(i), rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			System.out.println("sql error %%%  "+e.getMessage());
			return null;
		} 
		return hm;
	}
}