package com.nari.baseapp.datagathorman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.datagatherman.IllegalFrameQry;
import com.nari.baseapp.datagatherman.OrigFrameQryAsset;
import com.nari.baseapp.datagathorman.IllegalFrameQryDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * 非法报文查询DAO 实现类
 * @author ChunMingLi
 * @version 2010-4-25
 *
 */
public class IllegalFrameQryDaoImpl extends JdbcBaseDAOImpl implements IllegalFrameQryDao {

	//非法报文DTO
	private IllegalFrameQry illegalFrameQry;
	//逻辑地址
	private OrigFrameQryAsset origFrameQryAsset;
	
	public IllegalFrameQry getIllegalFrameQry() {
		return illegalFrameQry;
	}

	public void setIllegalFrameQry(IllegalFrameQry illegalFrameQry) {
		this.illegalFrameQry = illegalFrameQry;
	}

	public OrigFrameQryAsset getOrigFrameQryAsset() {
		return origFrameQryAsset;
	}

	public void setOrigFrameQryAsset(OrigFrameQryAsset origFrameQryAsset) {
		this.origFrameQryAsset = origFrameQryAsset;
	}

	/*
	 * (non-Javadoc)
	 * @see com.nari.baseapp.datagathorman.IllegalFrameQryDao#findIllegalFrameQry(java.lang.String, java.lang.String, java.lang.String, long, int)
	 */
	@Override
	public Page<IllegalFrameQry> findIllegalFrameQry(String terminalAddr,
			String DateStart, String DateEnd, long start, int limit)
			throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT L.COMM_TIME,L.TERMINAL_ADDR,L.TMNL_CODE,L.CTRL_CODE,L.MESSAGE \n");
		sb.append("FROM L_LOWLESS_MSG_LOG L \n");
		sb.append("WHERE L.TERMINAL_ADDR = ? \n");
		sb.append("AND L.COMM_TIME BETWEEN TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') \n");
		sb.append("AND TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n");
		sb.append(" ORDER BY COMM_TIME DESC \n");
		String sql = sb.toString();
		logger.debug(sql);
		try{
			return  super.pagingFind(sql, start, limit, new IllegalFrameQryRowMapper(),new Object[]{terminalAddr,DateStart,DateEnd});
		}catch(RuntimeException e){
			this.logger.debug(sql);
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.nari.baseapp.datagathorman.IllegalFrameQryDao#findIllegalFrameQryAsset(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrigFrameQryAsset> findIllegalFrameQryAsset(String illegalFrameQryID) throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT TERMINAL_ADDR, CONS_NO FROM VW_TMNL_RUN WHERE CONS_NO = ? \n");
		String sql = sb.toString();
		logger.debug(sql);
		try {
			return this.getJdbcTemplate().query(sb.toString(), new Object[]{illegalFrameQryID}, new IllegalFrameQryAssetRowMapper());
		} catch (RuntimeException e) {
			this.logger.debug(sql);
			throw e;
		}
	}

	/**
	 * 
	 * JDBC映射类
	 * 
	 * @author ChunMingLi
	 * 2010-4-26
	 *
	 */
	class IllegalFrameQryRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			IllegalFrameQry illegalFrameQry = new IllegalFrameQry();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (rs.getString("COMM_TIME") != null) {
					illegalFrameQry.setCommTime(sdf.parse(rs.getString("COMM_TIME")));
				}
				illegalFrameQry.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				illegalFrameQry.setCtrlCode(rs.getString("CTRL_CODE"));
				illegalFrameQry.setTmnlCode(rs.getString("TMNL_CODE"));
				illegalFrameQry.setMessage(rs.getString("MESSAGE"));
				return illegalFrameQry;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * 
	 * JDBC映射类
	 * 
	 * @author chunmingli
	 * 2010-4-26
	 *
	 */
	class IllegalFrameQryAssetRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			OrigFrameQryAsset origframeqryasset = new OrigFrameQryAsset();
			try {
				origframeqryasset.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				origframeqryasset.setConsNo(rs.getString("CONS_NO"));
				return origframeqryasset;
			} catch (Exception e) {
				return null;
			}
		}
	}

}
