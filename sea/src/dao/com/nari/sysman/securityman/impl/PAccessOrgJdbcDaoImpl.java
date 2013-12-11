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
import com.nari.privilige.PAccessOrg;
import com.nari.privilige.PAccessOrgId;
import com.nari.sysman.securityman.IPAccessOrgJdbcDao;
import com.nari.sysman.securityman.impl.PAccessConsTypeJdbcDaoImpl.PAccessConsTypeRowMapper;
import com.nari.util.exception.DBAccessException;

public class PAccessOrgJdbcDaoImpl extends JdbcDaoSupport implements IPAccessOrgJdbcDao {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 根据staffNo查询所拥有的单位
	 * @param staffNo
	 * @throws DBAccessException
	 */
	public List<PAccessOrg> findByStaffNo(String staffNo) {
		String sql = "select t.STAFF_NO, t.ORG_NO, t.ORG_TYPE from  P_ACCESS_ORG t where t.STAFF_NO = ?";
        try{
            List<PAccessOrg> list = new ArrayList<PAccessOrg>();
            list = this.getJdbcTemplate().query(sql, new Object[]{staffNo}, new PAccessOrgRowMapper());
            return list;
        } catch(RuntimeException e){
              this.logger.debug("查询操作员单位权限出错！");
              throw e;
        }
	}
	
	class PAccessOrgRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
			PAccessOrg pAccessOrg = new PAccessOrg();
			PAccessOrgId pAccessOrgId = new PAccessOrgId();
			try {
				pAccessOrgId.setStaffNo(rs.getString("STAFF_NO"));
				pAccessOrgId.setOrgNo(rs.getString("ORG_NO"));
				pAccessOrgId.setOrgType(rs.getString("ORG_TYPE"));
				pAccessOrg.setId(pAccessOrgId);
			    return pAccessOrg;
			} catch (Exception e) {
				PAccessOrgJdbcDaoImpl.this.logger.error("取 PAccessOrgRowMapper 时出现错误！ ");
			    return null;
			}
		}
	}

	/**
	 * 根据操作员ID删除单位权限
	 * @param staffNo
	 * @throws DBAccessException
	 */
	public void delOrg(String staffNo) {
		String sql = "delete P_ACCESS_ORG t where t.STAFF_NO = ?";
		try{
			this.getJdbcTemplate().update(sql,new Object[]{staffNo});
		}catch(RuntimeException e){
			this.logger.debug("根据操作员ID删除单位权限！");
			throw e;
		}
	}

	/**
	 * 插入单位权限
	 * @param staffNo
	 * @throws DBAccessException
	 */
	public void saveOrg(PAccessOrg pAccessOrg) {
		String staffNo = pAccessOrg.getId().getStaffNo();
		String orgNo = pAccessOrg.getId().getOrgNo();
		String orgType = pAccessOrg.getId().getOrgType();
		String sql = "insert into P_ACCESS_ORG (STAFF_NO, ORG_NO, ORG_TYPE) VALUES (?,?,?)";
		try{
			this.getJdbcTemplate().update(sql,new Object[]{staffNo, orgNo, orgType});
		}catch(RuntimeException e){
			this.logger.debug("插入新的单位权限出错！");
			throw e;
		}
	}
}
