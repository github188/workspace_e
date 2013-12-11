package com.nari.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class LinkedBaseDao extends BaseDao {

	 //操作中间数据库
    private  JdbcTemplate jdbcYXDAO;
    //操作采集数据库
    private JdbcTemplate jdbcDAO;
    
    /**
     * 主站查询
     * @param sql
     * @param obj
     * @return
     */
    public List<?> queryForListJdbc(String sql, Object[] obj) {
    	jdbcDAO = this.getJdbcDAO();
    	Connection con = null;
    	List<?> list = null;
    	try {
    		list = jdbcDAO.queryForList(sql, obj);
    		con = jdbcDAO.getDataSource().getConnection();
    		if (con != null) {
    			con.close();
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		con = null;
    	}
    	return list;
    }
    
    /**
     * 主站查询
     * @param sql
     * @return
     * @throws SQLException
     */
    public List<?> queryForListJdbc(String sql) {
    	jdbcDAO = this.getJdbcDAO();
    	Connection con = null;
    	List<?> list = null;
    	try {
    		list = jdbcDAO.queryForList(sql);
    		con = jdbcDAO.getDataSource().getConnection();
    		if (con != null) {
    			con.close();
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		con = null;
    	} 
    	return list;
    }
    

    /**
     * 中间库查询
     * @param sql
     * @param obj
     * @return
     * @throws SQLException
     */
    public List<?> queryForListYXJdbc(String sql, Object[] obj) {
    	jdbcYXDAO = this.getJdbcYXDAO();
    	Connection con = null;
    	List<?> list = null;
    	try {
    		list = jdbcYXDAO.queryForList(sql, obj);
    		con = jdbcYXDAO.getDataSource().getConnection();
    		if (con != null) {
    			con.close();
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		con = null;
    	}
    	return list;
    }
    
    /**
     * 中间库查询
     * @param sql
     * @param obj
     * @return
     * @throws SQLException
     */
    public List<?> queryForListYXJdbc(String sql) {
    	jdbcYXDAO = this.getJdbcYXDAO();
    	Connection con = null;
    	List<?> list = null;
    	try {
    		list = jdbcYXDAO.queryForList(sql);
    		con = jdbcYXDAO.getDataSource().getConnection();
    		if (con != null) {
    			con.close();
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		con = null;
    	}
    	return list;
    }
    
    /**
     * 中间库更新
     * @param sql
     * @param obj
     * @return
     * @throws SQLException
     */
    public void updateYXJdbc(String sql, Object[] obj) {
    	jdbcYXDAO = this.getJdbcYXDAO();
    	Connection con = null;
    	try {
    		jdbcYXDAO.update(sql, obj);
    		con = jdbcYXDAO.getDataSource().getConnection();
    		if (con != null) {
    			con.commit();
    			con.close();
    		}
    	} catch (Exception e) {
    		System.out.println("+++++++++++++++++++++++++++++++++++数据库操作异常：updateYXJdbc+++++++++++++++++++++++++++++++++++");
    		e.printStackTrace();
    		con = null;
    	}
    }
    
    /**
     * 主站更新
     * @param sql
     * @param obj
     * @return
     * @throws SQLException
     */
    public int updateJdbc(String sql, Object[] obj) {
    	int i = 0;
    	Connection con = null;
    	jdbcDAO = this.getJdbcDAO();
    	try {
    		i = jdbcDAO.update(sql, obj);
    		con = jdbcDAO.getDataSource().getConnection();
    		if (con != null) {
    			con.close();
    		}
    	} catch (Exception e) {
    		System.out.println("+++++++++++++++++++++++++++++++++++数据库操作异常：updateJdbc+++++++++++++++++++++++++++++++++++");
    		e.printStackTrace();
    		con = null;
    	}
    	return i;
    }
    
    /**
     * 主站批量更新
     * @param sql
     * @param obj
     * @return
     * @throws SQLException
     */
    public int[] updateJdbc(String[] sql) {
    	int[] k = null ;
    	jdbcDAO = this.getJdbcDAO();
    	Connection con = null;
    	try {
    		k = jdbcDAO.batchUpdate(sql);
    		con = jdbcDAO.getDataSource().getConnection();
    		if (con != null) {
    			con.close();
    		}
    	} catch (Exception e) {
    		System.out.println("+++++++++++++++++++++++++++++++++++数据库操作异常：updateJdbc+++++++++++++++++++++++++++++++++++");
    		e.printStackTrace();
    		con = null;
    	}
    	return k;
    }
    
    /**
     * 主站更新
     * @param sql
     * @return
     * @throws SQLException
     */
    public int updateJdbc(String sql) {
    	int i = 0;
    	jdbcDAO = this.getJdbcDAO();
    	Connection con = null;
    	try {
    		i = jdbcDAO.update(sql);
    		if (con != null) {
    			System.out.println(con);
    			System.out.println("+++++++++++++++++++++++++++++++++++数据库连接不为空+++++++++++++++++++++++++++++++++++");
    		}
    	} catch (Exception e) {
    		System.out.println("+++++++++++++++++++++++++++++++++++数据库操作异常：updateJdbc+++++++++++++++++++++++++++++++++++");
    		e.printStackTrace();
    		con = null;
    	}
    	return i;
    }
	
}
