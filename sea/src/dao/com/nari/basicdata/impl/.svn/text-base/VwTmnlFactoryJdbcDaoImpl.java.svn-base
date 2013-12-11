package com.nari.basicdata.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.nari.basicdata.VwTmnlFactory;
import com.nari.basicdata.VwTmnlFactoryJdbcDao;

/**
 * 限电类型表Jdbc操作Dao实现类
 * @author 姜炜超
 */
public class VwTmnlFactoryJdbcDaoImpl extends JdbcDaoSupport implements VwTmnlFactoryJdbcDao {
	protected Logger logger = Logger.getLogger(this.getClass());

	public List<VwTmnlFactory> findAll(){
		String sql = "select tf.FACTORY_CODE, tf.FACTORY_NAME from  VW_TMNL_FACTORY tf order by tf.FACTORY_CODE";
        try{
            List<VwTmnlFactory> list = new ArrayList<VwTmnlFactory>();
            list = this.getJdbcTemplate().query(sql, new VwTmnlFactoryRowMapper());
            return list;
        } catch(RuntimeException e){
              this.logger.debug("查询终端厂家出错！");
              throw e;
        }
	}
	
	class VwTmnlFactoryRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
			VwTmnlFactory vwTmnlFactory = new VwTmnlFactory();
			try { 
				vwTmnlFactory.setFactoryCode(rs.getString("FACTORY_CODE"));
				vwTmnlFactory.setFactoryName(rs.getString("FACTORY_NAME"));
			    return vwTmnlFactory;
			} catch (Exception e) {
				VwTmnlFactoryJdbcDaoImpl.this.logger.error("取 VwTmnlFactoryRowMapper 时出现错误！ ");
			    return null;
			}
		}
	}
}
