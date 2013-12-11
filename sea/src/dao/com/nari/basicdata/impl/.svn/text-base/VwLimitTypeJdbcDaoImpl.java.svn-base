package com.nari.basicdata.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.nari.basicdata.VwLimitType;
import com.nari.basicdata.VwLimitTypeJdbcDao;

/**
 * 限电类型表Jdbc操作Dao实现类
 * @author 姜炜超
 */
public class VwLimitTypeJdbcDaoImpl extends JdbcDaoSupport implements VwLimitTypeJdbcDao {
	protected Logger logger = Logger.getLogger(this.getClass());

	public List<VwLimitType> findAll(){
		String sql = "select lt.LIMIT_TYPE, lt.LIMIT_TYPE_name from  vw_limit_type lt";
        try{
            List<VwLimitType> list = new ArrayList<VwLimitType>();
            list = this.getJdbcTemplate().query(sql, new VwLimitTypeRowMapper());
            return list;
        } catch(RuntimeException e){
              this.logger.debug("查询限电类型出错！");
              throw e;
        }
	}
	
	class VwLimitTypeRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
			VwLimitType vwlimittype = new VwLimitType();
			try { 
			    vwlimittype.setLimitType(rs.getString("LIMIT_TYPE"));
			    vwlimittype.setLimitTypeName(rs.getString("LIMIT_TYPE_NAME"));
			    return vwlimittype;
			} catch (Exception e) {
				VwLimitTypeJdbcDaoImpl.this.logger.error("取 VwLimitTypeRowMapper 时出现错误！ ");
			    return null;
			}
		}
	}
}
