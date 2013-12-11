package com.nari.activesyn;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.PlatformTransactionManager;

public class BaseDao{
	
    //操作中间数据库(营销库)
    private JdbcTemplate jdbcYXDAO;
    //操作采集数据库
    private JdbcTemplate jdbcDAO;
    

	public JdbcTemplate getJdbcDAO() {
		return jdbcDAO;
	}
	
	public void setJdbcDAO(JdbcTemplate jdbcDAO) {
		this.jdbcDAO = jdbcDAO;
	}
	
	public JdbcTemplate getJdbcYXDAO() {
		return jdbcYXDAO;
	}

	public void setJdbcYXDAO(JdbcTemplate jdbcYXDAO) {
		this.jdbcYXDAO = jdbcYXDAO;
	}
	
	//依赖注入管理事务 
	private PlatformTransactionManager transactionManager;
    
    public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}