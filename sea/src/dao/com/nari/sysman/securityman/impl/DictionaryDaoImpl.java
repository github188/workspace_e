package com.nari.sysman.securityman.impl;

import java.util.List;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.sysman.securityman.IDictionaryDao;

public class DictionaryDaoImpl extends JdbcBaseDAOImpl implements
		IDictionaryDao {

	@Override
	public List getDictionary(String sql) {
		return super.getJdbcTemplate().queryForList(sql);
	}

}
