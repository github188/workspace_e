package com.nari.sysman.securityman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.nari.privilige.PAccessTmnlFactory;
import com.nari.privilige.PAccessTmnlFactoryId;
import com.nari.sysman.securityman.IPAccessTmnlFactoryJdbcDao;
import com.nari.util.exception.DBAccessException;

public class PAccessTmnlFactoryJdbcDaoImpl extends JdbcDaoSupport implements
		IPAccessTmnlFactoryJdbcDao {
	protected Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 根据staffNo查询所拥有的终端厂商权限
	 * @param staffNo
	 * @throws DBAccessException
	 */
	public List<PAccessTmnlFactory> findByStaffNo(String staffNo) {
		String sql = "select tf.STAFF_NO, tf.FACTORY_CODE from  P_ACCESS_TMNL_FACTORY tf where tf.STAFF_NO = ?";
        try{
            List<PAccessTmnlFactory> list = new ArrayList<PAccessTmnlFactory>();
            list = this.getJdbcTemplate().query(sql, new Object[]{staffNo}, new PAccessTmnlFactoryRowMapper());
            return list;
        } catch(RuntimeException e){
              this.logger.debug("查询用户终端厂家权限出错！");
              throw e;
        }
	}
	
	class PAccessTmnlFactoryRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
			PAccessTmnlFactory pAccessTmnlFactory = new PAccessTmnlFactory();
			PAccessTmnlFactoryId pAccessTmnlFactoryId = new PAccessTmnlFactoryId();
			try { 
				
				pAccessTmnlFactoryId.setStaffNo(rs.getString("STAFF_NO"));
				pAccessTmnlFactoryId.setFactoryCode(rs.getString("FACTORY_CODE"));
				pAccessTmnlFactory.setId(pAccessTmnlFactoryId);
			    return pAccessTmnlFactory;
			} catch (Exception e) {
				PAccessTmnlFactoryJdbcDaoImpl.this.logger.error("取 PAccessTmnlFactoryRowMapper 时出现错误！ ");
			    return null;
			}
		}
	}

	/**
	 * 根据操作员ID删除所属厂家
	 * @param staffNo
	 * @throws DBAccessException
	 */
	public void delFac(String staffNo) {
		String sql = "delete P_ACCESS_TMNL_FACTORY t where t.STAFF_NO = ?";
		try{
			this.getJdbcTemplate().update(sql,new Object[]{staffNo});
		}catch(RuntimeException e){
			this.logger.debug("根据操作员ID删除所属厂家出错！");
			throw e;
		}
	}

	/**
	 * 插入新的厂家权限
	 * @param PAccessTmnlFactory
	 * @throws DBAccessException
	 */
	public void saveFac(PAccessTmnlFactory pAccessTmnlFactory) {
		String staffNo = pAccessTmnlFactory.getId().getStaffNo();
		String factoryCode = pAccessTmnlFactory.getId().getFactoryCode();
		String sql = "insert into P_ACCESS_TMNL_FACTORY (STAFF_NO, FACTORY_CODE) VALUES (?,?)";
		try{
			this.getJdbcTemplate().update(sql,new Object[]{staffNo, factoryCode});
		}catch(RuntimeException e){
			this.logger.debug("根据操作员ID删除所属角色出错！");
			throw e;
		}
	}

}
