package com.nari.oranization.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.oranization.IOrgJdbcDao;

public class OrgJdbcDaoImpl extends JdbcBaseDAOImpl implements IOrgJdbcDao {

	@SuppressWarnings("unchecked")
	@Override
	public Map findNoToName(Collection c) {
		final HashMap map=new HashMap(1);
		if(null==c||c.size()==0){
			return map;
		}
		StringBuilder sb=new StringBuilder("select ORG_NO,ORG_NAME from O_ORG where ORG_NO in (");
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			iterator.next();
			if(iterator.hasNext()){
				sb.append("?,");
			}else{
				sb.append("?)");
			}
		}
		getJdbcTemplate().query(sb.toString(), c.toArray(), new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int i)
					throws SQLException {
				map.put(rs.getString("org_no"), rs.getString("org_name"));
				return null;
			}});
		return map;
	}

}
