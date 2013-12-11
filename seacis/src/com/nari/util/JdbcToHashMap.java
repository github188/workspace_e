package com.nari.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

public class JdbcToHashMap {
	/**
	 * 将jdbc查询的结果集封装到List中
	 * @param rs--SQLROWSET
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getListByCS(SqlRowSet rs){
		List list = new ArrayList();
		//把ls转成hashmap格式
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
}