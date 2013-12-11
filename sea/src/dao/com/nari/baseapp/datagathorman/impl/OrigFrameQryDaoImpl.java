package com.nari.baseapp.datagathorman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.datagatherman.HeartBeat;
import com.nari.baseapp.datagatherman.OnoffStat;
import com.nari.baseapp.datagatherman.OrigFrameQry;
import com.nari.baseapp.datagatherman.OrigFrameQryAsset;
import com.nari.baseapp.datagathorman.OrigFrameQryDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;
import com.nari.util.DateUtil;
import com.nari.util.exception.DBAccessException;
import common.Logger;

public class OrigFrameQryDaoImpl extends JdbcBaseDAOImpl implements
		OrigFrameQryDao {
	private Logger log = Logger.getLogger(OrigFrameQryDaoImpl.class);
	private OrigFrameQry origFrameQry;
	private OrigFrameQryAsset origFrameQryAsset;

	public OrigFrameQryAsset getOrigFrameQryAsset() {
		return origFrameQryAsset;
	}

	public void setOrigFrameQryAsset(OrigFrameQryAsset origFrameQryAsset) {
		this.origFrameQryAsset = origFrameQryAsset;
	}

	public OrigFrameQry getOrigFrameQry() {
		return origFrameQry;
	}

	public void setOrigFrameQry(OrigFrameQry origFrameQry) {
		this.origFrameQry = origFrameQry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrigFrameQryAsset> findOrigFrameQryAsset(String origFrameQryID)
			throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT TERMINAL_ADDR,PROTOCOL_CODE,CONS_NO,TMNL_ASSET_NO FROM VW_TMNL_RUN WHERE CONS_NO = ?");
		String sql = sb.toString();
		this.logger.debug(sql);
		try {
			return this.getJdbcTemplate().query(sb.toString(),
					new Object[] { origFrameQryID },
					new OrigFrameQryAssetRowMapper());
		} catch (RuntimeException e) {
			this.logger.debug(sql);
			throw e;
		}
	}

	public Page<OrigFrameQry> findOrigFrameQry(String terminalAddr,
			String DateStart, String DateEnd, long start, int limit)
			throws DBAccessException {

		String terminalAddr2 = terminalAddr;
		String DateStart2 = DateStart;
		String DateEnd2 = DateEnd;

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT U.COMM_TIME,\n");
		sb.append("       U.TERMINAL_ADDR,\n");
		sb.append("       CASE WHEN v.COMM_MODE_CODE IS NULL THEN '未知'\n");
		sb.append("            ELSE v.COMM_MODE END AS COMM_MODE,\n");
		sb.append("       U.MSG_LEN,\n");
		sb.append("      U.MESSAGE,\n");
		sb.append("       A.TMNL_IP AS FROM_ADDR,\n");
		sb.append("       U.TO_ADDR, 1 as direction\n");
		sb
				.append("  FROM L_UP_COMM_MSG_LOG U,vw_comm_mode V,a_tmnl_real_time A,r_tmnl_run T  WHERE U.TERMINAL_ADDR = T.TERMINAL_ADDR AND  A.TMNL_ASSET_NO = T.TMNL_ASSET_NO AND U.comm_type = v.COMM_MODE_CODE(+) AND U.TERMINAL_ADDR = ?\n");
		sb
				.append("   AND U.COMM_TIME BETWEEN TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') AND\n");
		sb.append("       TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n");
		sb.append("UNION\n");
		sb.append("SELECT D.COMM_TIME,\n");
		sb.append("       D.TERMINAL_ADDR,\n");
		sb.append("       CASE WHEN v.COMM_MODE_CODE IS NULL THEN '未知'\n");
		sb.append("            ELSE v.COMM_MODE END AS COMM_MODE,\n");
		sb.append("       D.MSG_LEN,\n");
		sb.append("       D.MESSAGE,\n");
		sb.append("       D.FROM_ADDR,\n");
		sb.append("       A.TMNL_IP AS TO_ADDR,\n");
		sb.append("       2 AS DIRECTION\n");
		sb
				.append("  FROM L_DOWN_COMM_MSG_LOG D, VW_COMM_MODE V, a_tmnl_real_time A,r_tmnl_run T \n");
		sb
				.append(" WHERE d.comm_type = v.COMM_MODE_CODE(+) AND D.TERMINAL_ADDR = T.TERMINAL_ADDR AND  A.TMNL_ASSET_NO = T.TMNL_ASSET_NO \n");
		sb.append("   AND D.TERMINAL_ADDR = ?\n");
		sb.append("   AND D.COMM_TIME BETWEEN\n");
		sb.append("       TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') AND\n");
		sb.append("       TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n");
		sb.append(" ORDER BY COMM_TIME DESC");

		String sql = sb.toString();

		this.logger.debug(sql);
		try {
			return super.pagingFind(sb.toString(), start, limit,
					new OrigFrameQryRowMapper(), new Object[] { terminalAddr,
							DateStart, DateEnd, terminalAddr2, DateStart2,
							DateEnd2 });
		} catch (RuntimeException e) {
			this.logger.debug(sql);
			throw e;
		}
	}

	class OrigFrameQryRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			OrigFrameQry origframeqry = new OrigFrameQry();
			try {
				origframeqry.setCommMode(rs.getString("COMM_MODE"));
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				if (rs.getString("COMM_TIME") != null) {
					origframeqry.setCommTime(sdf.parse(rs
							.getString("COMM_TIME")));
				}
				origframeqry.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				origframeqry.setCommMode(rs.getString("COMM_MODE"));
				origframeqry.setMsgLen(rs.getShort("MSG_LEN"));
				origframeqry.setMessage(rs.getString("MESSAGE"));
				origframeqry.setFromAddr(rs.getString("FROM_ADDR"));
				origframeqry.setToAddr(rs.getString("TO_ADDR"));
				origframeqry.setDirection(rs.getLong("DIRECTION"));
				return origframeqry;
			} catch (Exception e) {
				return null;
			}
		}
	}

	class OrigFrameQryAssetRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			OrigFrameQryAsset origframeqryasset = new OrigFrameQryAsset();
			try {
				origframeqryasset
						.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				origframeqryasset.setConsNo(rs.getString("CONS_NO"));
				origframeqryasset
						.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				origframeqryasset.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				return origframeqryasset;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrigFrameQryAsset> findOrigFrameQryTmnl(String terminalAddr)
			throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		sb
				.append("select e.asset_no as TMNL_ASSET_NO from e_data_mp e where e.mp_sn<>0 and e.terminal_addr =? ");
		String sql = sb.toString();
		this.logger.debug(sql);
		try {
			return this.getJdbcTemplate().query(sb.toString(),
					new Object[] { terminalAddr },
					new OrigFrameQryTmnlRowMapper());
		} catch (RuntimeException e) {
			this.logger.debug(sql);
			throw e;
		}
	}

	/**
	 * 查询心跳信息
	 * 
	 * @param consNo
	 *            ,DateStart,DateEnd
	 * @author 陈国章
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<HeartBeat> queryHeartBeat(String consNo, String terminalAddr,
			String dateStart, String dateEnd, String protocolCode, long start,
			int limit) {
		Page<HeartBeat> hbPage = null;
		List<HeartBeat> bhList = null;
		String sql;
		if (null != terminalAddr && !("".equals(terminalAddr))) {
			sql = "SELECT distinct  O.ORG_NAME AS ORG_NAME, C.CONS_NO AS CONS_NO,C.CONS_NAME AS CONS_NAME ,TMNL.Terminal_Addr AS TERMINAL_ADDR,ATOI.TMNL_ASSET_NO AS TMNL_ASSET_NO,\n"
					+ "DECODE(ATOI.IS_ONLINE,1,'在线',0,'离线','未知') AS IS_ONLINE,ATOI.ONOFF_TIME AS ONOFF_TIME FROM\n"
					+ "E_DATA_MP EDM,R_TMNL_RUN TMNL,A_TMNL_ONLINE_INFO ATOI,C_CONS C,\n"
					+ "O_ORG O WHERE EDM.Terminal_Addr=TMNL.Terminal_Addr AND\n"
					+ " EDM.CONS_NO=C.CONS_NO AND ATOI.Terminal_Addr=TMNL.Terminal_Addr\n"
					+ "AND O.ORG_NO=EDM.ORG_NO\n"
					+ "AND C.CONS_NO=?\n"
					+ "AND TMNL.Terminal_Addr=?\n"
					+ "AND ATOI.ONOFF_TIME>=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') AND\n"
					+ "ATOI.ONOFF_TIME<=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')";
			hbPage = this.pagingFind(sql, start, limit, new HeartBeatMapper(),
					new Object[] { consNo, terminalAddr, dateStart, dateEnd });
			// bhList=super.getJdbcTemplate().query(sql,new
			// Object[]{consNo,terminalAddr,dateStart,dateEnd}, new
			// HeartBeatMapper());
			logger.debug(sql);
		} else {
			sql = "SELECT distinct  O.ORG_NAME AS ORG_NAME, C.CONS_NO AS CONS_NO,C.CONS_NAME AS CONS_NAME ,TMNL.Terminal_Addr AS TERMINAL_ADDR,ATOI.TMNL_ASSET_NO AS TMNL_ASSET_NO,\n"
					+ "DECODE(ATOI.IS_ONLINE,1,'在线',0,'离线','未知') AS IS_ONLINE,ATOI.ONOFF_TIME AS ONOFF_TIME FROM\n"
					+ "E_DATA_MP EDM,R_TMNL_RUN TMNL,A_TMNL_ONLINE_INFO ATOI,C_CONS C,\n"
					+ "O_ORG O WHERE EDM.Terminal_Addr=TMNL.Terminal_Addr AND\n"
					+ " EDM.CONS_NO=C.CONS_NO AND ATOI.Terminal_Addr=TMNL.Terminal_Addr\n"
					+ "AND O.ORG_NO=EDM.ORG_NO\n"
					+ "AND C.CONS_NO=?\n"
					+ "AND ATOI.ONOFF_TIME>=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') AND\n"
					+ "ATOI.ONOFF_TIME<=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')";
			hbPage = this.pagingFind(sql, start, limit, new HeartBeatMapper(),
					new Object[] { consNo, dateStart, dateEnd });
			// bhList=super.getJdbcTemplate().query(sql,new
			// Object[]{consNo,dateStart,dateEnd}, new HeartBeatMapper());
			logger.debug(sql);
		}
		if(hbPage==null){
			hbPage=new Page<HeartBeat>();
		}
		return hbPage;
	}

	/**
	 * 查询心跳信息
	 * 
	 * @param consNo
	 *            ,DateStart,DateEnd
	 * @author 陈国章
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OnoffStat> onoffStat(String consNo, String terminalAddr,
			String dateStart, String dateEnd) {
		List<OnoffStat> oosList = null;
		if (null != terminalAddr && !("".equals(terminalAddr))) {
		String sql = 

			"SELECT  SUM(DECODE(C.IS_ONLINE,1,1,0,0,0)) AS onlineNum , SUM(DECODE(C.IS_ONLINE,0,1,1,0,0)) AS offlineNum\n" +
			"FROM (SELECT DISTINCT O.ORG_NAME AS ORG_NAME,\n" + 
			"C.CONS_NO AS CONS_NO,C.CONS_NAME AS CONS_NAME ,TMNL.Terminal_Addr AS TERMINAL_ADDR,ATOI.TMNL_ASSET_NO AS TMNL_ASSET_NO,\n" + 
			"ATOI.IS_ONLINE AS IS_ONLINE,ATOI.ONOFF_TIME AS ONOFF_TIME FROM\n" + 
			"E_DATA_MP EDM,R_TMNL_RUN TMNL,A_TMNL_ONLINE_INFO ATOI,C_CONS C,\n" + 
			"O_ORG O WHERE EDM.Terminal_Addr=TMNL.Terminal_Addr AND\n" + 
			" EDM.CONS_NO=C.CONS_NO AND ATOI.Terminal_Addr=TMNL.Terminal_Addr\n" + 
			"AND O.ORG_NO=EDM.ORG_NO\n" + 
			"AND C.CONS_NO=?\n" + 
			 "AND TMNL.Terminal_Addr=?\n"+
			"AND ATOI.ONOFF_TIME>=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') AND\n" + 
			"ATOI.ONOFF_TIME<=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')) C";

		logger.debug(sql);
		
		// OnoffStat oos=(OnoffStat) super.getJdbcTemplate().queryForObject(sql,
		// new OnOffMapper());
		oosList = super.getJdbcTemplate().query(sql,new Object[]{ consNo, terminalAddr, dateStart, dateEnd }, new OnOffMapper());
		}
		else{
			String sql = 
				"SELECT  SUM(DECODE(C.IS_ONLINE,1,1,0,0,0)) AS onlineNum , SUM(DECODE(C.IS_ONLINE,0,1,1,0,0)) AS offlineNum\n" +
				"FROM (SELECT DISTINCT O.ORG_NAME AS ORG_NAME,\n" + 
				"C.CONS_NO AS CONS_NO,C.CONS_NAME AS CONS_NAME ,TMNL.Terminal_Addr AS TERMINAL_ADDR,ATOI.TMNL_ASSET_NO AS TMNL_ASSET_NO,\n" + 
				"ATOI.IS_ONLINE AS IS_ONLINE,ATOI.ONOFF_TIME AS ONOFF_TIME FROM\n" + 
				"E_DATA_MP EDM,R_TMNL_RUN TMNL,A_TMNL_ONLINE_INFO ATOI,C_CONS C,\n" + 
				"O_ORG O WHERE EDM.Terminal_Addr=TMNL.Terminal_Addr AND\n" + 
				" EDM.CONS_NO=C.CONS_NO AND ATOI.Terminal_Addr=TMNL.Terminal_Addr\n" + 
				"AND O.ORG_NO=EDM.ORG_NO\n" + 
				"AND C.CONS_NO=?\n" + 
				"AND ATOI.ONOFF_TIME>=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') AND\n" + 
				"ATOI.ONOFF_TIME<=TO_DATE(?,'YYYY-MM-DD HH24:MI:SS')) C";
		logger.debug(sql);
		
		// OnoffStat oos=(OnoffStat) super.getJdbcTemplate().queryForObject(sql,
		// new OnOffMapper());
		oosList = super.getJdbcTemplate().query(sql,new Object[]{ consNo,dateStart, dateEnd }, new OnOffMapper());
		}
		// oosList.add(oos);
		// super.getJdbcTemplate().q
		if(null==oosList){
			oosList=new ArrayList<OnoffStat>();
			OnoffStat oos=new OnoffStat();
			oos.setOffNum(0);
			oos.setOnNum(0);
			oosList.add(oos);
		}
		return oosList;

	}

	class OnOffMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			OnoffStat oos = new OnoffStat();
			try {
				oos.setOnNum(rs.getInt("onlineNum"));
				oos.setOffNum(rs.getInt("offlineNum"));
				System.out.println(oos.getOnNum());
				return oos;
			} catch (Exception e) {
				return null;
			}
			// return oos;
		}
	}

	// class HeartBeatMapper implements RowMapper {
	// @Override
	// public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
	// HeartBeat hb = new HeartBeat();
	// try {
	// hb.setOrgName(rs.getString("ORG_NAME"));
	// hb.setConsNo(rs.getString("CONS_NO"));
	// hb.setConsName(rs.getString("CONS_NAME"));
	// hb.setTmnlAddr(rs.getString("Terminal_Addr"));
	// hb.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
	// hb.setIsOnLine(rs.getString("IS_ONLINE"));
	// hb.setOnOffTime(DateUtil.timeStampToStr(rs.getTimestamp("ONOFF_TIME")));
	// return hb;
	// } catch (Exception e) {
	// log.debug("实体映射出错");
	// return null;
	// }
	// }
	// }

	class OrigFrameQryTmnlRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			OrigFrameQryAsset origframeqryasset = new OrigFrameQryAsset();
			try {
				origframeqryasset.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				return origframeqryasset;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
