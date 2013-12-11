package com.nari.runman.archivesman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.runcontrol.RcpParaJdbc;
import com.nari.runcontrol.RtmnlRunJdbc;
import com.nari.runman.archivesman.IArchiveTmnlManDao;

/**
 * DAO实现类 ArchiveTmnlManDaoImpl
 * 
 * @author zhangzhw
 * @describe 档案管理终端管理实现类
 * 
 */
public class ArchiveTmnlManDaoImpl extends JdbcBaseDAOImpl implements
		IArchiveTmnlManDao {

	/**
	 * 通过consNo查询终端列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RtmnlRunJdbc> queryTmnlByConsNo(String consNo) {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT TERMINAL_ID,\n");
		sb.append("       CP_NO,\n");
		sb.append("       TMNL_ASSET_NO,\n");
		sb.append("       TERMINAL_ADDR,\n");
		sb.append("       CIS_ASSET_NO,\n");
		sb.append("       SIM_NO,\n");
		sb.append("       ID,\n");
		sb.append("       FACTORY_CODE,\n");
		sb.append("       ATTACH_METER_FLAG,\n");
		sb.append("       TERMINAL_TYPE_CODE,\n");
		sb.append("       COLL_MODE,\n");
		sb.append("       PROTOCOL_CODE,\n");
		sb.append("       COMM_PASSWORD,\n");
		sb.append("       RUN_DATE,\n");
		sb.append("       STATUS_CODE,\n");
		sb.append("       HARMONIC_DEV_FLAG,\n");
		sb.append("       PS_ENSURE_FLAG,\n");
		sb.append("       AC_SAMPLING_FLAG,\n");
		sb.append("       ELIMINATE_FLAG,\n");
		sb.append("       GATE_ATTR_FLAG,\n");
		sb.append("       PRIO_PS_MODE,\n");
		sb.append("       FREEZE_MODE,\n");
		sb.append("       FREEZE_CYCLE_NUM,\n");
		sb.append("       MAX_LOAD_CURVE_DAYS,\n");
		sb.append("       PS_LINE_LEN,\n");
		sb.append("       WORK_PS,\n");
		sb.append("       SPEAKER_FLAG,\n");
		sb.append("       SPEAKER_DIST,\n");
		sb.append("       SEND_UP_MODE,\n");
		sb.append("       COMM_MODE,\n");
		sb.append("       FRAME_NUMBER,\n");
		sb.append("       POWER_CUT_DATE,\n");
		sb.append("       INSTL_LOC_DIAGRAM,\n");
		sb.append("       WIRING_DIAGRAM\n");
		sb.append("  FROM R_TMNL_RUN R\n");
		sb.append(" WHERE EXISTS (SELECT 1\n");
		sb.append("          FROM R_CONS_TMNL_RELA RC\n");
		sb.append("         WHERE RC.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n");
		sb.append("           AND RC.CONS_NO = ?)");

		String sql = sb.toString();

		return super.getJdbcTemplate().query(sql, new Object[] { consNo },
				new RTmnlRunRowMapper());
	}

	
	/**
	 * 保存终端列表
	 * 
	 * @未处理重复的情况
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean saveTmnls(String consNo,RtmnlRunJdbc[] rtmnlRunJdbc,RcpParaJdbc[] rcpParaJdbc) {

		List<String> sqlList = new ArrayList<String>();
		List<List> paramList = new ArrayList<List>();
		for (int i = 0; i < rtmnlRunJdbc.length; i++) {
			String tmnlId = rtmnlRunJdbc[i].getTerminalId();
			if (null == tmnlId || tmnlId.isEmpty()) {
				sqlList.add(saveTmnlSql(rtmnlRunJdbc[i]));
				paramList.add(saveTmnlList(rtmnlRunJdbc[i]));
				
				sqlList.add(saveTmnlConsRelaSql());
				paramList.add(saveTmnlConsRelaList(consNo,rtmnlRunJdbc[i]));

			} else {
				sqlList.add(updateTmnlSql(rtmnlRunJdbc[i]));
				paramList.add(updateTmnlList(rtmnlRunJdbc[i]));
			}
		}

		if(null!=rcpParaJdbc){
			for (int i = 0; i < rcpParaJdbc.length; i++) {
				Long tmnlId = rcpParaJdbc[i].getCommParaId();
				if (null == tmnlId ) {
					sqlList.add(saveTmnlParamSql(rcpParaJdbc[i]));
					paramList.add(saveTmnlParamList(rcpParaJdbc[i]));
				} else {
					sqlList.add(updateTmnlParamSql());
					paramList.add(updateTmnlParamList(rcpParaJdbc[i]));
				}
			}
		}
		super.doTransaction(sqlList, paramList);

		return true;
	}

	private List updateTmnlParamList(RcpParaJdbc rj) {
		List<Object> list = new ArrayList<Object>();
		list.add(rj.getCpNo());
		list.add(rj.getTerminalAddr());
		list.add(rj.getProtocolCode());
		list.add(rj.getChannelNo());
		list.add(rj.getRtsOn());
		list.add(rj.getRtsOff());
		list.add(rj.getTransmitDelay());
		list.add(rj.getRespTimeout());
		list.add(rj.getMasterIp());
		list.add(rj.getMasterPort());
		list.add(rj.getSpareIpAddr());
		list.add(rj.getSparePort());
		list.add(rj.getGatewayIp());
		list.add(rj.getGatewayPort());
		list.add(rj.getProxyIpAddr());
		list.add(rj.getProxyPort());
		list.add(rj.getGprsCode());
		list.add(rj.getSmsNo());
		list.add(rj.getApn());
		list.add(rj.getHeartbeatCycle()==null?0:rj.getHeartbeatCycle());
		list.add(rj.getStartDate());
		list.add(rj.getAlgNo());
		list.add(rj.getAlgKey());
		list.add(rj.getCommParaId());
		return list;
	}

	private String updateTmnlParamSql(){
		String sql = "update r_cp_comm_para "+
			   	 " set cp_no = ?, "+
			     " terminal_addr = ?,   "+
			     " protocol_type_code = ?,"+
			     " channel_no = ?,  "+
			     " rts_on = ?,   "+
			     " rts_off = ?, "+
			     " transmit_delay = ?, "+
			     " resp_timeout = ?,     "+
			     " master_ip = ?,    "+
			     " master_port = ?,"+
			     " spare_ip_addr = ?,   "+
			     " spare_port = ?,  "+
			     " gateway_ip = ?,  "+
			     " gateway_port = ?,     "+
			     " proxy_ip_addr = ?,   "+
			     " proxy_port = ?,  "+
			     " gprs_code = ?,    "+
			     " sms_no = ?,   "+
			     " apn = ?,  "+
			     " heartbeat_cycle = ?,"+
			     " start_date = ?,  "+
			     " alg_no = ?,   "+
			     " alg_key = ?  "+
			     " where comm_para_id = ?";
		return sql;
	}
	
	private List saveTmnlParamList(RcpParaJdbc rj) {
		List<Object> list = new ArrayList<Object>();
		//list.add(rj.getCommParaId());
		list.add(rj.getCpNo());
		list.add(rj.getTerminalAddr());
		list.add(rj.getProtocolCode());
		list.add(rj.getChannelNo());
		list.add(rj.getRtsOn());
		list.add(rj.getRtsOff());
		list.add(rj.getTransmitDelay());
		list.add(rj.getRespTimeout());
		list.add(rj.getMasterIp());
		list.add(rj.getMasterPort());
		list.add(rj.getSpareIpAddr());
		list.add(rj.getSparePort());
		list.add(rj.getGatewayIp());
		list.add(rj.getGatewayPort());
		list.add(rj.getProxyIpAddr());
		list.add(rj.getProxyPort());
		list.add(rj.getGprsCode());
		list.add(rj.getSmsNo());
		list.add(rj.getApn());
		list.add(rj.getHeartbeatCycle()==null?0:rj.getHeartbeatCycle());
		list.add(rj.getStartDate());
		list.add(rj.getAlgNo());
		list.add(rj.getAlgKey());
		return list;
	}

	private String saveTmnlParamSql(RcpParaJdbc rcpParaJdbc) {
				String sql = "INSERT INTO R_CP_COMM_PARA  ("+
							"COMM_PARA_ID,              "+
							"CP_NO,                     "+
							"TERMINAL_ADDR,             "+
							"PROTOCOL_TYPE_CODE,        "+
							"CHANNEL_NO,                "+
							"RTS_ON,                    "+
							"RTS_OFF,                   "+
							"TRANSMIT_DELAY,            "+
							"RESP_TIMEOUT,              "+
							"MASTER_IP,                 "+
							"MASTER_PORT,               "+
							"SPARE_IP_ADDR,             "+
							"SPARE_PORT,                "+
							"GATEWAY_IP,                "+
							"GATEWAY_PORT,              "+
							"PROXY_IP_ADDR,             "+
							"PROXY_PORT,                "+
							"GPRS_CODE,                 "+
							"SMS_NO, APN,               "+
							"HEARTBEAT_CYCLE,           "+
							"START_DATE,                "+
							"ALG_NO,                    "+
							"ALG_KEY)  VALUES  (        "+
							"S_R_CP_COMM_PARA.NEXTVAL,  "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?,                         "+
							"?)                         ";
	return sql;
}

	/**
	 * 方法 saveTmnl
	 * 
	 * @param tmnl
	 * @return 保存终端的SQL
	 */
	private String saveTmnlSql(RtmnlRunJdbc tmnl) {

		String sql = "INSERT INTO  R_TMNL_RUN\n" + "(TERMINAL_ID,\n"
				+ "       CP_NO,\n" + "       TMNL_ASSET_NO,\n"
				+ "       TERMINAL_ADDR,\n" + "       CIS_ASSET_NO,\n"
				+ "       SIM_NO,\n" + "       ID,\n"
				+ "       FACTORY_CODE,\n" + "       ATTACH_METER_FLAG,\n"
				+ "       TERMINAL_TYPE_CODE,\n" + "       COLL_MODE,\n"
				+ "       PROTOCOL_CODE,\n" + "       COMM_PASSWORD,\n"
				+ "       RUN_DATE,\n" + "       STATUS_CODE,\n"
				+ "       HARMONIC_DEV_FLAG,\n" + "       PS_ENSURE_FLAG,\n"
				+ "       AC_SAMPLING_FLAG,\n" + "       ELIMINATE_FLAG,\n"
				+ "       GATE_ATTR_FLAG,\n" + "       PRIO_PS_MODE,\n"
				+ "       FREEZE_MODE,\n" + "       FREEZE_CYCLE_NUM,\n"
				+ "       MAX_LOAD_CURVE_DAYS,\n" + "       PS_LINE_LEN,\n"
				+ "       WORK_PS,\n" + "       SPEAKER_FLAG,\n"
				+ "       SPEAKER_DIST,\n" + "       SEND_UP_MODE,\n"
				+ "       COMM_MODE,\n" + "       FRAME_NUMBER,\n"
				+ "       POWER_CUT_DATE\n" + ")\n" + "VALUES\n" + "(\n"
				+ "S_R_TMNL_RUN.NEXTVAL,--TERMINAL_ID,\n" + "?,--       CP_NO,\n"
				+ "?,--       TMNL_ASSET_NO,\n" + "?,--       TERMINAL_ADDR,\n"
				+ "?,--       CIS_ASSET_NO,\n" + "?,--       SIM_NO,\n"
				+ "?,--       ID,\n" + "?,--       FACTORY_CODE,\n"
				+ "?,--       ATTACH_METER_FLAG,\n"
				+ "?,--       TERMINAL_TYPE_CODE,\n"
				+ "?,--       COLL_MODE,\n" + "?,--       PROTOCOL_CODE,\n"
				+ "?,--       COMM_PASSWORD,\n" + "?,--       RUN_DATE,\n"
				+ "?,--       STATUS_CODE,\n"
				+ "?,--       HARMONIC_DEV_FLAG,\n"
				+ "?,--       PS_ENSURE_FLAG,\n"
				+ "?,--       AC_SAMPLING_FLAG,\n"
				+ "?,--       ELIMINATE_FLAG,\n"
				+ "?,--       GATE_ATTR_FLAG,\n" + "?,--       PRIO_PS_MODE,\n"
				+ "?,--       FREEZE_MODE,\n"
				+ "?,--       FREEZE_CYCLE_NUM,\n"
				+ "?,--       MAX_LOAD_CURVE_DAYS,\n"
				+ "?,--       PS_LINE_LEN,\n" + "?,--       WORK_PS,\n"
				+ "?,--       SPEAKER_FLAG,\n" + "?,--       SPEAKER_DIST,\n"
				+ "?,--       SEND_UP_MODE,\n" + "?,--       COMM_MODE,\n"
				+ "?,--       FRAME_NUMBER,\n" + "?--       POWER_CUT_DATE\n"
				+ ")";

		return sql;

	}

	/**
	 * 方法 saveTmnlsList
	 * 
	 * @param tmnl
	 * @return 保存终端的SQL参数
	 */
	private List<Object> saveTmnlList(RtmnlRunJdbc tmnl) {
		List<Object> list = new ArrayList<Object>();
		//list.add(tmnl.getTerminalId());
		list.add(tmnl.getCpNo());
		list.add(tmnl.getTmnlAssetNo());
		list.add(tmnl.getTerminalAddr());
		list.add(tmnl.getCisAssetNo());
		list.add(tmnl.getSimNo());
		list.add(tmnl.getId());
		list.add(tmnl.getFactoryCode());
		list.add(tmnl.getAttachMeterFlag());
		list.add(tmnl.getTerminalTypeCode());
		list.add(tmnl.getCollMode());
		list.add(tmnl.getProtocolCode());
		list.add(tmnl.getCommPassword());
		list.add(tmnl.getRunDate());
		list.add(tmnl.getStatusCode());
		list.add(tmnl.getHarmonicDevFlag());
		list.add(tmnl.getPsEnsureFlag());
		list.add(tmnl.getAcSamplingFlag());
		list.add(tmnl.getEliminateFlag());
		list.add(tmnl.getGateAttrFlag());
		list.add(tmnl.getPrioPsMode());
		list.add(tmnl.getFreezeMode());
		list.add(tmnl.getFreezeCycleNum());
		list.add(tmnl.getMaxLoadCurveDays());
		list.add(tmnl.getPsLineLen());
		list.add(tmnl.getWorkPs());
		list.add(tmnl.getSpeakerFlag());
		list.add(tmnl.getSpeakerDist());
		list.add(tmnl.getSendUpMode());
		list.add(tmnl.getCommMode());
		list.add(tmnl.getFrameNumber());
		list.add(tmnl.getPowerCutDate());

		return list;
	}
	
	/**
	 * 方法 	saveTmnlConsRelaSql
	 * @param consNo
	 * @param tmnl
	 * @return 保存用户和终端关联的SQL
	 */
	private String saveTmnlConsRelaSql()
	{

		StringBuffer sb=new StringBuffer();
		sb.append("\n");
		sb.append("INSERT INTO R_CONS_TMNL_RELA\n");
		sb.append("  (ORG_NO,\n");
		sb.append("   AREA_NO,\n");
		sb.append("   CONS_NO,\n");
		sb.append("   TMNL_ASSET_NO)\n");
		sb.append("  (SELECT ORG_NO,\n");
		sb.append("          AREA_NO,\n");
		sb.append("          CONS_NO,\n");
		sb.append("          ?\n");
		sb.append("     FROM C_CONS\n");
		sb.append("    WHERE CONS_NO = ?)");

		String sql=sb.toString();

		return sql;
	}
	
	
	
	/**
	 * 方法 saveTmnlConsRelaList
	 * @param consNo
	 * @param tmnl
	 * @return 保存用户和终端 关联的SQL参数
	 */
	private List<Object> saveTmnlConsRelaList(String consNo,RtmnlRunJdbc tmnl)
	{
		List<Object> list=new ArrayList<Object>();
		list.add(tmnl.getTmnlAssetNo());
		list.add(consNo);
		return list;
	}

	/**
	 * 方法　updateTmnl
	 * 
	 * @param tmnl
	 * @return 更新终端的SQL
	 */
	private String updateTmnlSql(RtmnlRunJdbc tmnl) {
		String sql = "UPDATE r_tmnl_run\n" + "SET\n" + "       CP_NO=?,\n"
				+ "       TMNL_ASSET_NO=?,\n" + "       TERMINAL_ADDR=?,\n"
				+ "       CIS_ASSET_NO=?,\n" + "       SIM_NO=?,\n"
				+ "       ID=?,\n" + "       FACTORY_CODE=?,\n"
				+ "       ATTACH_METER_FLAG=?,\n"
				+ "       TERMINAL_TYPE_CODE=?,\n" + "       COLL_MODE=?,\n"
				+ "       PROTOCOL_CODE=?,\n" + "       COMM_PASSWORD=?,\n"
				+ "       RUN_DATE=?,\n" + "       STATUS_CODE=?,\n"
				+ "       HARMONIC_DEV_FLAG=?,\n"
				+ "       PS_ENSURE_FLAG=?,\n" + "       AC_SAMPLING_FLAG=?,\n"
				+ "       ELIMINATE_FLAG=?,\n" + "       GATE_ATTR_FLAG=?,\n"
				+ "       PRIO_PS_MODE=?,\n" + "       FREEZE_MODE=?,\n"
				+ "       FREEZE_CYCLE_NUM=?,\n"
				+ "       MAX_LOAD_CURVE_DAYS=?,\n" + "       PS_LINE_LEN=?,\n"
				+ "       WORK_PS=?,\n" + "       SPEAKER_FLAG=?,\n"
				+ "       SPEAKER_DIST=?,\n" + "       SEND_UP_MODE=?,\n"
				+ "       COMM_MODE=?,\n" + "       FRAME_NUMBER=?,\n"
				+ "       POWER_CUT_DATE=?\n" + "WHERE terminal_id=?";

		return sql;
	}

	
	
	
	/**
	 * 方法 updateTmnlList
	 * 
	 * @param tmnl
	 * @return 更新终端的SQL参数
	 */
	private List<Object> updateTmnlList(RtmnlRunJdbc tmnl) {
		List<Object> list = new ArrayList<Object>();

		list.add(tmnl.getCpNo());
		list.add(tmnl.getTmnlAssetNo());
		list.add(tmnl.getTerminalAddr());
		list.add(tmnl.getCisAssetNo());
		list.add(tmnl.getSimNo());
		list.add(tmnl.getId());
		list.add(tmnl.getFactoryCode());
		list.add(tmnl.getAttachMeterFlag());
		list.add(tmnl.getTerminalTypeCode());
		list.add(tmnl.getCollMode());
		list.add(tmnl.getProtocolCode());
		list.add(tmnl.getCommPassword());
		list.add(tmnl.getRunDate());
		list.add(tmnl.getStatusCode());
		list.add(tmnl.getHarmonicDevFlag());
		list.add(tmnl.getPsEnsureFlag());
		list.add(tmnl.getAcSamplingFlag());
		list.add(tmnl.getEliminateFlag());
		list.add(tmnl.getGateAttrFlag());
		list.add(tmnl.getPrioPsMode());
		list.add(tmnl.getFreezeMode());
		list.add(tmnl.getFreezeCycleNum());
		list.add(tmnl.getMaxLoadCurveDays());
		list.add(tmnl.getPsLineLen());
		list.add(tmnl.getWorkPs());
		list.add(tmnl.getSpeakerFlag());
		list.add(tmnl.getSpeakerDist());
		list.add(tmnl.getSendUpMode());
		list.add(tmnl.getCommMode());
		list.add(tmnl.getFrameNumber());
		list.add(tmnl.getPowerCutDate());
		list.add(tmnl.getTerminalId());

		return list;
	}

	/**
	 * 通过tmnlId 查询是否存在
	 * 
	 * @return
	 */
	public long countTmnl(long tmnlId, String tmnlAssetNo) {
		String sql = "SELECT COUNT(*) FROM r_tmnl_run  r\n"
				+ "WHERE r.terminal_id=?\n" + "  AND r.tmnl_asset_no=?";

		return super.getJdbcTemplate().queryForLong(sql,
				new Object[] { tmnlId, tmnlAssetNo });

	}

	/**
	 * 根据终端地址查询终端详细参数信息
	 * @param tmnladdrs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RcpParaJdbc> queryTmnlParams(String tmnladdrs){
		String sql ="select comm_para_id, cp_no, terminal_addr, protocol_type_code, " +
				" channel_no, rts_on, rts_off, transmit_delay, resp_timeout, master_ip, " +
				" master_port, spare_ip_addr, spare_port, gateway_ip, gateway_port, " +
				" proxy_ip_addr, proxy_port, gprs_code, sms_no, apn, heartbeat_cycle, " +
				" start_date, alg_no, alg_key from r_cp_comm_para where terminal_addr = ?";
		return super.getJdbcTemplate().query(sql, new Object[] { tmnladdrs },
				new RTmnlParamRowMapper());
	}
	
	class RTmnlParamRowMapper implements RowMapper {
		
		@Override
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			RcpParaJdbc rpj = new RcpParaJdbc();
			try {
				rpj.setCommParaId(rs.getLong("comm_para_id"));
				rpj.setCpNo(rs.getString("cp_no"));
				rpj.setTerminalAddr(rs.getString("terminal_addr"));
				rpj.setProtocolCode(rs.getString("protocol_type_code"));
				rpj.setChannelNo(rs.getString("channel_no"));
				rpj.setRtsOn(rs.getInt("rts_on"));
				rpj.setRtsOff(rs.getInt("rts_off"));
				rpj.setTransmitDelay(rs.getInt("transmit_delay"));
				rpj.setRespTimeout(rs.getInt("resp_timeout"));
				rpj.setMasterIp(rs.getString("master_ip"));
				rpj.setMasterPort(rs.getInt("master_port"));
				rpj.setSpareIpAddr(rs.getString("spare_ip_addr"));
				rpj.setSparePort(rs.getInt("spare_port"));
				rpj.setGatewayIp(rs.getString("gateway_ip"));
				rpj.setGatewayPort(rs.getInt("gateway_port"));
				rpj.setProxyIpAddr(rs.getString("proxy_ip_addr"));
				rpj.setProxyPort(rs.getInt("proxy_port"));
				rpj.setGprsCode(rs.getString("gprs_code"));
				rpj.setSmsNo(rs.getString("sms_no"));
				rpj.setApn(rs.getString("apn"));
				rpj.setHeartbeatCycle(rs.getInt("heartbeat_cycle"));
				rpj.setStartDate(rs.getDate("start_date"));
				rpj.setAlgNo(rs.getString("alg_no"));
				rpj.setAlgKey(rs.getString("alg_key"));
				return rpj;
			} catch (Exception e) {
				return null;
			}
		}
		
	}
	
	/**
	 * 删除终端信息 
	 * @param tmnladdr 要进行删除的终端地址
	 * @return 成功:TRUE 失败:FALSE
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int deleteTmnl(String tmnladdr) throws Exception{
		List<String> sqlList = new ArrayList<String>();
		List<List> paramList = new ArrayList<List>();
		List cpprame = new ArrayList();
		cpprame.add(tmnladdr);
		//STEP1:删除采集点终端参数信息
		//DELETE R_CP_COMM_PARA  WHERE TERMINAL_ADDR = ?
		sqlList.add("DELETE R_CP_COMM_PARA  WHERE TERMINAL_ADDR = ?");
		paramList.add(cpprame);
		//STEP2:删除终端信息
		//DELETE R_TMNL_RUN RX WHERE RX.TERMINAL_ADDR = ?
		sqlList.add("DELETE R_TMNL_RUN RX WHERE RX.TERMINAL_ADDR = ?");
		paramList.add(cpprame);
		
		if(super.doTransaction(sqlList, paramList)==1){
			return 1;
		}else{
			return 0;
		}
	}
	
	
	
	/**
	 * 内部类　RTmnlRunRowMapper
	 * 
	 * @author zhangzhw 终端　RowMapper
	 */
	class RTmnlRunRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RtmnlRunJdbc rtmnlrunjdbc = new RtmnlRunJdbc();
			try {
				rtmnlrunjdbc.setTerminalId(rs.getString("TERMINAL_ID"));
				rtmnlrunjdbc.setCpNo(rs.getString("CP_NO"));
				rtmnlrunjdbc.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				rtmnlrunjdbc.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				rtmnlrunjdbc.setCisAssetNo(rs.getString("CIS_ASSET_NO"));
				rtmnlrunjdbc.setSimNo(rs.getString("SIM_NO"));
				rtmnlrunjdbc.setId(rs.getString("ID"));
				rtmnlrunjdbc.setFactoryCode(rs.getString("FACTORY_CODE"));
				rtmnlrunjdbc.setAttachMeterFlag(rs
						.getString("ATTACH_METER_FLAG"));
				rtmnlrunjdbc.setTerminalTypeCode(rs
						.getString("TERMINAL_TYPE_CODE"));
				rtmnlrunjdbc.setCollMode(rs.getString("COLL_MODE"));
				rtmnlrunjdbc.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				rtmnlrunjdbc.setCommPassword(rs.getString("COMM_PASSWORD"));
				rtmnlrunjdbc.setRunDate(rs.getTimestamp("RUN_DATE"));
				rtmnlrunjdbc.setStatusCode(rs.getString("STATUS_CODE"));
				rtmnlrunjdbc.setHarmonicDevFlag(rs
						.getString("HARMONIC_DEV_FLAG"));
				rtmnlrunjdbc.setPsEnsureFlag(rs.getString("PS_ENSURE_FLAG"));
				rtmnlrunjdbc
						.setAcSamplingFlag(rs.getString("AC_SAMPLING_FLAG"));
				rtmnlrunjdbc.setEliminateFlag(rs.getString("ELIMINATE_FLAG"));
				rtmnlrunjdbc.setGateAttrFlag(rs.getString("GATE_ATTR_FLAG"));
				rtmnlrunjdbc.setPrioPsMode(rs.getString("PRIO_PS_MODE"));
				rtmnlrunjdbc.setFreezeMode(rs.getString("FREEZE_MODE"));
				rtmnlrunjdbc.setFreezeCycleNum(rs.getInt("FREEZE_CYCLE_NUM"));
				rtmnlrunjdbc.setMaxLoadCurveDays(rs
						.getInt("MAX_LOAD_CURVE_DAYS"));
				rtmnlrunjdbc.setPsLineLen(rs.getInt("PS_LINE_LEN"));
				rtmnlrunjdbc.setWorkPs(rs.getString("WORK_PS"));
				rtmnlrunjdbc.setSpeakerFlag(rs.getString("SPEAKER_FLAG"));
				rtmnlrunjdbc.setSpeakerDist(rs.getInt("SPEAKER_DIST"));
				rtmnlrunjdbc.setSendUpMode(rs.getByte("SEND_UP_MODE"));
				rtmnlrunjdbc.setCommMode(rs.getString("COMM_MODE"));
				rtmnlrunjdbc.setFrameNumber(rs.getShort("FRAME_NUMBER"));
				rtmnlrunjdbc.setPowerCutDate(rs.getTimestamp("POWER_CUT_DATE"));
				return rtmnlrunjdbc;
			} catch (Exception e) {
				return null;
			}
		}
	}

}
