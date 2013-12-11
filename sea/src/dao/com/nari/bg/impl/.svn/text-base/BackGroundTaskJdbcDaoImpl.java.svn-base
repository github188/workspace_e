package com.nari.bg.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.nari.bg.BackGroundTaskJdbcDao;

public class BackGroundTaskJdbcDaoImpl extends JdbcDaoSupport implements
		BackGroundTaskJdbcDao {

	@Override
	public long getTaskId() {
		// TODO Auto-generated method stub
		String sql = "select s_t_bg_task.nextval from dual";
		return this.getJdbcTemplate().queryForLong(sql);
	}
}
