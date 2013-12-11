package com.nari.runman.archivesman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.cp.REODev;
import com.nari.runcontrol.RcpParaJdbc;
import com.nari.runcontrol.RcpRunJdbc;
import com.nari.runman.archivesman.IArchieveManDao;
import com.nari.runman.archivesman.IArchiveRcpManDao;
import com.nari.runman.archivesman.impl.ArchiveCmeterHangDaoImpl.CMeterRowMapper;
import com.nari.runman.runstatusman.CMeterDto;
import com.nari.runman.runstatusman.RCollObj;
import com.nari.support.Page;

/**
 * DAO 实现 ArchiveRcpManDaoImpl
 * 
 * @author zhangzhw
 * @describe 采集点DAO实现
 * @modied ：PROTOCOL_CODE 改为 PROTOCOL_TYPE_CODE
 */
public class ArchiveRcpManDaoImpl extends JdbcBaseDAOImpl implements
		IArchiveRcpManDao {

	IArchieveManDao archieveManDao;

	/**
	 * 通过 consId 查询Rcp
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RcpRunJdbc> queryRcpByconsId(String consId) {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT CP_NO,\n");
		sb.append("       NAME,\n");
		sb.append("       CP_TYPE_CODE,\n");
		sb.append("       STATUS_CODE,\n");
		sb.append("       CP_ADDR,\n");
		sb.append("       GPS_LONGITUDE,\n");
		sb.append("       GPS_LATITUDE\n");
		sb.append("  FROM R_CP\n");
		sb.append(" WHERE EXISTS (SELECT 1\n");
		sb.append("          FROM R_CP_CONS_RELA RR\n");
		sb.append("         WHERE RR.CP_NO = R_CP.CP_NO\n");
		sb.append("           AND RR.CONS_ID = ?) \n");
		sb.append("   ORDER BY R_CP.CP_NO ");

		String sql = sb.toString();

		return super.getJdbcTemplate().query(sql, new Object[] { consId },
				new RcpRunRowMapper());
	}

	/**
	 * 通过consId 查询 Rcp 参数
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RcpParaJdbc> queryRcpParaByconsId(String consId) {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT RP.COMM_PARA_ID,\n");
		sb.append("       RP.CP_NO,\n");
		sb.append("       RP.TERMINAL_ADDR,\n");
		sb.append("       RP.PROTOCOL_TYPE_CODE,\n");
		sb.append("       RP.CHANNEL_NO,\n");
		sb.append("       RP.RTS_ON,\n");
		sb.append("       RP.RTS_OFF,\n");
		sb.append("       RP.TRANSMIT_DELAY,\n");
		sb.append("       RP.RESP_TIMEOUT,\n");
		sb.append("       RP.MASTER_IP,\n");
		sb.append("       RP.MASTER_PORT,\n");
		sb.append("       RP.SPARE_IP_ADDR,\n");
		sb.append("       RP.SPARE_PORT,\n");
		sb.append("       RP.GATEWAY_IP,\n");
		sb.append("       RP.GATEWAY_PORT,\n");
		sb.append("       RP.PROXY_IP_ADDR,\n");
		sb.append("       RP.PROXY_PORT,\n");
		sb.append("       RP.GPRS_CODE,\n");
		sb.append("       RP.SMS_NO,\n");
		sb.append("       RP.APN,\n");
		sb.append("       RP.HEARTBEAT_CYCLE,\n");
		sb.append("       RP.START_DATE,\n");
		sb.append("       RP.ALG_NO,\n");
		sb.append("       RP.ALG_KEY\n");
		sb.append("  FROM R_CP_COMM_PARA RP\n");
		sb.append(" WHERE EXISTS (SELECT 1\n");
		sb.append("          FROM R_CP           RC,\n");
		sb.append("               R_CP_CONS_RELA RR\n");
		sb.append("         WHERE RC.CP_NO = RR.CP_NO\n");
		sb.append("          AND RC.CP_NO = RP.CP_NO \n");
		sb.append("           AND RR.CONS_ID = ?) \n");
		sb.append(" ORDER BY RP.CP_NO ");

		String sql = sb.toString();

		return super.getJdbcTemplate().query(sql, new Object[] { consId },
				new RcpParaRowMapper());
	}

	/**
	 * 保存 采集点和采集点信息
	 * 
	 * @see 未处理保存前判断记录是否存在 （数据库）
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean saveRcps(RcpRunJdbc[] rcpRunJdbc, RcpParaJdbc[] rcpParaJdbc,
			String consId) {

		List<String> sqlList = new ArrayList<String>();
		List<List> paramList = new ArrayList<List>();

		for (int i = 0; i < rcpRunJdbc.length; i++) {
			if (null == rcpRunJdbc[i].getCpNo()
					|| rcpRunJdbc[i].getCpNo().isEmpty()) {
				sqlList.add(saveRcpSql(rcpRunJdbc[i]));
				paramList.add(saveRcpList(rcpRunJdbc[i]));

				sqlList.add(saveRcpParaSql(rcpParaJdbc[i]));
				paramList.add(saveRcpParaList(rcpParaJdbc[i]));

				// 添加 r_cp_cons_rela
				sqlList.add(saveRcpRelaSql());
				paramList.add(saveRcpRelaParam(consId));

			} else {
				sqlList.add(updateRcpSql(rcpRunJdbc[i]));
				paramList.add(updateRcpList(rcpRunJdbc[i]));

				sqlList.add(updateRcpParaSql(rcpParaJdbc[i]));
				paramList.add(updateRcpParaList(rcpParaJdbc[i]));
			}

		}

		super.doTransaction(sqlList, paramList, "保存采集点信息时出错");
		return true;
	}

	/**
	 * 保存 采集点和采集点信息
	 * 
	 * @see 未处理保存前判断记录是否存在 （数据库）
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean saveRcps(RcpRunJdbc[] rcpRunJdbc,String consId) {

		List<String> sqlList = new ArrayList<String>();
		List<List> paramList = new ArrayList<List>();

		for (int i = 0; i < rcpRunJdbc.length; i++) {
			if (null == rcpRunJdbc[i].getCpNo()
					|| rcpRunJdbc[i].getCpNo().isEmpty()) {
				sqlList.add(saveRcpSql(rcpRunJdbc[i]));
				paramList.add(saveRcpList(rcpRunJdbc[i]));


				// 添加 r_cp_cons_rela
				sqlList.add(saveRcpRelaSql());
				paramList.add(saveRcpRelaParam(consId));

			} else {
				sqlList.add(updateRcpSql(rcpRunJdbc[i]));
				paramList.add(updateRcpList(rcpRunJdbc[i]));
			}

		}
		super.doTransaction(sqlList, paramList, "保存采集点信息时出错");
		return true;
	}
	/**
	 * 方法 saveRcpRelaSql
	 * 
	 * @return 保存RcpRela 的sql
	 */
	private String saveRcpRelaSql() {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO R_CP_CONS_RELA\n");
		sb.append("  (CP_CONS_ID,\n");
		sb.append("   CONS_ID,\n");
		sb.append("   CP_NO)\n");
		sb.append("VALUES\n");
		sb.append("  (S_R_CP_CONS_RELA.NEXTVAL, --CP_CONS_ID,\n");
		sb.append("   ?, --      CONS_ID,\n");
		sb.append("   S_R_CP.CURRVAL --      CP_NO\n");
		sb.append("   )");

		String sql = sb.toString();

		return sql;
	}

	/**
	 * 方法 saveRcpRelaParam
	 * 
	 * @param consId
	 * @return 保存 RcpRela 的SQL参数
	 */
	@SuppressWarnings("unused")
	private List<Object> saveRcpRelaParam(String consId) {
		List<Object> list = new ArrayList<Object>();
		// 对应 SQL 的参数
		list.add(consId);
		return list;
	}

	/**
	 * 方法 saveRcpSql
	 * 
	 * @param rcprunjdbc
	 * @return 生成保存 Rcp 的SQL
	 */
	private String saveRcpSql(RcpRunJdbc rcprunjdbc) {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO R_CP\n");
		sb.append("  (CP_NO,\n");
		sb.append("   NAME,\n");
		sb.append("   CP_TYPE_CODE,\n");
		sb.append("   STATUS_CODE,\n");
		sb.append("   CP_ADDR,\n");
		sb.append("   GPS_LONGITUDE,\n");
		sb.append("   GPS_LATITUDE)\n");
		sb.append("VALUES\n");
		sb.append("  (S_R_CP.NEXTVAL, --CP_NO,\n"); // 对应参数也要改
		sb.append("   ?, --       NAME,\n");
		sb.append("   ?, --       CP_TYPE_CODE,\n");
		sb.append("   ?, --       STATUS_CODE,\n");
		sb.append("   ?, --       CP_ADDR,\n");
		sb.append("   ?, --       GPS_LONGITUDE,\n");
		sb.append("   ? --       GPS_LATITUDE\n");
		sb.append("   )");

		String sql = sb.toString();

		return sql;

	}

	/**
	 * 方法 saveRcpList
	 * 
	 * @param rcprunjdbc
	 * @return 返回保存 Rcp的参数
	 * @describe list 对应SQL 中的参数
	 */
	private List<Object> saveRcpList(RcpRunJdbc rcprunjdbc) {
		List<Object> list = new ArrayList<Object>();
		// list.add(rcprunjdbc.getCpNo()); //对应SQL里的?
		list.add(rcprunjdbc.getName());
		list.add(rcprunjdbc.getCpTypeCode());
		list.add(rcprunjdbc.getStatusCode());
		list.add(rcprunjdbc.getCpAddr());
		list.add(rcprunjdbc.getGpsLongitude());
		list.add(rcprunjdbc.getGpsLatitude());
		return list;
	}

	/**
	 * 方法 updateRcpSql
	 * 
	 * @param rcprunjdbc
	 * @return 更新Rcp的SQL
	 */
	private String updateRcpSql(RcpRunJdbc rcprunjdbc) {

		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE R_CP\n");
		sb.append("   SET NAME = ?,\n");
		sb.append("        CP_TYPE_CODE = ?,\n");
		sb.append("        STATUS_CODE = ?,\n");
		sb.append("        CP_ADDR = ?,\n");
		sb.append("        GPS_LONGITUDE = ?,\n");
		sb.append("        GPS_LATITUDE = ?\n");
		sb.append(" WHERE CP_NO = ?");

		String sql = sb.toString();
		return sql;

	}

	/**
	 * 方法 updateRcpList
	 * 
	 * @param rcprunjdbc
	 * @return 更新Rcp的参数 List
	 */
	private List<Object> updateRcpList(RcpRunJdbc rcprunjdbc) {
		List<Object> list = new ArrayList<Object>();

		list.add(rcprunjdbc.getName());
		list.add(rcprunjdbc.getCpTypeCode());
		list.add(rcprunjdbc.getStatusCode());
		list.add(rcprunjdbc.getCpAddr());
		list.add(rcprunjdbc.getGpsLongitude());
		list.add(rcprunjdbc.getGpsLatitude());
		list.add(rcprunjdbc.getCpNo());

		return list;
	}

	/**
	 * 方法saveRcpPara
	 * 
	 * @param rcpParaJdbc
	 * @return 生成保存Rcp参数的SQL
	 */
	private String saveRcpParaSql(RcpParaJdbc rcpParaJdbc) {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO R_CP_COMM_PARA\n");
		sb.append("  (COMM_PARA_ID,\n");
		sb.append("   CP_NO,\n");
		sb.append("   TERMINAL_ADDR,\n");
		sb.append("   PROTOCOL_TYPE_CODE,\n");
		sb.append("   CHANNEL_NO,\n");
		sb.append("   RTS_ON,\n");
		sb.append("   RTS_OFF,\n");
		sb.append("   TRANSMIT_DELAY,\n");
		sb.append("   RESP_TIMEOUT,\n");
		sb.append("   MASTER_IP,\n");
		sb.append("   MASTER_PORT,\n");
		sb.append("   SPARE_IP_ADDR,\n");
		sb.append("   SPARE_PORT,\n");
		sb.append("   GATEWAY_IP,\n");
		sb.append("   GATEWAY_PORT,\n");
		sb.append("   PROXY_IP_ADDR,\n");
		sb.append("   PROXY_PORT,\n");
		sb.append("   GPRS_CODE,\n");
		sb.append("   SMS_NO,\n");
		sb.append("   APN,\n");
		sb.append("   HEARTBEAT_CYCLE,\n");
		sb.append("   START_DATE,\n");
		sb.append("   ALG_NO,\n");
		sb.append("   ALG_KEY)\n");
		sb.append("VALUES\n");
		sb.append("  (S_R_CP_COMM_PARA.NEXTVAL, --       COMM_PARA_ID,\n");
		sb.append("   S_R_CP.CURRVAL, --       CP_NO,\n");
		sb.append("   ?, --       TERMINAL_ADDR,\n");
		sb.append("   ?, --       PROTOCOL_CODE,\n");
		sb.append("   ?, --       CHANNEL_NO,\n");
		sb.append("   ?, --       RTS_ON,\n");
		sb.append("   ?, --       RTS_OFF,\n");
		sb.append("   ?, --       TRANSMIT_DELAY,\n");
		sb.append("   ?, --       RESP_TIMEOUT,\n");
		sb.append("   ?, --       MASTER_IP,\n");
		sb.append("   ?, --       MASTER_PORT,\n");
		sb.append("   ?, --       SPARE_IP_ADDR,\n");
		sb.append("   ?, --       SPARE_PORT,\n");
		sb.append("   ?, --       GATEWAY_IP,\n");
		sb.append("   ?, --       GATEWAY_PORT,\n");
		sb.append("   ?, --       PROXY_IP_ADDR,\n");
		sb.append("   ?, --       PROXY_PORT,\n");
		sb.append("   ?, --       GPRS_CODE,\n");
		sb.append("   ?, --       SMS_NO,\n");
		sb.append("   ?, --       APN,\n");
		sb.append("   ?, --       HEARTBEAT_CYCLE,\n");
		sb.append("   ?, --       START_DATE,\n");
		sb.append("   ?, --       ALG_NO,\n");
		sb.append("   ? --       ALG_KEY\n");
		sb.append("   )");

		String sql = sb.toString();

		return sql;

	}

	/**
	 * 方法 saveRcpParaList
	 * 
	 * @param rcpParaJdbc
	 * @return 生成保存 Rcp参数的 List
	 */
	private List<Object> saveRcpParaList(RcpParaJdbc rcpParaJdbc) {
		List<Object> list = new ArrayList<Object>();
		// list.add(rcpParaJdbc.getCommParaId()); 对应SQL的参数
		// list.add(rcpParaJdbc.getCpNo());
		list.add(rcpParaJdbc.getTerminalAddr());
		list.add(rcpParaJdbc.getProtocolCode());
		list.add(rcpParaJdbc.getChannelNo());
		list.add(rcpParaJdbc.getRtsOn());
		list.add(rcpParaJdbc.getRtsOff());
		list.add(rcpParaJdbc.getTransmitDelay());
		list.add(rcpParaJdbc.getRespTimeout());
		list.add(rcpParaJdbc.getMasterIp());
		list.add(rcpParaJdbc.getMasterPort());
		list.add(rcpParaJdbc.getSpareIpAddr());
		list.add(rcpParaJdbc.getSparePort());
		list.add(rcpParaJdbc.getGatewayIp());
		list.add(rcpParaJdbc.getGatewayPort());
		list.add(rcpParaJdbc.getProxyIpAddr());
		list.add(rcpParaJdbc.getProxyPort());
		list.add(rcpParaJdbc.getGprsCode());
		list.add(rcpParaJdbc.getSmsNo());
		list.add(rcpParaJdbc.getApn());
		list.add(rcpParaJdbc.getHeartbeatCycle());
		list.add(rcpParaJdbc.getStartDate());
		list.add(rcpParaJdbc.getAlgNo());
		list.add(rcpParaJdbc.getAlgKey());

		return list;
	}

	/**
	 * 方法 updateRcpParaSql
	 * 
	 * @param rcpParaJdbc
	 * @return 更新Rcp参数的SQL
	 */
	private String updateRcpParaSql(RcpParaJdbc rcpParaJdbc) {

		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE R_CP_COMM_PARA\n");
		sb.append("SET\n");
		sb.append("   CP_NO=?,\n");
		sb.append("   TERMINAL_ADDR=?,\n");
		sb.append("   PROTOCOL_TYPE_CODE=?,\n");
		sb.append("   CHANNEL_NO=?,\n");
		sb.append("   RTS_ON=?,\n");
		sb.append("   RTS_OFF=?,\n");
		sb.append("   TRANSMIT_DELAY=?,\n");
		sb.append("   RESP_TIMEOUT=?,\n");
		sb.append("   MASTER_IP=?,\n");
		sb.append("   MASTER_PORT=?,\n");
		sb.append("   SPARE_IP_ADDR=?,\n");
		sb.append("   SPARE_PORT=?,\n");
		sb.append("   GATEWAY_IP=?,\n");
		sb.append("   GATEWAY_PORT=?,\n");
		sb.append("   PROXY_IP_ADDR=?,\n");
		sb.append("   PROXY_PORT=?,\n");
		sb.append("   GPRS_CODE=?,\n");
		sb.append("   SMS_NO=?,\n");
		sb.append("   APN=?,\n");
		sb.append("   HEARTBEAT_CYCLE=?,\n");
		sb.append("   START_DATE=?,\n");
		sb.append("   ALG_NO=?,\n");
		sb.append("   ALG_KEY=?\n");
		sb.append("WHERE COMM_PARA_ID=?");

		String sql = sb.toString();
		return sql;

	}

	/**
	 * 方法 updateRcpParaList
	 * 
	 * @param rcpParaJdbc
	 * @return 更新Rcp参数 的SQL参数List
	 */
	private List<Object> updateRcpParaList(RcpParaJdbc rcpParaJdbc) {
		List<Object> list = new ArrayList<Object>();

		list.add(rcpParaJdbc.getCpNo());
		list.add(rcpParaJdbc.getTerminalAddr());
		list.add(rcpParaJdbc.getProtocolCode());
		list.add(rcpParaJdbc.getChannelNo());
		list.add(rcpParaJdbc.getRtsOn());
		list.add(rcpParaJdbc.getRtsOff());
		list.add(rcpParaJdbc.getTransmitDelay());
		list.add(rcpParaJdbc.getRespTimeout());
		list.add(rcpParaJdbc.getMasterIp());
		list.add(rcpParaJdbc.getMasterPort());
		list.add(rcpParaJdbc.getSpareIpAddr());
		list.add(rcpParaJdbc.getSparePort());
		list.add(rcpParaJdbc.getGatewayIp());
		list.add(rcpParaJdbc.getGatewayPort());
		list.add(rcpParaJdbc.getProxyIpAddr());
		list.add(rcpParaJdbc.getProxyPort());
		list.add(rcpParaJdbc.getGprsCode());
		list.add(rcpParaJdbc.getSmsNo());
		list.add(rcpParaJdbc.getApn());
		list.add(rcpParaJdbc.getHeartbeatCycle());
		list.add(rcpParaJdbc.getStartDate());
		list.add(rcpParaJdbc.getAlgNo());
		list.add(rcpParaJdbc.getAlgKey());
		list.add(rcpParaJdbc.getCommParaId());

		return list;
	}

	/**
	 * 内部类 RcpRunRowMapper
	 * 
	 * @author zhangzhw
	 * 
	 */
	class RcpRunRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RcpRunJdbc rcprunjdbc = new RcpRunJdbc();
			try {
				rcprunjdbc.setCpNo(rs.getString("CP_NO"));
				rcprunjdbc.setName(rs.getString("NAME"));
				rcprunjdbc.setCpTypeCode(rs.getString("CP_TYPE_CODE"));
				rcprunjdbc.setStatusCode(rs.getString("STATUS_CODE"));
				rcprunjdbc.setCpAddr(rs.getString("CP_ADDR"));
				rcprunjdbc.setGpsLongitude(rs.getString("GPS_LONGITUDE"));
				rcprunjdbc.setGpsLatitude(rs.getString("GPS_LATITUDE"));
				return rcprunjdbc;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 内部类 RcpParaRowMapper
	 * 
	 * @author zhangzhw 采集点通讯参数映射
	 */
	class RcpParaRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RcpParaJdbc rcpparajdbc = new RcpParaJdbc();
			try {
				rcpparajdbc.setCommParaId(rs.getLong("COMM_PARA_ID"));
				rcpparajdbc.setCpNo(rs.getString("CP_NO"));
				rcpparajdbc.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				rcpparajdbc.setProtocolCode(rs.getString("PROTOCOL_TYPE_CODE"));
				rcpparajdbc.setChannelNo(rs.getString("CHANNEL_NO"));
				rcpparajdbc.setRtsOn(rs.getInt("RTS_ON"));
				rcpparajdbc.setRtsOff(rs.getInt("RTS_OFF"));
				rcpparajdbc.setTransmitDelay(rs.getInt("TRANSMIT_DELAY"));
				rcpparajdbc.setRespTimeout(rs.getInt("RESP_TIMEOUT"));
				rcpparajdbc.setMasterIp(rs.getString("MASTER_IP"));
				rcpparajdbc.setMasterPort(rs.getInt("MASTER_PORT"));
				rcpparajdbc.setSpareIpAddr(rs.getString("SPARE_IP_ADDR"));
				rcpparajdbc.setSparePort(rs.getInt("SPARE_PORT"));
				rcpparajdbc.setGatewayIp(rs.getString("GATEWAY_IP"));
				rcpparajdbc.setGatewayPort(rs.getInt("GATEWAY_PORT"));
				rcpparajdbc.setProxyIpAddr(rs.getString("PROXY_IP_ADDR"));
				rcpparajdbc.setProxyPort(rs.getInt("PROXY_PORT"));
				rcpparajdbc.setGprsCode(rs.getString("GPRS_CODE"));
				rcpparajdbc.setSmsNo(rs.getString("SMS_NO"));
				rcpparajdbc.setApn(rs.getString("APN"));
				rcpparajdbc.setHeartbeatCycle(rs.getInt("HEARTBEAT_CYCLE"));
				rcpparajdbc.setStartDate(rs.getTimestamp("START_DATE"));
				rcpparajdbc.setAlgNo(rs.getString("ALG_NO"));
				rcpparajdbc.setAlgKey(rs.getString("ALG_KEY"));
				return rcpparajdbc;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteRcp(String cpNo){
		//首先查询此采集点下是否含有运行终端，如果含有运行终端，则不进行删除
		String sql = "SELECT COUNT(C.TERMINAL_ID) FROM R_TMNL_RUN C WHERE C.CP_NO = ?";
		int flag = super.getJdbcTemplate().queryForInt(sql, new Object[]{cpNo});
		if(flag >=1){//如果采集点下存在运行终端
			return -1;//返回-1:
		}else{
			List<String> sqlList = new ArrayList<String>();
			List<List> paramList = new ArrayList<List>();
			List cpprame = new ArrayList();
			cpprame.add(cpNo);
			//step1:删除与此采集点相关的负控开关轮次
			//DELETE FROM R_LC_SWITCH_TURNS R WHERE R.CP_NO = ?
			sqlList.add("DELETE FROM R_LC_SWITCH_TURNS R WHERE R.CP_NO = ?");
			paramList.add(cpprame);
			//STEP2:删除与此采集点相关的采集对象 
			//DELETE FROM R_COLL_OBJ RC WHERE RC.CP_NO = ?
			sqlList.add("DELETE FROM R_COLL_OBJ RC WHERE RC.CP_NO = ?");
			paramList.add(cpprame);
			//STEP3:删除与此采集点相关的开关量参数
			//DELETE FROM R_CP_SWITCH_PARA RC WHERE RC.CP_NO=?
			sqlList.add("DELETE FROM R_CP_SWITCH_PARA RC WHERE RC.CP_NO=?");
			paramList.add(cpprame);
			//STEP4:删除与此采集点相关的采集点控制参数
			//DELETE FROM R_CP_CTRL_PARA RC WHERE RC.Cp_No=?
			sqlList.add("DELETE FROM R_CP_CTRL_PARA RC WHERE RC.Cp_No=?");
			paramList.add(cpprame);
			//STEP5:删除与此采集点相关的采集点通信参数 
			//DELETE FROM R_CP_COMM_PARA RC WHERE RC.Cp_No=?
			sqlList.add("DELETE FROM R_CP_COMM_PARA RC WHERE RC.Cp_No=?");
			paramList.add(cpprame);
			//STEP6:删除与此采集点相关的采集用户关系 
			//DELETE FROM R_CP_CONS_RELA RC WHERE RC.Cp_No=?
			sqlList.add("DELETE FROM R_CP_COMM_PARA RC WHERE RC.Cp_No=?");
			paramList.add(cpprame);
			//STEP7:删除与此采集点相关的采集计量关系 
			//DELETE FROM R_CP_MP_RELA RC WHERE RC.Cp_No=?
			sqlList.add("DELETE FROM R_CP_MP_RELA RC WHERE RC.Cp_No=?");
			paramList.add(cpprame);
			//STEP8:删除与此采集点相关的采集点勘查信息
			//DELETE FROM R_CP_SURVEY RC WHERE RC.Cp_No=?
			sqlList.add("DELETE FROM R_CP_SURVEY RC WHERE RC.Cp_No=?");
			paramList.add(cpprame);
			//STEP9:删除与采集点相关的采集点缺陷信息
			//DELETE FROM R_CP_FAULT RC WHERE RC.Cp_No=?
			sqlList.add("DELETE FROM R_CP_FAULT RC WHERE RC.Cp_No=?");
			paramList.add(cpprame);
			//STEP10:删除与采集点相关的采集器信息
			//DELETE FROM R_EXEC_OTHER_DEV RC WHERE RC.CP_NO=?
			sqlList.add("DELETE FROM R_EXEC_OTHER_DEV RC WHERE RC.CP_NO=?");
			paramList.add(cpprame);
			//STEP11:删除此采集点数据
			//DELETE FROM R_CP  R WHERE R.Cp_No=?
			sqlList.add("DELETE FROM R_CP  R WHERE R.Cp_No=?");
			
			
			paramList.add(cpprame);
			if(super.doTransaction(sqlList, paramList)==1){
				return 1;
			}else{
				return 0;
			}
		}
	}
	
	
	/**
	 * 根据采集点标识查询此采集点下的所有采集器信息
	 * @param cpno 采集点标识
	 * @return 此采集点下所有采集器List
	 */
	@SuppressWarnings("unchecked")
	public List<REODev> queryREODev(String cpno){
		String sql = "SELECT RD.COLLECTOR_ID ,RD.CP_NO ,RD.PRE_ID,RD.COLL_MODE ," +
				"RD.COMM_ADDR ,RD.PORT_NO  ,RD.DEV_MODE,RD.DEV_TYPE_CODE,RD.STATUS_CODE,RD.INST_DATE,RD.INSTALL_LOC," +
				"RD.HOUSE_NO,RD.FLOOR_NO,RD.DOOR_NO ,RD.IP_ADDR ,RD.IP_PORT    FROM R_EXEC_OTHER_DEV  RD WHERE RD.CP_NO=?";
		return super.getJdbcTemplate().query(sql, new Object[] { cpno },new REODevRowMapper());
	}
	
	
	/**
	 * 内部类 解析REODev采集器实体
	 * @author Administrator
	 *
	 */
	class REODevRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int rownum) throws SQLException {
			REODev re = new REODev();
			try {
				re.setCollectorid(rs.getLong("COLLECTOR_ID"));
				re.setCpno(rs.getString("CP_NO"));
				re.setPreid(rs.getLong("PRE_ID"));
				re.setCollmode(rs.getString("COLL_MODE"));
				re.setCommaddr(rs.getString("COMM_ADDR"));
				re.setPortno(rs.getString("PORT_NO"));
				re.setDevmode(rs.getString("DEV_MODE"));
				re.setDevtypecode(rs.getString("DEV_TYPE_CODE"));
				re.setStatuscode(rs.getString("STATUS_CODE"));
				re.setInstdate(rs.getDate("INST_DATE"));
				re.setInstallloc(rs.getString("INSTALL_LOC"));
				re.setHouseno(rs.getString("HOUSE_NO"));
				re.setFloorno(rs.getString("FLOOR_NO"));
				re.setDoorno(rs.getString("DOOR_NO"));
				re.setIpaddr(rs.getString("IP_ADDR"));
				re.setIpport(rs.getInt("IP_PORT"));
				return re;
			} catch (Exception e) {
				return null;
			}
		}
		
	}
	
	/**
	 * 保存采集器
	 * @param reodev 要进行保存的采集器实体
	 * @return 成功返回1 失败返回0
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveREODev(REODev reodev) throws Exception{
		List<String> sqlList = new ArrayList<String>();
		List<List> paramList = new ArrayList<List>();
		String inserSql = "INSERT INTO R_EXEC_OTHER_DEV (" +
				"COLLECTOR_ID,CP_NO,PRE_ID,COLL_MODE,COMM_ADDR,PORT_NO,DEV_MODE," +
				"DEV_TYPE_CODE,STATUS_CODE,INST_DATE,INSTALL_LOC,HOUSE_NO,FLOOR_NO,DOOR_NO," +
				"IP_ADDR,IP_PORT) VALUES (S_R_EXEC_OTHER_DEV.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		sqlList.add(inserSql);
		
		List<Object> list = new ArrayList<Object>();
		
		list.add(reodev.getCpno());
		list.add(reodev.getPreid());
		list.add(reodev.getCollmode());
		list.add(reodev.getCommaddr());
		list.add(reodev.getPortno());
		list.add(reodev.getDevmode());
		list.add(reodev.getDevtypecode());
		list.add(reodev.getStatuscode());
		list.add(reodev.getInstdate());
		list.add(reodev.getInstallloc());
		list.add(reodev.getHouseno());
		list.add(reodev.getFloorno());
		list.add(reodev.getDoorno());
		list.add(reodev.getIpaddr());
		list.add(reodev.getIpport());
		
		paramList.add(list);
		
		if(super.doTransaction(sqlList, paramList)==1){
			return 1;
		}
		return 0;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int updateREODev(REODev[] reodev) {
		if(null==reodev || reodev.length <=0){
			return 0;
		}
		List<String> sqlList = new ArrayList<String>();
		List<List> paramList = new ArrayList<List>();
		String sql = "update r_exec_other_dev "+                
					"   set   "+
					"       cp_no = ?, "+
					"       pre_id = ?,"+
					"       coll_mode = ?, "+
					"       comm_addr = ?,"+
					"       port_no = ?,"+
					"       dev_mode = ?,"+
					"       dev_type_code = ?,"+
					"       status_code = ?,"+
					"       inst_date = ?,"+
					"       install_loc = ?,"+
					"       house_no = ?,"+
					"       floor_no = ?,"+
					"       door_no = ?,"+
					"       ip_addr = ?,"+
					"       ip_port = ? "+
					" where collector_id = ?     ";
		sqlList.add(sql);
		REODev re = reodev[0];
		
		List<Object> list = new ArrayList<Object>();
		list.add(re.getCpno());
		list.add(re.getPreid());
		list.add(re.getCollmode());
		list.add(re.getCommaddr());
		list.add(re.getPortno());
		list.add(re.getDevmode());
		list.add(re.getDevtypecode());
		list.add(re.getStatuscode());
		list.add(re.getInstdate());
		list.add(re.getInstallloc());
		list.add(re.getHouseno());
		list.add(re.getFloorno());
		list.add(re.getDoorno());
		list.add(re.getIpaddr());
		list.add(re.getIpport());
		list.add(re.getCollectorid()); 
		
		paramList.add(list);
		if(super.doTransaction(sqlList, paramList)==1){
			return 1;
		}
		return 0;
	}

	/**
	 * 删除采集器信息
	 * @param collId 要删除的采集器标识
	 * @return 成功返回1 失败返回0
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteREODev(String collId) throws Exception{
		List<String> sqlList = new ArrayList<String>();
		List<List> paramList = new ArrayList<List>();
		List cpprame = new ArrayList();
		cpprame.add(collId);
		sqlList.add("DELETE FROM R_EXEC_OTHER_DEV RC WHERE RC.COLLECTOR_ID=?");
		paramList.add(cpprame);
		if(super.doTransaction(sqlList, paramList)==1){
			return 1;
		}
		return 0;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<CMeterDto> queryCmeterIsHang(String cpNo,long start,int limit) {
		
		String sql = "select cm.meter_id,cc.cons_no,cc.cons_name,cm.inst_loc,cc.mr_sect_no,cm.tmnl_asset_no,cm.fmr_asset_no,cm.asset_no,rco.coll_obj_id " +
				    "   from sea.c_meter cm "+
					"	left join sea.c_cons cc on cm.cons_no = cc.cons_no"+
					"	inner join sea.r_Coll_Obj rco on rco.meter_id = cm.meter_id where rco.cp_no = ?";
		
		return super.pagingFind(sql, start, limit, new CMeterRowMapper(),new Object[]{cpNo});
		//super.getJdbcTemplate().query(sql, new Object[]{cpNo}, new CMeterRowMapper());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<CMeterDto> queryCmeterIsNotHang(String cpNo,long start,int limit) throws Exception{
		String sql = "select cm.meter_id,cc.cons_no,cc.cons_name,cm.inst_loc,cc.mr_sect_no,cm.tmnl_asset_no,cm.fmr_asset_no,cm.asset_no,rco.coll_obj_id " +
	    "   from sea.c_meter cm "+
		"	left join sea.c_cons cc on cm.cons_no = cc.cons_no"+
		"	left join sea.r_Coll_Obj rco on rco.meter_id = cm.meter_id where  rco.meter_id is  null ";

		return super.pagingFind(sql, start, limit, new CMeterRowMapper());
		//return super.getJdbcTemplate().query(sql, new CMeterRowMapper());
	}
	
	class CMeterRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			CMeterDto cMeterDto = new CMeterDto();
			try {
				cMeterDto.setMeterid(rs.getLong("meter_id"));
				cMeterDto.setConsno(rs.getString("cons_no"));
				cMeterDto.setConsname(rs.getString("cons_name"));
				cMeterDto.setInstloc(rs.getString("inst_loc"));
				cMeterDto.setMrsectno(rs.getString("mr_sect_no"));
				cMeterDto.setTmnlassetno(rs.getString("tmnl_asset_no"));
				cMeterDto.setFmrassetno(rs.getString("fmr_asset_no"));
				cMeterDto.setAssetno(rs.getString("asset_no"));
				cMeterDto.setCollobjid(rs.getString("coll_obj_id"));
				return cMeterDto;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	/**
	 * 保存采集对象信息
	 * @param rco 要进行保存的采集对象
	 * @return 成功:返回采集对象标识  失败:返回为空
	 * @throws Exception
	 */
	@Override
	public String saveCmeterHangInfo(RCollObj rco) throws Exception{
		
		try {
			JdbcTemplate jdbc = super.getJdbcTemplate();
			int number = jdbc.queryForInt("select sea.s_r_coll_obj.nextval from r_coll_obj r where  rownum = 1");
			String sql = "insert into r_coll_obj "+
			 " (coll_obj_id, meter_id, cp_no, coll_port, ct_ratio, pt_ratio, t_factor, meter_const, port_no, pulse_attr) "+
			 "	values "+
			 " ("+number+", "+rco.getMeterId()+", \'"+rco.getCpNo()+"\', "+rco.getCollPort()+", "+rco.getCtRatio()+", "+rco.getPtRatio()+", "+rco.gettFactor()+", "+rco.getMeterConst()+", "+rco.getPortNo()+", '"+rco.getPulseAttr()+"') ";
			jdbc.update(sql);
			return String.valueOf(number);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public int deleteCmeterHang(String collobjid) throws Exception {
		String sql = "delete from r_coll_obj r where r.coll_obj_id ="+collobjid;
		try {
			super.getJdbcTemplate().update(sql);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	};
	// getters and setters
	public IArchieveManDao getArchieveManDao() {
		return archieveManDao;
	}

	public void setArchieveManDao(IArchieveManDao archieveManDao) {
		this.archieveManDao = archieveManDao;
	}

}
