package com.nari.sysman.securityman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.nari.privilige.PAccessConsType;
import com.nari.privilige.PAccessConsTypeId;
import com.nari.sysman.securityman.IPAccessConsTypeJdbcDao;
import com.nari.util.exception.DBAccessException;

public class PAccessConsTypeJdbcDaoImpl extends JdbcDaoSupport implements IPAccessConsTypeJdbcDao {

	protected Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 根据staffNo查询所拥有的用电用户类型
	 * @param staffNo
	 * @throws DBAccessException
	 */
	public List<PAccessConsType> findByStaffNo(String staffNo) {
		String sql = "select t.STAFF_NO, t.CONS_TYPE from  P_ACCESS_CONS_TYPE t where t.STAFF_NO = ?";
        try{
            List<PAccessConsType> list = new ArrayList<PAccessConsType>();
            list = this.getJdbcTemplate().query(sql, new Object[]{staffNo}, new PAccessConsTypeRowMapper());
            return list;
        } catch(RuntimeException e){
              this.logger.debug("查询操作员用电用户类型权限出错！");
              throw e;
        }
	}
	
	class PAccessConsTypeRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
			PAccessConsType pAccessConsType = new PAccessConsType();
			PAccessConsTypeId pAccessConsTypeId = new PAccessConsTypeId();
			try { 
				
				pAccessConsTypeId.setStaffNo(rs.getString("STAFF_NO"));
				pAccessConsTypeId.setConsType(Byte.parseByte(rs.getString("CONS_TYPE")));
				pAccessConsType.setId(pAccessConsTypeId);
			    return pAccessConsType;
			} catch (Exception e) {
				PAccessConsTypeJdbcDaoImpl.this.logger.error("取 PAccessConsTypeRowMapper 时出现错误！ ");
			    return null;
			}
		}
	}

	/**
	 * 根据操作员ID删除所属用户用电类型
	 * @param staffNo
	 * @throws DBAccessException
	 */
	public void delConsType(String staffNo) {
		String sql = "delete P_ACCESS_CONS_TYPE t where t.STAFF_NO = ?";
		try{
			this.getJdbcTemplate().update(sql,new Object[]{staffNo});
		}catch(RuntimeException e){
			this.logger.debug("根据操作员ID删除所属用户用电类型出错！");
			throw e;
		}
	}

	/**
	 * 插入新的用电用户类型权限
	 * @param PAccessTmnlFactory
	 * @throws DBAccessException
	 */
	public void saveConsType(PAccessConsType pAccessConsType) {
		String staffNo = pAccessConsType.getId().getStaffNo();
		Byte consType = pAccessConsType.getId().getConsType();
		String sql = "insert into P_ACCESS_CONS_TYPE (STAFF_NO, CONS_TYPE) VALUES (?,?)";
		try{
			this.getJdbcTemplate().update(sql,new Object[]{staffNo, consType});
		}catch(RuntimeException e){
			this.logger.debug("插入新的用户用电类型出错！");
			throw e;
		}
	}

}
