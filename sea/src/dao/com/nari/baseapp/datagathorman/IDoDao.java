package com.nari.baseapp.datagathorman;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;

/**
 * 一个接口，用于dao公共语句的回调
 * @author huangxuan
 *
 */
public interface IDoDao {
	public	<T> T execute(JdbcBaseDAOImpl jb,RowMapper rm,SqlData sd);
}