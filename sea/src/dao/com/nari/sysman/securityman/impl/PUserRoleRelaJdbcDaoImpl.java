package com.nari.sysman.securityman.impl;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.nari.sysman.securityman.IPUserRoleRelaJdbcDao;
import com.nari.util.exception.DBAccessException;

public class PUserRoleRelaJdbcDaoImpl extends JdbcDaoSupport implements IPUserRoleRelaJdbcDao {
	protected Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 根据操作员ID删除所属角色
	 * @param staffNo
	 * @throws DBAccessException
	 */
	public void delRole(String staffNo) {
		String sql = "delete P_USER_ROLE_RELA t where t.STAFF_NO = ?";
		try{
			this.getJdbcTemplate().update(sql,new Object[]{staffNo});
		}catch(RuntimeException e){
			this.logger.debug("根据操作员ID删除所属角色出错！");
			throw e;
		}
	}
	
	/**
	 * 根据角色ID删除所属角色
	 * @param staffNo
	 * @throws DBAccessException
	 */
	public void deleteByRoleId(long roleId) {
		String sql = "delete P_USER_ROLE_RELA t where t.ROLE_ID = ?";
		try{
			this.getJdbcTemplate().update(sql,new Object[]{roleId});
		}catch(RuntimeException e){
			this.logger.debug("根据角色ID删除所属角色出错！");
			throw e;
		}
	}
}
