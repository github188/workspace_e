package com.nari.basicdata.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.nari.basicdata.VwConsType;
import com.nari.basicdata.VwConsTypeJdbcDao;

/**
 * 用电用户类型Jdbc操作Dao实现类
 * @author ww
 */
public class VwConsTypeJdbcDaoImpl extends JdbcDaoSupport implements VwConsTypeJdbcDao {
	protected Logger logger = Logger.getLogger(this.getClass());

	public List<VwConsType> findAll(){
		String sql = "select t.CONS_TYPE, t.CONS_TYPE_NAME from  VW_CONS_TYPE t";
        try{
            List<VwConsType> list = new ArrayList<VwConsType>();
            list = this.getJdbcTemplate().query(sql, new VwConsTypeRowMapper());
            return list;
        } catch(RuntimeException e){
              this.logger.debug("查询终端厂家出错！");
              throw e;
        }
	}
	
	class VwConsTypeRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
			VwConsType vwConsType = new VwConsType();
			try { 
				vwConsType.setConsType(rs.getString("CONS_TYPE"));
				vwConsType.setConsTypeName(rs.getString("CONS_TYPE_NAME"));
			    return vwConsType;
			} catch (Exception e) {
				VwConsTypeJdbcDaoImpl.this.logger.error("取 VwConsTypeRowMapper 时出现错误！ ");
			    return null;
			}
		}
	}
}
